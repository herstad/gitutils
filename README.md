git
===

git utilities

# Usage #

## Using groovy ##

    From src/groovy:
    Run: groovy git/<command> <arg1> <arg..>

    Example: groovy git/statusAll ~/src/allMyGitRepos

## Using mac ##

There are scripts in src/mac that executes commands with the current folder as root.

Assuming alias have been created for the commands

    From <gitRootDir>
    Run: <command>

    Exemple:
    cd ~/src/allMyGitRepos
    git-status-all

### Create alias for command ###

Add in .bash_profile for each command

    alias <command_alias>='./<pathToGitUtils>/src/mac/<command>'
    Example: alias git-status-all='~/./src/gitutils/src/mac/git-status-all'




