package git

// All repositories in subdirectories of remoteRoot will be cloned to localRoot

if (args.length != 2) {
    println "usage: cloneAll remoteRoot localRoot"
    System.exit(0)
}

def remoteRoot = new File(args[0])
def localRoot = new File(args[1])

println "remoteRoot: $remoteRoot"
println "localRoot: $localRoot"
println "********************************************************************************"

def shell = new shell.Shell()

def isBareRepository = { dir -> 
    def head = new File(dir, "HEAD")
    return head.exists()
}

def isRepository = { dir -> 
    def dotGit = new File(dir, ".git")
    return dotGit.exists()
}

def clone = { remoteRepository ->
    def relativePath = new File(remoteRepository.parent).canonicalPath.substring(remoteRoot.canonicalPath.length())   
    def repositoryName = remoteRepository.canonicalPath.replaceAll(".*" + File.separator, "")
    def localRepository = new File(localRoot, relativePath)
    localRepository.mkdirs()

    // println "remoteRepository: $remoteRepository"
    // println "localRepository: $localRepository"

    if (isRepository(new File(localRepository, repositoryName))) {
        println "localRepository $localRepository already exists. remoteRepository $remoteRepository not cloned"
    } else {
        def command = "git clone \"${remoteRepository.canonicalPath}\""
        
        def shellResult = shell.execute(command, localRepository)
    
        println shellResult.command
        println shellResult.output
    }
    println "********************************************************************************"
}

remoteRoot.eachDirRecurse({ dir ->
    if (isBareRepository(dir)) {
        clone(dir)
    }
})