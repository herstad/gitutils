package shell

class Shell {
    private static final boolean WINDOWS = System.properties['os.name'].toLowerCase().contains('windows')

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
        if (WINDOWS) {
            return ['cmd', '/C', command]
        }
        def envShell = System.getenv()['SHELL']
        if (envShell) {
            return [envShell, '-c', command]
        }
        return ['sh', '-c', command]
    }
}