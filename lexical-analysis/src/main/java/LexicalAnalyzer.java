import java.io.IOException;
import java.util.*;

import config.LexicalConfig;
import config.json.ErrorTokensConfig;
import config.json.ReservedConfig;
import helper.LexicalHelper;
import jvm.LexicalArgs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import machine.json.State;
import machine.StateMachine;
import machine.StateTransitionTable;
import token.AbstractToken;
import token.ErrorToken;
import token.LexicalToken;

public class LexicalAnalyzer {

    // Logger
    private Logger l = LogManager.getFormatterLogger(getClass());

    // Core variables
    private StateMachine stateMachine;
    private StateTransitionTable stateTransitionTable;

    // Store current line
    private String currentLine;

    // Store the location of the analysis process
    private int line, index, position;

    // Store the first letter of a word
    private int wordRow, wordCol;

    // Store the token value
    private String word;

    // Store the list of generated tokens
    private List<AbstractToken> tokens;

    // Keep track of execution time
    private long lexicalAnalysisProcessTime;

    // Input is analyzed using a scanner
    // A scanner will remove all the new line characters at the end of a line
    private Scanner scan;

    // State
    private int state;

    /**
     * Main method
     * @param args
     */
    public static void main(String[] args) {
        new LexicalAnalyzer(System.getProperty(LexicalArgs.MACHINE), System.getProperty(LexicalArgs.CONFIG));
    }

    /**
     * Constructor
     * @param stateMachineFilename
     * @param configurationFilename
     */
    public LexicalAnalyzer(String stateMachineFilename, String configurationFilename) {

        try {

            // Build state machine based on input file
            stateMachine = new StateMachine(stateMachineFilename);

            // Build state transition table
            stateTransitionTable = new StateTransitionTable(stateMachine);

            // Print the state transition table
            l.info("\n" + stateTransitionTable);

            // Load configuration
            LexicalConfig.getInstance().load(configurationFilename);

            // Initialize variables
            tokens = new ArrayList<>();
            word = "";
        } catch (IOException e) {
            l.error(e.getMessage());
        }
    }

    /**
     * Analyze a text
     * @param text
     */
    public void analyzeText(String text) {

        // Reset timer
        this.lexicalAnalysisProcessTime = System.currentTimeMillis();

        // Reset row count
        this.line = 0;

        // Reset position
        this.position = 0;

        // Reset state
        this.state = stateMachine.getInitialState().getId();

        // Scan text
        scan = new Scanner(text);

        // Reset list of tokens
        tokens.clear();

        // Process lines
        while(scan.hasNextLine())
            analyzeLine(scan.nextLine());

        // Close scanner
        scan.close();

        // Stop timer
        this.lexicalAnalysisProcessTime = System.currentTimeMillis() - this.lexicalAnalysisProcessTime;
    }

    /**
     * Analyze a line of code
     * @param line
     */
    private void analyzeLine(String line) {

        // Append EOL or EOF
        line += scan.hasNextLine() ? LexicalHelper.EOL : LexicalHelper.EOF;

        // Log
        l.info("Scanning line: %s", line.replaceAll(LexicalHelper.EOL+"", "~EOL~").replaceAll(LexicalHelper.EOF+"", "~EOF~"));

        // Store line
        this.currentLine = line;

        // Reset column
        this.index = 0;

        // Increment row
        this.line++;

        // While there are more tokens to consume
        while(index < line.length()) {

            // Get next token
            AbstractToken token = nextToken();

            // If token found
            if(token != null) {

                // Log created token
                l.info(token);

                // Add token
                tokens.add(token);
            }
        }
    }

    /**
     * Get next token
     * @return next token
     */
    private AbstractToken nextToken() {

        AbstractToken token = null;

        do {

            // If initial state
            if(state == stateMachine.getInitialState().getId()){
                wordCol = index + 1;
                wordRow = line;
            }

            // Current char
            char currentChar = currentLine.charAt(index++);

            // Update position
            position++;

            // Move to next state
            state = stateTransitionTable.lookup(state, currentChar);

            // Fetch new state
            State currentState = stateTransitionTable.getStateAtRow(state);

            // If current state is final
            if(currentState.getType() == State.Type.FINAL) {

                // If should backup, then backup one char
                if(currentState.shouldBacktrack()) {

                    // Go back one consumed character
                    index--;

                    // Update position
                    position--;

                } else {

                    // Add character to the word
                    word += currentChar;
                }

                // AbstractToken value
                String tokenStr = currentState.getToken();

                // Check if the word is a reserved one
                for(ReservedConfig reservedConfig : LexicalConfig.getInstance().getMachineConfig().getTokensConfig().getReservedConfig()) {
                    if(reservedConfig.getValue().equals(word)) {
                        tokenStr = reservedConfig.getToken();
                        break;
                    }
                }

                // Create token
                ErrorTokensConfig errorTokensConfig = LexicalConfig.getInstance().getMachineConfig().getTokensConfig().getErrorTokensConfig();
                if((tokenStr.startsWith(errorTokensConfig.getPrefix()) ||
                        errorTokensConfig.getInclude().contains(tokenStr)) &&
                        !errorTokensConfig.getExclude().contains(tokenStr)
                        ) {
                    token = new ErrorToken(tokenStr, word, wordRow, wordCol, position - word.length());
                } else {
                    token = new LexicalToken(tokenStr, word, wordRow, wordCol, position - word.length());
                }

                // Reset word
                word = "";

                // Go to initial state
                state = stateMachine.getInitialState().getId();

                // If not final state and not in the initial state
            } else if(state != stateMachine.getInitialState().getId()) {
                word += currentChar;
            }
        } while(token == null && index < currentLine.length());
        return token;
    }

    /**
     * Get lexical analysis process time
     * @return lexical analysis process time in ms
     */
    public long getProcessTime() {
        return this.lexicalAnalysisProcessTime;
    }

    /**
     * Get generated tokens
     * @return list of all type tokens
     */
    public List<AbstractToken> getTokens() {
        return tokens;
    }
}
