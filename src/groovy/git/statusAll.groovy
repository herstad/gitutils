package git

// All repositories in root and its subdirectories will have their status printed to std out

def defaultRoot = new File(".")

def root = args ? new File(args[0]) : defaultRoot

def shell = new shell.Shell()

def command = "git status"

def executeCommand = { localRepository ->
    def shellResult = shell.execute(command, localRepository)

    if (!(shellResult.output =~ /# On branch master\nnothing to commit, working directory clean/)) {
        println localRepository.canonicalPath
        println shellResult.command
        println shellResult.output
        println '********************************************************************************'
    }
}

def isRepository = { dir -> 
    def dotGit = new File(dir, '.git')
    return dotGit.exists()
}

if (isRepository(root)) {
   executeCommand(root)
}

root.eachDirRecurse({ dir ->
    if (isRepository(dir)) {
       executeCommand(dir)
    }
})
