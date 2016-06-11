import center.CenterPanel;
import sidebar.LeftSideBar;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    // Components
    private LeftSideBar leftSideBar;
    private CenterPanel centerPanel;

    public static void main(String[] args) {
        new MainFrame("EasyCC - Lexical tokens Generator");
    }

    public MainFrame(String title) {

        // Add title
        super(title);

        // Set default layout to border layout
        setLayout(new BorderLayout());

        // Init components
        leftSideBar = new LeftSideBar();
        centerPanel = new CenterPanel();

        // Add components
        add(leftSideBar, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);

        // Configure frame
        Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension((int) (screenDim.width*0.9), (int) (screenDim.height*0.9)));
        this.setMinimumSize(new Dimension(600, 600));
        this.setVisible(true);
        this.pack();
        this.setLocationRelativeTo(null);
    }
}
