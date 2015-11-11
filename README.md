# Git Utilities - Usage #

## Using groovy ##

    From src/groovy:
    Run: groovy git/<command> <arg1> <arg..>

    Example: groovy git/statusAll ~/src/allMyGitRepos

## Using mac ##

There are scripts in src/mac that executes commands with the current folder as root.

There are two ways to make the new commands available. Either integrated in git or as aliases for the commands

### Using the commands integrated in git ###

    From <gitRootDir>
    Run: git <command>

    Exemple:
    cd ~/src/allMyGitRepos
    git status-all

### Assuming alias have been created for the commands ###

    From <gitRootDir>
    Run: <commandAlias>

    Exemple:
    cd ~/src/allMyGitRepos
    gsa

### Add scripts to path ###

Add in .bash_profile scripts to path

    Example:
    export PATH=$PATH:/<pathToGitUtils>/gitutils/src/mac

### Create alias for command ###

Add in .bash_profile for each command

    alias <command_alias>='./<pathToGitUtils>/src/mac/<command>'
    Example: alias gsa='~/./src/gitutils/src/mac/git-status-all'




