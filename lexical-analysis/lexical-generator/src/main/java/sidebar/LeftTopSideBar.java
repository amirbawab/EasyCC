package sidebar;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by amir on 5/31/16.
 */
public class LeftTopSideBar extends JPanel {

    // Components
    private JComboBox<String> stateType, stateBacktrack;
    private JTextField finalStateToken;
    private JButton addStateButton, exportButton, loadButton, deleteSelectedEdgeRowButton, addEdgeRowsButton;
    private JPanel statePanel;
    private JTable edgesTable;
    private JScrollPane edgesTableSP;

    // Values
    private String[] typeValues = { "Initial", "Normal", "Final"};
    private String[] backtrackValues = { "Yes", "No"};

    public LeftTopSideBar() {

        // Init components
        stateType = new JComboBox<>(typeValues);
        stateBacktrack = new JComboBox<>(backtrackValues);
        finalStateToken = new JTextField(10);
        addStateButton = new JButton("Add new state");
        exportButton = new JButton("Export to JSON");
        loadButton = new JButton("Load from JSON");
        deleteSelectedEdgeRowButton = new JButton("Delete edge row");
        addEdgeRowsButton = new JButton("Add 10 edge rows");
        statePanel = new JPanel();
        DefaultTableModel tableModel = new DefaultTableModel(null, new Object[]{"From", "To", "Label"});
        edgesTable = new JTable(tableModel);
        edgesTableSP = new JScrollPane(edgesTable);

        // Set layout
        setLayout(new GridBagLayout());
        statePanel.setLayout(new GridBagLayout());

        GridBagConstraints gc = new GridBagConstraints();

        // Set border
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        // Add listeners
        addListeners();

        // Configure components
        configureComponents();

        gc.fill = GridBagConstraints.NONE;
        gc.weightx = 1;
        gc.weighty = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        gc.insets = new Insets(5,5,5,5);

        // Add components to state panel
        gc.gridx=0;
        gc.gridy=0;
        statePanel.add(new JLabel("Type: "), gc);
        gc.gridx=1;
        statePanel.add(stateType, gc);

        gc.gridx=0;
        gc.gridy++;
        statePanel.add(new JLabel("Backtrack: "), gc);
        gc.gridx=1;
        statePanel.add(stateBacktrack, gc);

        gc.gridx=0;
        gc.gridy++;
        statePanel.add(new JLabel("Token: "), gc);
        gc.gridx=1;
        statePanel.add(finalStateToken, gc);

        gc.anchor = GridBagConstraints.CENTER;
        gc.gridx=0;
        gc.gridy++;
        gc.gridwidth = 2;
        statePanel.add(addStateButton, gc);

        gc.gridx=0;
        gc.gridy++;
        statePanel.add(edgesTableSP, gc);

        gc.gridx=0;
        gc.gridy++;
        statePanel.add(deleteSelectedEdgeRowButton, gc);

        gc.gridx=0;
        gc.gridy++;
        statePanel.add(addEdgeRowsButton, gc);

        gc.gridx=0;
        gc.gridy++;
        statePanel.add(exportButton, gc);

        gc.gridx=0;
        gc.gridy++;
        statePanel.add(loadButton, gc);

        // Add components to left panel
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        gc.gridx=0;
        gc.gridy=0;
        add(statePanel, gc);
    }

    public void configureComponents() {

        // By default
        stateBacktrack.setEnabled(false);
        finalStateToken.setEnabled(false);

        // Configure edges table
        edgesTableSP.setPreferredSize(new Dimension(200, 200));
        edgesTable.getTableHeader().setReorderingAllowed(false);

    }

    /**
     * Add buttons listeners
     */
    public void addListeners() {
        stateType.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                // If final state
                if(stateType.getSelectedIndex() == 2) {
                    stateBacktrack.setEnabled(true);
                    finalStateToken.setEnabled(true);
                } else {
                    stateBacktrack.setEnabled(false);
                    finalStateToken.setEnabled(false);
                    finalStateToken.setText("");
                }
            }
        });
    }
}
