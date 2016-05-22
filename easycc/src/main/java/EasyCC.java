import jvm.LexicalArgs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EasyCC {

    // Logger
    private Logger l = LogManager.getFormatterLogger(getClass());

    public static void main(String[] args) {
        new EasyCC();
    }

    public EasyCC() {
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(System.getProperty(LexicalArgs.MACHINE), System.getProperty(LexicalArgs.CONFIG));
        lexicalAnalyzer.analyzeText("Hello world");
    }
}
