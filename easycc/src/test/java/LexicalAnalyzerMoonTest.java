import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Apply unit test for a custom language "Moon"
 */

public class LexicalAnalyzerMoonTest {

    // Lexical analyzer
    private static LexicalAnalyzer lexicalAnalyzer;

    @BeforeClass
    public static void initLexicalAnalyzer() {
        lexicalAnalyzer = new LexicalAnalyzer("/moon/lexical-machine.json", "/moon/lexical-tokens.json", "/moon/lexical-messages.json");
    }

    @Test
    public void testInput1() throws IOException {
        testTemplate("/moon/input/example1.txt", "/moon/output/example1.txt");
    }

    @Test
    public void testInput2() throws IOException {
        testTemplate("/moon/input/example2.txt", "/moon/output/example2.txt");
    }

    @Test
    public void testInput3() throws IOException {
        testTemplate("/moon/input/example3.txt", "/moon/output/example3.txt");
    }

    @Test
    public void testInput4() throws IOException {
        testTemplate("/moon/input/example4.txt", "/moon/output/example4.txt");
    }

    @Test
    public void testInput5() throws IOException {
        testTemplate("/moon/input/example5.txt", "/moon/output/example5.txt");
    }

    /**
     * Template for testing input and expected output
     * @param input
     * @param output
     * @throws IOException
     */
    private void testTemplate(String input, String output) throws IOException {
        String code = IOUtils.toString(getClass().getResource(input));
        String expectedOutput = IOUtils.toString(getClass().getResource(output));

        // Analyze the input text
        lexicalAnalyzer.analyzeText(code);

        assertEquals(expectedOutput, StringUtils.join(lexicalAnalyzer.getTokens(), "\n"));
    }
}
