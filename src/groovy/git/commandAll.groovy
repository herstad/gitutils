package git

// All repositories in root and its subdirectories will have command executed

if (args.length != 1) {
    println "usage: commandAll <command> [root]"
    System.exit(0)
}

def command = args[0]

def defaultRoot = new File(".")

def root = args.length >= 2 ? new File(args[1]) : defaultRoot

def shell = new shell.Shell()

def executeCommand = { repository ->
    def shellResult = shell.execute(command, repository)

    println repository.canonicalPath
    println shellResult.command
    println shellResult.output
    println '********************************************************************************'

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
