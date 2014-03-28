package git

// All repositories in root and its subdirectories will have command executed

if (args.length < 1) {
    println "usage: commandAll <command> [root]"
    System.exit(0)
}

def defaultRoot = new File(".")
def root = args.length >= 2 ? new File(args[1]) : defaultRoot
def command = args[0]

new LocalCommand().execute(root, command, null)