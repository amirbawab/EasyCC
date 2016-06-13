import jvm.LexicalArgs;
import jvm.SyntaxArgs;
import org.apache.commons.cli.*;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

public class EasyCC {

    // Logger
    private Logger l = LogManager.getFormatterLogger(getClass());

    // Components
    private LexicalAnalyzer lexicalAnalyzer;
    private SyntaxAnalyzer syntaxAnalyzer;
    private SemanticAnalyzer semanticAnalyzer;
    private CodeGeneration codeGeneration;

    // Gui
    private MainFrame mainFrame;

    public static void main(String[] args) {
        try {
            new EasyCC(args);
        } catch (ParseException | IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Construct easycc instance
     * @param args
     */
    public EasyCC(String[] args) throws ParseException, IOException {

        // Init components
        lexicalAnalyzer = new LexicalAnalyzer(System.getProperty(LexicalArgs.MACHINE), System.getProperty(LexicalArgs.TOKENS), System.getProperty(LexicalArgs.MESSAGES));
        syntaxAnalyzer = new SyntaxAnalyzer(lexicalAnalyzer, System.getProperty(SyntaxArgs.GRAMMAR), System.getProperty(SyntaxArgs.PARSE_STRATEGY), System.getProperty(SyntaxArgs.MESSAGES));
        semanticAnalyzer = new SemanticAnalyzer(syntaxAnalyzer);
        codeGeneration = new CodeGeneration(semanticAnalyzer);

        Option helpOption = Option.builder("h")
                .longOpt("help")
                .required(false)
                .desc("Show help")
                .build();

        Option guiOption = Option.builder("n")
                .longOpt("no-gui")
                .required(false)
                .desc("Display GUI")
                .build();

        Option includeOption = Option.builder("i")
                .longOpt("input")
                .required(false)
                .desc("Input files")
                .build();

        Options options = new Options();
        options.addOption(helpOption);
        options.addOption(guiOption);
        options.addOption(includeOption);

        CommandLineParser parser = new DefaultParser();
        CommandLine cmdLine = parser.parse(options, args);

        if (cmdLine.hasOption("help")) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("EasyCC", options);
            return;
        }
        startGui();
    }

    /**
     * Compile text
     * @param text
     */
    private void compile(String text) {
        lexicalAnalyzer.analyzeText(text);
        syntaxAnalyzer.parse(lexicalAnalyzer.getFirstToken());
    }

    /**
     * Start application with a GUI
     */
    private void startGui() {
        mainFrame = new MainFrame("EasyCC - Dev GUI");
        mainFrame.setDevGUIListener(new GuiIntegration(lexicalAnalyzer, syntaxAnalyzer, semanticAnalyzer));
    }
}
