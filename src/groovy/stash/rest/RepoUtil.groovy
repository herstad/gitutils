package stash.rest
@Grab(group='org.codehaus.groovy.modules.http-builder',
module='http-builder', version='0.6' )
import groovyx.net.http.RESTClient
import groovy.util.slurpersupport.GPathResult
import static groovyx.net.http.ContentType.JSON
class RepoUtil {

    def projectRegEx = 'ES..'
    def repoRegEx = '.*'
    def repoPathDelimiter = '\\.'

    def RESTClient enterpriseService;
    def String username;
    def String password;

    public RepoUtil(String rootURI, String username, String password) {
        enterpriseService = new RESTClient( rootURI )
        this.username = username
        this.password = password
        enterpriseService.defaultContentType = JSON

        enterpriseService.headers['Authorization'] = 'Basic ' + ("$username:$password").bytes.encodeBase64()
    }

    def getRemoteRepos = {
        def createCloneURL = { repoUrl ->
            return "http://$username:$password" + repoUrl.substring(repoUrl.indexOf('@'))
        }

        def getMatchingRepos = { projectPath ->
            enterpriseService.get( path : projectPath) { resp, json ->
                json.values.findAll{it =~ repoRegEx}.collect {
                    def command = createCloneURL(it.cloneUrl)
                    return new RemoteRepo(it.name, command)
                }
            }
        }

        def getProjectKeys = {
            ->
            enterpriseService.get( path : 'projects') { resp, json ->
                json.values.collect{ it.key }.findAll{ it =~ projectRegEx }
            }
        }

        return getProjectKeys().collect { getMatchingRepos("projects/$it/repos") }.flatten()
    }
}
