package git

import shell.Shell
import shell.ShellResult

class LocalCommand {
   private Shell shell = new Shell()

    // All repositories in root and its subdirectories will have command executed
    List execute(File root, String command, def outputFilter) {
        if (isRepository(root)) {
            executeCommand(root, command, outputFilter)
        }
        
        root.eachDirRecurse({ dir ->
            if (isRepository(dir)) {
                executeCommand(dir, command, outputFilter)
            }
        })
    }

    private boolean isRepository(File dir) {
        new File(dir, '.git').exists()
    }

    private void executeCommand(File repository, String command, def outputFilter) {
        ShellResult shellResult = shell.execute(command, repository)

        if (outputFilter == null || outputFilter(shellResult.output)) {
            println "${shellResult.workingDir.canonicalPath}: ${shellResult.command}"
            println shellResult.output
            println  "********************************************************************************"
        }
    }
}

