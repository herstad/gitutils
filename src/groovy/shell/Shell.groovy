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

        return new ShellResult(
            command: command, 
            workingDir: workingDir,
            output: output.trim(),
            exitValue:process.exitValue()
        )
    }

    private String[] addShellPrefix(String command) {
        if (System.properties['os.name'].toLowerCase().contains('windows')) {
            return ['cmd', '/C', command]
        }
        return ['sh', '-c', command]
    }
}