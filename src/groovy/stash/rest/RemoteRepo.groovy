package stash.rest

class RemoteRepo {
    String name;
    String cloneURL;
    
    RemoteRepo(String name, String cloneURL) {
        super();
        this.name = name;
        this.cloneURL = cloneURL;
    }
    
}
