package git
@Grab(group='org.codehaus.groovy.modules.http-builder',
    module='http-builder', version='0.5.2' )
import groovyx.net.http.RESTClient
import groovy.util.slurpersupport.GPathResult
import static groovyx.net.http.ContentType.JSON

def shell = new shell.Shell()

def rootURI = args[0] //'http://cuso.edb.se/stash/rest/api/1.0/'
def localRoot = args[1] //'C:/LFSERVICE/test'
def projectRegEx = 'ES..'
def repoRegEx = '.*'
def username = args[2] // System.console().readLine 'Username: '
def password =args[3]  // System.console().readPassword 'Password: '
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
    println localRepository.canonicalPath
    localRepository.mkdirs()
    return localRepository
}

def createCloneCommand = { repoUrl ->
    def cloneUrl = "http://$username:$password" + repoUrl.substring(repoUrl.indexOf('@'))
    return "git clone $cloneUrl ."
}

def cloneRepo = { projectPath -> 
    enterpriseService.get( path : projectPath) { resp, json ->
        json.values.findAll{it =~ repoRegEx}.each {
            def localRepository = createLocalRepoDir(it.name)
            def command = createCloneCommand(it.cloneUrl)
            
            def shellResult = shell.execute(command, localRepository)
            
            println shellResult.command
            println shellResult.output
        }
     }
 }

def getProjectKeys = { ->
    enterpriseService.get( path : 'projects') { resp, json ->
        return json.values.collect{ it.key }.findAll{ it =~ projectRegEx }
    }
}

enterpriseService = new RESTClient( rootURI )
enterpriseService.defaultContentType = JSON

enterpriseService.headers['Authorization'] = 'Basic ' + ("$username:$password").bytes.encodeBase64()

def projectKeys = getProjectKeys().each {
    cloneRepo("projects/$it/repos")
}

                         