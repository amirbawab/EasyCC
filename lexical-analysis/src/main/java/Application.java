import Utils.StringUtilsPlus;
import org.apache.logging.log4j.LogManager;
import parser.StateMachine;

public class Application {

    // Constants
    private final String IN_FILE = "/state-machine.json";
    private final org.apache.logging.log4j.Logger l = LogManager.getLogger();

    public static void main(String[] args) {
        new Application();
    }

    public Application() {
        StateMachine stateMachine = new StateMachine(IN_FILE);

        l.info("Printing state machine: " + IN_FILE);
        l.info("\n" + stateMachine.getAllStates());
    }
}
