import center.CenterPanel;
import machine.json.Lexical_Analysis;
import sidebar.LeftSideBar;
import sidebar.LeftSideBarListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainFrame extends JFrame {

    // Components
    private LeftSideBar leftSideBar;
    private CenterPanel centerPanel;
    private Lexical_Analysis lexical_analysis;

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
        lexical_analysis = new Lexical_Analysis();
        lexical_analysis.setEdges(new ArrayList<>());
        lexical_analysis.setStates(new ArrayList<>());

        // Set machine
        leftSideBar.setLexical_analysis(lexical_analysis);
        centerPanel.setLexical_analysis(lexical_analysis);

        // Add components
        add(leftSideBar, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);

        // Add listeners
        addListeners();

        // Configure frame
        Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension((int) (screenDim.width*0.9), (int) (screenDim.height*0.9)));
        this.setMinimumSize(new Dimension(600, 600));
        this.setVisible(true);
        this.pack();
        this.setLocationRelativeTo(null);
    }

    /**
     * Add listeners for components
     */
    public void addListeners() {
        leftSideBar.setLeftTopSideBarListener(new LeftSideBarListener() {
            @Override
            public void refresh() {
                centerPanel.refresh();
            }

            @Override
            public String getJSON() {
                return centerPanel.getJSON();
            }
        });
    }
}
