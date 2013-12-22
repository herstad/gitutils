package git

// Update below defaultRoot variable as relevant
// All repositories in subdirectories of localRoot will be pulled

def defaultRoot = new File(".")

def root = args[0] ? new File(args[0]) : defaultRoot

def shell = new shell.Shell()

def command = "git push"

def executeCommand = { localRepository ->
    def shellResult = shell.execute(command, localRepository)

    if (!(shellResult.output =~ /Everything up-to-date/)) {
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
