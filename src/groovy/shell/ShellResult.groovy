package shell

class ShellResult {
    String command
    String output
    int exitValue
    
    ShellResult(String command, String output, int exitValue) {
        this.command = command
        this.output = output
        this.exitValue = exitValue
    }
}