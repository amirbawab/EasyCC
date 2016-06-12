package center;

import helper.GuiHelper;
import machine.StateMachine;
import utils.StringUtilsPlus;

import javax.swing.*;
import java.awt.*;

public class CenterPanel extends JTabbedPane {

    // Components
    private JPanel graphPanel, jsonPanel;
    private JTextPane jsonText;
    private StateMachine stateMachine;

    public CenterPanel() {

        // Init components
        graphPanel = new JPanel();
        jsonPanel = new JPanel();
        jsonText = new JTextPane();
        jsonText.setEditable(false);

        // Set layout
        graphPanel.setLayout(new BorderLayout());
        jsonPanel.setLayout(new BorderLayout());

        // Set border
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add components
        jsonPanel.add(jsonText, BorderLayout.CENTER);

        addTab("Graph", graphPanel);
        addTab("JSON", new JScrollPane(jsonPanel));
    }

    /**
     * Generate JSON from a state machine
     */
    private void generateJSON() {
        jsonText.setText(StringUtilsPlus.objectToJson(stateMachine.createMachineGraph()));
    }

    /**
     * Draw graph on the graph tab
     */
    private void drawGraph() {
        graphPanel.setLayout(new BorderLayout());
        graphPanel.removeAll();
        graphPanel.add(GuiHelper.stateMachineToPanel(stateMachine));
        graphPanel.revalidate();
        graphPanel.repaint();
    }

    /**
     * Refresh panels
     */
    public void refresh() {
        drawGraph();
        generateJSON();
    }

    /**
     * Get the json result
     * @return json result
     */
    public String getJSON() {
        return jsonText.getText();
    }

    /**
     * Set state machine
     * @param stateMachine
     */
    public void setStateMachine(StateMachine stateMachine) {
        this.stateMachine = stateMachine;
    }
}
