package git

// Update below localRoot variables as relevant
// All repositories in subdirectories of localRoot will have their status printed to std out

def defaultRoot = new File(".")

def root = args[0] ? new File(args[0]) : defaultRoot

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
