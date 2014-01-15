package git

import groovy.json.JsonSlurper
import stash.rest.RepoUtil

def shell = new shell.Shell()
def jsonSlurper = new JsonSlurper()

def configurationFile = args[0]
def username = args[1]
def password = args[2]

def rootURI
def localRoot
def repoPathDelimiter
def projectPattern
def repositoryPattern

// Clones repositories from stash using a configuration file
// Example configuration file:
//{
//    "rootURI": "http://example.com/stash/rest/api/1.0/",
//    "localRoot": "C:/SomeProject/test",
//    "projectPattern": "ES..|ENMI",
//    "repositoryPattern": ".*",
//    "repoPathDelimiter": "\\."
// }


if (args.length != 3) {
    println "usage: cloneStash configFile username password"
    System.exit(0)
}

def readConfiguration = {
    def config = jsonSlurper.parse(new File(configurationFile))
    rootURI = config.rootURI
    localRoot = config.localRoot
    projectPattern = config.projectPattern
    repositoryPattern = config.repositoryPattern
    repoPathDelimiter = config.repoPathDelimiter
}


def createPathFromName = { name ->
    return name.replaceAll(repoPathDelimiter, "/")
}

def createLocalRepoDir = { repoName ->
    def relativePath =  createPathFromName(repoName)
    def localRepository = new File(localRoot, relativePath)
    localRepository.mkdirs()
    return localRepository
}

def cloneRepo = { command, localRepository ->
    def shellResult = shell.execute(command, localRepository)

    println shellResult.command
    println shellResult.output
}

def createCloneCommand = { cloneURL -> "git clone $cloneURL ." }

readConfiguration()
enterpriseService = new RepoUtil( rootURI, projectPattern, repositoryPattern, username, password)

def repos = enterpriseService.getRemoteRepos()

repos.each {
    cloneRepo(createCloneCommand(it.cloneURL), createLocalRepoDir(it.name))
}