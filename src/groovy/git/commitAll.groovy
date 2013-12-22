package git

// All repositories in root and its subdirectories will be commited

def defaultRoot = new File(".")

def root = args ? new File(args[0]) : defaultRoot

def shell = new shell.Shell()

def command = "git commit"

def commandArgs

def executeCommand = { localRepository ->
    def shellResult = shell.execute("$command $commandArgs", localRepository)

    if (!(shellResult.output =~ /nothing to commit, working directory clean/)) {
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

def readInput = { label ->
    print label

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in))
    def input = br.readLine()

    return input
}

def commitMessage = readInput("Commit message: ")

if (!commitMessage) {
    println "You must enter a commit message"
    System.exit(0);
} else {
    commandArgs = "-am \"$commitMessage\""

    if (isRepository(root)) {
       executeCommand(root)
    }

    root.eachDirRecurse({ dir ->
        if (isRepository(dir)) {
            executeCommand(dir)
        }
    })
}
