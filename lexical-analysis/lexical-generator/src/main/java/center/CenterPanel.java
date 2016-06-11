package center;

import data.LexicalMachineJSON;

import javax.swing.*;

public class CenterPanel extends JTabbedPane {

    // Components
    private JPanel graphPanel, jsonPanel;
    private LexicalMachineJSON lexicalMachineJSON;

    public CenterPanel() {

        // Init components
        graphPanel = new JPanel();
        jsonPanel = new JPanel();

        // Set border
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        addTab("Graph", graphPanel);
        addTab("JSON", jsonPanel);
    }

    /**
     * Refresh panels
     */
    public void refresh() {
        System.out.println("Refresh!");
    }

    /**
     * Set lexical machine
     * @param lexicalMachineJSON
     */
    public void setLexicalMachineJSON(LexicalMachineJSON lexicalMachineJSON) {
        this.lexicalMachineJSON = lexicalMachineJSON;
    }
}
