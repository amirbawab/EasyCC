import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public static void main(String[] args) {
        new MainFrame("EasyCC - Lexical tokens Generator");
    }

    public MainFrame(String title) {

        // Add title
        super(title);

        Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension((int) (screenDim.width*0.9), (int) (screenDim.height*0.9)));
        this.setMinimumSize(new Dimension(600, 600));
        this.setVisible(true);
        this.pack();
        this.setLocationRelativeTo(null);
    }
}
