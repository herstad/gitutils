package git

// All repositories in root and its subdirectories will be pulled

def defaultRoot = new File(".")

def root = args ? new File(args[0]) : defaultRoot

def shell = new shell.Shell()

def command = "git pull"

def executeCommand = { repository ->
    def shellResult = shell.execute(command, repository)

//    if (!(shellResult.output =~ /Everything up-to-date/)) {
        println repository.canonicalPath
        println shellResult.command
        println shellResult.output
        println '********************************************************************************'
//    }
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
