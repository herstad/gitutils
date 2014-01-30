package stash.rest
@Grab(group='org.codehaus.groovy.modules.http-builder',
module='http-builder', version='0.6' )
import groovyx.net.http.RESTClient
import groovy.util.slurpersupport.GPathResult
import static groovyx.net.http.ContentType.JSON
class RepoUtil {

    def projectPattern
    def repositoryPattern

    private RESTClient stashRestClient;
    private String username;
    private String password;

    public RepoUtil(String rootURI, String projectPattern, String repositoryPattern, String username, String password) {
        stashRestClient = new RESTClient( rootURI )
        this.projectPattern = projectPattern
        this.repositoryPattern = repositoryPattern
        this.username = username
        this.password = password
        stashRestClient.defaultContentType = JSON
        stashRestClient.headers['Authorization'] = 'Basic ' + ("$username:$password").bytes.encodeBase64()
    }

    def getRemoteRepos = {

        def getMatchingRepos = { projectPath ->
            stashRestClient.get( path : projectPath) { resp, json ->
                json.values.findAll{it =~ repositoryPattern}.collect {
                    return new RemoteRepo(it.name, it.cloneUrl)
                }
            }
        }

        def getProjectKeys = {
            stashRestClient.get( path : 'projects') { resp, json ->
                return json.values.collect{ it.key }.findAll{ it =~ projectPattern }
            }
        }

        def getReposFromProjectKeys = {
            getProjectKeys().collect {
                getMatchingRepos("projects/$it/repos")
            }.flatten()
        }
        
        return getReposFromProjectKeys()
    }
}
