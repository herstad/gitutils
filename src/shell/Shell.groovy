package shell

class Shell {

    ShellResult execute(String command, File workingDir) {
        String output = ''

        Process process = new ProcessBuilder(addShellPrefix(command))
            .directory(workingDir)
            .redirectErrorStream(true)
            .start()

        process.inputStream.eachLine { line ->
            output += (line + '\n')
        }

        process.waitFor()

        return new ShellResult(command, output.trim(), process.exitValue())
    }

    private String[] addShellPrefix(String command) {
        if (System.properties['os.name'].toLowerCase().contains('windows')) {
            return ['cmd', '/C', command]
        }
        return ['sh', '-c', command]
    }
}