import jvm.LexicalArgs;
import jvm.SyntaxArgs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EasyCC {

    // Logger
    private Logger l = LogManager.getFormatterLogger(getClass());

    // Components
    private LexicalAnalyzer lexicalAnalyzer;
    private SyntaxAnalyzer syntaxAnalyzer;

    public static void main(String[] args) {
        new EasyCC();
    }

    public EasyCC() {

        // Init components
        lexicalAnalyzer = new LexicalAnalyzer(System.getProperty(LexicalArgs.MACHINE), System.getProperty(LexicalArgs.CONFIG));
        syntaxAnalyzer = new SyntaxAnalyzer(lexicalAnalyzer, System.getProperty(SyntaxArgs.GRAMMAR), System.getProperty(SyntaxArgs.PARSE_STRATEGY));

        startGui();
    }

    /**
     * Start application with a GUI
     */
    public void startGui() {
        MainFrame mainFrame = new MainFrame("EasyCC - Dev GUI");
        mainFrame.setDevGUIListener(new GuiIntegration(lexicalAnalyzer, syntaxAnalyzer));
    }
}
