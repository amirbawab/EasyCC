package sidebar;

import data.LexicalMachineJSON;
import data.LexicalStateJSON;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LeftTopSideBar extends JPanel {

    // Components
    private JComboBox<String> stateType, stateBacktrack;
    private JTextField finalStateToken;
    private JButton addStateButton, refreshEdgeRowsButton, addEdgeRowsButton, clearAllButton;
    private JPanel statePanel;
    private JTable edgesTable;
    private JScrollPane edgesTableSP;
    private DefaultTableModel tableModel;

    // Private unique id
    private int uniqueId = 0;

    // Store machine
    private LexicalMachineJSON lexicalMachineJSON;

    // Listener
    private LeftTopSideBarListener leftTopSideBarListener;

    // Values
    private String[] typeValues = { "Initial", "Normal", "Final"};
    private String[] backtrackValues = { "Yes", "No"};

    public LeftTopSideBar() {

        // Init components
        stateType = new JComboBox<>(typeValues);
        stateBacktrack = new JComboBox<>(backtrackValues);
        finalStateToken = new JTextField(10);
        addStateButton = new JButton("Add new state");
        addEdgeRowsButton = new JButton("Add 10 edge rows");
        clearAllButton = new JButton("Clear all");
        refreshEdgeRowsButton = new JButton("Refresh All");
        statePanel = new JPanel();
        tableModel = new DefaultTableModel(null, new Object[]{"From", "To", "Label"});
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
        statePanel.add(addEdgeRowsButton, gc);

        gc.gridx=0;
        gc.gridy++;
        statePanel.add(refreshEdgeRowsButton, gc);

        gc.gridx=0;
        gc.gridy++;
        statePanel.add(clearAllButton, gc);

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

        // Add empty rows
        for(int i=0; i < 10; i++) {
            tableModel.addRow(new Object[tableModel.getColumnCount()]);
        }
    }

    /**
     * Add buttons listeners
     */
    public void addListeners() {

        refreshEdgeRowsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                leftTopSideBarListener.refresh();
            }
        });

        addStateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                LexicalStateJSON lexicalStateJSON = new LexicalStateJSON();
                lexicalStateJSON.setName("" + uniqueId++);
                lexicalMachineJSON.getStates().add(lexicalStateJSON);
                leftTopSideBarListener.refresh();
            }
        });

        stateType.addActionListener(new ActionListener() {
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

        addEdgeRowsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                for(int i=0; i < 10; i++) {
                    tableModel.addRow(new Object[tableModel.getColumnCount()]);
                }
            }
        });

        clearAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                while(tableModel.getRowCount() > 0) {
                    tableModel.removeRow(0);
                    lexicalMachineJSON.getEdges().clear();
                    lexicalMachineJSON.getStates().clear();
                }
                leftTopSideBarListener.refresh();
            }
        });
    }

    /**
     * Set listener
     * @param leftTopSideBarListener
     */
    public void setLeftTopSideBarListener(LeftTopSideBarListener leftTopSideBarListener) {
        this.leftTopSideBarListener = leftTopSideBarListener;
    }

    /**
     * Set lexical machine
     * @param lexicalMachineJSON
     */
    public void setLexicalMachineJSON(LexicalMachineJSON lexicalMachineJSON) {
        this.lexicalMachineJSON = lexicalMachineJSON;
    }
}
