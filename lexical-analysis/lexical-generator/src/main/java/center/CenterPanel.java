package center;

import javax.swing.*;

public class CenterPanel extends JTabbedPane {

    // Components
    private JPanel graphPanel, jsonPanel;

    public CenterPanel() {

        // Init components
        graphPanel = new JPanel();
        jsonPanel = new JPanel();

        // Set border
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        addTab("Graph", graphPanel);
        addTab("JSON", jsonPanel);
    }
}
