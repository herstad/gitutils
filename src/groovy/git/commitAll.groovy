package git

// All repositories in root and its subdirectories will be committed

def defaultRoot = new File(".")
def root = args ? new File(args[0]) : defaultRoot
def command = "git commit"

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
    new LocalCommand().execute(root, "git commit -m $commitMessage", { output ->
        true
    })
}
