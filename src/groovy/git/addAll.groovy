package git

// All repositories in root and its subdirectories will have not added files added

def defaultRoot = new File(".")
def root = args ? new File(args[0]) : defaultRoot

new LocalCommand().execute(root, "git add .", null)