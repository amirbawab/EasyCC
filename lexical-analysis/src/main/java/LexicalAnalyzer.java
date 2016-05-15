import org.apache.logging.log4j.LogManager;
import parser.StateMachine;
import parser.StateTransitionTable;

public class LexicalAnalyzer {

    // Constants
    private final String IN_FILE = "/state-machine.json";
    private final org.apache.logging.log4j.Logger l = LogManager.getLogger();

    public static void main(String[] args) {
        new LexicalAnalyzer();
    }

    public LexicalAnalyzer() {

        // Create state machine
        StateMachine stateMachine = new StateMachine(IN_FILE);

        // Construct state transition table
        StateTransitionTable stateTransitionTable = new StateTransitionTable(stateMachine);

        // Print states
        l.info("Printing state machine: " + IN_FILE);
        l.info("\n" + stateMachine);

        // Print state transition table
        l.info("Printing state transition table:");
        l.info("\n" + stateTransitionTable);
    }
}
