package shell

import org.junit.Before
import org.junit.Test
import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertNotNull

class ShellTest {
    Shell shell

    @Before
    void init() {
        shell = new Shell()
    }

    @Test
    void testExecuteMvn() {
        String command = 'mvn -version'

        ShellResult shellResult = shell.execute(command, new File('.'))

        assertEquals(command, shellResult.command)
        assertNotNull(shellResult.output)
        assertEquals(0, shellResult.exitValue)
    }

    @Test
    void testExecuteGroovy() {
        String command = 'groovy -version'

        ShellResult shellResult = shell.execute(command, new File('.'))

        assertEquals(command, shellResult.command)
        assertNotNull(shellResult.output)
        assertEquals(0, shellResult.exitValue)
    }
}