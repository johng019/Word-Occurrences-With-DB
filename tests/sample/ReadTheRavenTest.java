package sample;

import org.junit.Test;

import java.io.File;
import java.net.URL;

import static org.junit.Assert.*;

public class ReadTheRavenTest {

    @Test
    public void testReadFromRelativePath() {
        File file = new File("src/sample/TextAnalyzer.txt");
        assertTrue(file.exists());
    }

    @Test
    public void testReadFromAnInvalidPath() {
        File file = new File("src/sample/SomeFile.txt");
        assertFalse(file.exists());
    }
}

/** Ctrl + Shift + F10 run current method if in method.
 * current class if not inside a method.
 * Shift + F10: ReRun last Test.
 **/
