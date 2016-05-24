import data.GenericTable;
import data.LexicalAnalysisRow;
import data.structure.ConsoleData;
import helper.LexicalHelper;
import jvm.LexicalArgs;
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
    private SemanticAnalyzer semanticAnalyzer;

    public static void main(String[] args) {
        new EasyCC();
    }

    public EasyCC() {

        // Init components
        lexicalAnalyzer = new LexicalAnalyzer(System.getProperty(LexicalArgs.MACHINE), System.getProperty(LexicalArgs.CONFIG));
        semanticAnalyzer = new SemanticAnalyzer(lexicalAnalyzer);

        startGui();
    }

    /**
     * Start application with a GUI
     */
    public void startGui() {
        MainFrame mainFrame = new MainFrame("EasyCC - Dev GUI");
        mainFrame.setDevGUIListener(new DevGuiListener() {
            @Override
            public void lexicalAnalysis(String text) {
                lexicalAnalyzer.analyzeText(text);
            }

            @Override
            public ConsoleData<LexicalAnalysisRow> getLexicalAnalyzerOutput() {

                ConsoleData<LexicalAnalysisRow> rows = new ConsoleData<>();
                for(int row = 0; row < lexicalAnalyzer.getTokens().size(); row++) {

                    // Get current
                    AbstractToken token = lexicalAnalyzer.getTokens().get(row);

                    // If not error
                    if(token instanceof LexicalToken)
                        rows.add(new LexicalAnalysisRow(token.getToken(), token.getValue(), token.getRow(), token.getCol(), token.getPosition()));
                }

                return rows;
            }

            @Override
            public ConsoleData<LexicalAnalysisRow> getLexicalAnalyzerError() {

                ConsoleData<LexicalAnalysisRow> rows = new ConsoleData<>();
                for(int row = 0; row < lexicalAnalyzer.getTokens().size(); row++) {

                    // Get current
                    AbstractToken token = lexicalAnalyzer.getTokens().get(row);

                    // If error
                    if(token instanceof ErrorToken) {
                        rows.add(new LexicalAnalysisRow(token.getToken(), token.getValue(), token.getRow(), token.getCol(), token.getPosition(), LexicalHelper.tokenMessage(token)));
                    }
                }

                return rows;
            }

            @Override
            public void parse() {

            }

            @Override
            public Object[][] getParserOutput() {
                return new Object[0][];
            }

            @Override
            public Object[][] getParserError() {
                return new Object[0][];
            }

            @Override
            public long getLexicalAnalysisTime() {
                return lexicalAnalyzer.getProcessTime();
            }

            @Override
            public long getParserTime() {
                return 0;
            }

            @Override
            public boolean doesCompile() {
                return false;
            }

            @Override
            public Object[][][] getSymbolTables() {
                return new Object[0][][];
            }

            @Override
            public String getSymbolTableName(int id) {
                return null;
            }

            @Override
            public Object[][] getSemanticErrors() {
                return new Object[0][];
            }

            @Override
            public JPanel getParserTree() {
                return null;
            }

            @Override
            public String getGeneratedCode() {
                return null;
            }

            @Override
            public void setStateTable(GenericTable genericTable) {
                genericTable.setHeader(StringUtilsPlus.convertStringArrayToObjectArray(lexicalAnalyzer.getStateTransitionTable().prettifyStateTransitionTableHeader()));
                genericTable.setData(StringUtilsPlus.convertStringTableToObjectTable(lexicalAnalyzer.getStateTransitionTable().prettifyStateTransitionTableData()));
            }

            @Override
            public Object[][] getParsingTable() {
                return new Object[0][];
            }

            @Override
            public Object[][] getParsingTableRules() {
                return new Object[0][];
            }

            @Override
            public Object[][] getParsingTableErrors() {
                return new Object[0][];
            }

            @Override
            public Object[][] getFirstAndFollowSets() {
                return new Object[0][];
            }
        });
    }
}
