package git

// All repositories in root and its subdirectories will have their status printed to std out

def defaultRoot = new File(".")
def root = args ? new File(args[0]) : defaultRoot

new LocalCommand().execute(root, "git status", { output -> 
    !(output =~ /nothing to commit, working directory clean/)
})
