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
        startGui();
    }

    /**
     * Start application with a GUI
     */
    public void startGui() {
        MainFrame mainFrame = new MainFrame("EasyCC - Dev GUI");
    }
}
