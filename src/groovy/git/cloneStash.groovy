package git
@Grab(group='org.codehaus.groovy.modules.http-builder',
module='http-builder', version='0.6' )
import groovyx.net.http.RESTClient
import groovy.util.slurpersupport.GPathResult
import stash.rest.RepoUtil
import static groovyx.net.http.ContentType.JSON

def shell = new shell.Shell()

def rootURI = args[0] //'http://cuso.edb.se/stash/rest/api/1.0/'
def localRoot = args[1] //'C:/LFSERVICE/test'
def projectRegEx = 'ES..'
def repoRegEx = '.*'
def username = args[2] // System.console().readLine 'Username: '
def password = args[3]  // System.console().readPassword 'Password: '
def repoPathDelimiter = '\\.'

// All bare repositories in remoteRoot and its subdirectories will be cloned to localRoot

if (args.length != 4) {
    println "usage: callRest rootURI localRoot username password"
    System.exit(0)
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

enterpriseService = new RepoUtil( rootURI, username, password)

def repos = enterpriseService.getRemoteRepos()

repos.each {
    cloneRepo(createCloneCommand(it.cloneURL), createLocalRepoDir(it.name))
}