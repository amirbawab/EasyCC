import core.config.SemanticHandler;
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
            e.printStackTrace();
        }
    }

    /**
     * Construct easycc instance
     * @param args
     */
    public EasyCC(String[] args) throws ParseException, IOException {

        final String HELP = "help", GUI = "gui", INPUT = "input";

        Option helpOption = Option.builder(Character.toString(HELP.charAt(0)))
                .longOpt(HELP)
                .required(false)
                .desc("Show help")
                .build();

        Option guiOption = Option.builder(Character.toString(GUI.charAt(0)))
                .longOpt(GUI)
                .required(false)
                .desc("Display GUI")
                .build();

        Option includeOption = Option.builder(Character.toString(INPUT.charAt(0)))
                .longOpt(INPUT)
                .required(false)
                .numberOfArgs(Option.UNLIMITED_VALUES)
                .desc("Input files")
                .build();

        Options options = new Options();
        options.addOption(helpOption);
        options.addOption(guiOption);
        options.addOption(includeOption);

        CommandLineParser parser = new DefaultParser();
        CommandLine cmdLine = parser.parse(options, args);

        if (cmdLine.hasOption(HELP)) {
            cliHelp(options);

        } else if(cmdLine.hasOption(GUI)) {
            startGui();

        } else if(cmdLine.hasOption(INPUT)){
            init();
            String[] fileNameArray = cmdLine.getOptionValues(INPUT);
            for(String fileName : fileNameArray) {
                File file = new File(fileName);
                if(file.exists()) {
                    compile(FileUtils.readFileToString(file));
                } else {
                    System.err.println("Skipping: " + fileName + ". File " + file.getAbsolutePath() + " was not found");
                }
            }
        } else {
            cliHelp(options);
        }
    }

    /**
     * Display options
     * @param options
     */
    private void cliHelp(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("EasyCC", options);
    }

    /**
     * Init components
     */
    private void init() {
        lexicalAnalyzer = new LexicalAnalyzer(System.getProperty(LexicalArgs.MACHINE), System.getProperty(LexicalArgs.TOKENS), System.getProperty(LexicalArgs.MESSAGES));
        syntaxAnalyzer = new SyntaxAnalyzer(lexicalAnalyzer, System.getProperty(SyntaxArgs.GRAMMAR), System.getProperty(SyntaxArgs.PARSE_STRATEGY), System.getProperty(SyntaxArgs.MESSAGES));
        semanticAnalyzer = new SemanticAnalyzer(syntaxAnalyzer);
        codeGeneration = new CodeGeneration(SemanticHandler.getInstance());
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
        init();
        mainFrame = new MainFrame("EasyCC - Dev GUI");
        mainFrame.setDevGUIListener(new GuiIntegration(lexicalAnalyzer, syntaxAnalyzer, semanticAnalyzer));
    }
}
