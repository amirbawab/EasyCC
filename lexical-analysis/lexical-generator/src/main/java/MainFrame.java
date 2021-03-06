import center.CenterPanel;
import machine.StateMachine;
import sidebar.LeftSideBar;
import sidebar.LeftSideBarListener;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    // Components
    private LeftSideBar leftSideBar;
    private CenterPanel centerPanel;
    private StateMachine stateMachine;

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
        stateMachine = new StateMachine();

        // Set machine
        leftSideBar.setStateMachine(stateMachine);
        centerPanel.setStateMachine(stateMachine);

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
                leftSideBar.refresh();
            }

            @Override
            public void updateStateMachine(StateMachine stateMachine) {
                MainFrame.this.stateMachine = stateMachine;
                leftSideBar.setStateMachine(stateMachine);
                centerPanel.setStateMachine(stateMachine);
                refresh();
            }

            @Override
            public String getJSON() {
                return centerPanel.getJSON();
            }

            @Override
            public void reset() {
                updateStateMachine(new StateMachine());
            }
        });
    }
}
