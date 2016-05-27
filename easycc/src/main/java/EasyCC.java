import data.GenericTable;
import data.LexicalAnalysisRow;
import data.structure.ConsoleData;
import helper.LexicalHelper;
import jvm.LexicalArgs;
import jvm.SyntaxArgs;
import listener.DevGuiListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import token.AbstractToken;
import token.ErrorToken;
import token.LexicalToken;
import utils.StringUtilsPlus;

import javax.swing.*;

public class EasyCC {

    // Logger
    private Logger l = LogManager.getFormatterLogger(getClass());

    // Components
    private LexicalAnalyzer lexicalAnalyzer;
    private SyntaxAnalyzer semanticAnalyzer;

    public static void main(String[] args) {
        new EasyCC();
    }

    public EasyCC() {

        // Init components
        lexicalAnalyzer = new LexicalAnalyzer(System.getProperty(LexicalArgs.MACHINE), System.getProperty(LexicalArgs.CONFIG));
        semanticAnalyzer = new SyntaxAnalyzer(lexicalAnalyzer, System.getProperty(SyntaxArgs.GRAMMAR));

        startGui();
    }

    /**
     * Start application with a GUI
     */
    public void startGui() {
        MainFrame mainFrame = new MainFrame("EasyCC - Dev GUI");
        mainFrame.setDevGUIListener(new GuiIntegration(lexicalAnalyzer));
    }
}
