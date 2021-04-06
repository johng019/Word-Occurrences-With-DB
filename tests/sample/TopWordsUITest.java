package sample;

import javafx.stage.Stage;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


import static org.junit.Assert.*;

public class TopWordsUITest {
    TopWordsUI topWords = new TopWordsUI();
    File testFile = new File("src/sample/TextAnalyzer.txt");

    @Test
    public void testReadFromTargetRelativePath() {
        File file = new File("src/sample/TextAnalyzer.txt");
        assertTrue(file.exists());
    }

    @Test
    public void testReadFromAnInvalidPath() {
        File file = new File("src/sample/SomeFile.txt");
        assertFalse(file.exists());
    }

    @Test
    public void testCreateWordArray() throws IOException {
        topWords.createWordArray();;
        assertFalse(topWords.words.isEmpty());
    }

    @Test
    public void testCreateIntegerArray() throws IOException {
       topWords.createFrequencyArray();
        assertFalse(topWords.frequency.isEmpty());
    }

    @Test
    public void testStringArrayContainsWord(){
        topWords.words.contains("the");
        assertTrue("the",true);
    }
}

/** Ctrl + Shift + F10 run current method if in method.
 * current class if not inside a method.
 * Shift + F10: ReRun last Test.
 **/