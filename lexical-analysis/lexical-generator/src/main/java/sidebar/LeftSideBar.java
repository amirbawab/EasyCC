package sidebar;

import data.LexicalEdgeJSON;
import data.LexicalMachineJSON;
import data.LexicalStateJSON;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LeftSideBar extends JPanel {

    // Components
    private JComboBox<String> stateType, stateBacktrack, fromState, toState;
    private JTextField stateName, finalStateToken, edgeLabel;
    private JButton addStateButton, deleteStateButton, deleteEdgeButton, verifyDFAButton, addEdgeButton, clearAllButton, exportButton, importButton;
    private JTable edgesTable;
    private JScrollPane edgesTableSP;
    private DefaultTableModel edgeTableModel;

    // Store machine
    private LexicalMachineJSON lexicalMachineJSON;

    // Listener
    private LeftSideBarListener leftTopSideBarListener;

    // Values
    private String[] typeValues = { "initial", "normal", "final"};
    private String[] backtrackValues = { "Yes", "No"};

    public LeftSideBar() {

        JPanel statePanel, edgePanel, otherPanel;

        // Init components
        stateType = new JComboBox<>(typeValues);
        stateBacktrack = new JComboBox<>(backtrackValues);
        fromState = new JComboBox<>();
        toState = new JComboBox<>();
        finalStateToken = new JTextField(10);
        edgeLabel = new JTextField(10);
        stateName = new JTextField(10);
        addStateButton = new JButton("Add new state");
        addEdgeButton = new JButton("Add new edge");
        deleteStateButton = new JButton("Delete state by Name");
        deleteEdgeButton = new JButton("Delete selected Edge");
        verifyDFAButton = new JButton("Verify DFA");
        clearAllButton = new JButton("Clear all");
        exportButton = new JButton("Export JSON");
        importButton = new JButton("Import JSON");
        statePanel = new JPanel();
        edgePanel = new JPanel();
        otherPanel = new JPanel();
        edgeTableModel = new DefaultTableModel(null, new Object[]{"From", "To", "Label"});
        edgesTable = new JTable(edgeTableModel);
        edgesTableSP = new JScrollPane(edgesTable);

        // Set layout
        setLayout(new GridBagLayout());
        statePanel.setLayout(new GridBagLayout());
        edgePanel.setLayout(new GridBagLayout());
        otherPanel.setLayout(new GridBagLayout());

        GridBagConstraints gc = new GridBagConstraints();

        // Set border
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        statePanel.setBorder(BorderFactory.createTitledBorder("Add State"));
        edgePanel.setBorder(BorderFactory.createTitledBorder("Add Edge"));
        otherPanel.setBorder(BorderFactory.createTitledBorder("Other"));

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
        statePanel.add(new JLabel("Name: "), gc);
        gc.gridx=1;
        statePanel.add(stateName, gc);

        gc.gridx=0;
        gc.gridy++;
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
        statePanel.add(deleteStateButton, gc);

        gc.gridx=0;
        gc.gridy=0;
        gc.gridwidth = 1;
        edgePanel.add(new JLabel("From: "), gc);
        gc.gridx=1;
        edgePanel.add(fromState, gc);

        gc.gridx=0;
        gc.gridy++;
        edgePanel.add(new JLabel("To: "), gc);
        gc.gridx=1;
        edgePanel.add(toState, gc);

        gc.gridx=0;
        gc.gridy++;
        edgePanel.add(new JLabel("Label: "), gc);
        gc.gridx=1;
        edgePanel.add(edgeLabel, gc);

        gc.gridx=0;
        gc.gridy++;
        gc.gridwidth = 2;
        edgePanel.add(addEdgeButton, gc);

        gc.gridx=0;
        gc.gridy++;
        edgePanel.add(edgesTableSP, gc);

        gc.gridx=0;
        gc.gridy++;
        edgePanel.add(deleteEdgeButton, gc);

        gc.gridx=0;
        gc.gridy=0;
        otherPanel.add(verifyDFAButton, gc);

        gc.gridx=0;
        gc.gridy++;
        otherPanel.add(clearAllButton, gc);

        gc.gridx=0;
        gc.gridy++;
        otherPanel.add(exportButton, gc);

        gc.gridx=0;
        gc.gridy++;
        otherPanel.add(importButton, gc);

        // Add components to left panel
        gc.anchor = GridBagConstraints.CENTER;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.gridwidth = 1;
        gc.gridx=0;
        gc.gridy=0;
        add(statePanel, gc);

        gc.gridx=0;
        gc.gridy++;
        add(edgePanel, gc);

        gc.gridx=0;
        gc.gridy++;
        add(otherPanel, gc);
    }

    public void configureComponents() {

        // By default
        stateBacktrack.setEnabled(false);
        finalStateToken.setEnabled(false);
        edgesTable.setDefaultEditor(Object.class, null);

        // Configure edges table
        edgesTableSP.setPreferredSize(new Dimension(200, 200));
        edgesTable.getTableHeader().setReorderingAllowed(false);
    }

    /**
     * Add buttons listeners
     */
    public void addListeners() {
        JFrame frame = (JFrame)SwingUtilities.getRoot(LeftSideBar.this);

        addStateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                if( (stateType.getSelectedIndex() == 2 && finalStateToken.getText().isEmpty() ) || stateName.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill all the state fields", "State not created", JOptionPane.ERROR_MESSAGE);
                } else {

                    // Get data
                    String name = stateName.getText();
                    String type = (String) stateType.getSelectedItem();
                    String backtrack = stateBacktrack.getSelectedIndex() == 0 ? "true" : "false";
                    String token = finalStateToken.getText();

                    for(LexicalStateJSON lexicalStateJSON : lexicalMachineJSON.getStates()) {

                        // If more than one initial
                        if(type.equals(typeValues[0]) && lexicalStateJSON.getType().equals(typeValues[0])) {
                            JOptionPane.showMessageDialog(frame, "Cannot have more than one initial state", "State not created", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        // If already exists
                        if(lexicalStateJSON.getName().equals(name)) {
                            JOptionPane.showMessageDialog(frame, "State name already exists", "State not created", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }

                    // Create state
                    LexicalStateJSON lexicalStateJSON = new LexicalStateJSON();
                    lexicalStateJSON.setName(name);
                    lexicalStateJSON.setBacktrack(backtrack);
                    lexicalStateJSON.setType(type);
                    lexicalStateJSON.setToken(token);
                    lexicalMachineJSON.getStates().add(lexicalStateJSON);

                    // Update from to states for edge panel
                    fromState.addItem(name);
                    toState.addItem(name);

                    // Refresh all
                    leftTopSideBarListener.refresh();
                }
            }
        });

        addEdgeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(fromState.getSelectedIndex() < 0 || toState.getSelectedIndex() < 0 || edgeLabel.getText().isEmpty()) {
                    JFrame frame = (JFrame)SwingUtilities.getRoot(LeftSideBar.this);
                    JOptionPane.showMessageDialog(frame, "Please fill all the edge fields", "Edge not created", JOptionPane.ERROR_MESSAGE);
                } else {

                    // Get data
                    String from = (String) fromState.getSelectedItem();
                    String to = (String) toState.getSelectedItem();
                    String label = edgeLabel.getText();

                    // Check if already exists
                    for(LexicalEdgeJSON lexicalEdgeJSON : lexicalMachineJSON.getEdges()) {
                        if(lexicalEdgeJSON.getFrom().equals(from) && lexicalEdgeJSON.getTo().equals(to) && lexicalEdgeJSON.getValue().equals(label)) {
                            JOptionPane.showMessageDialog(frame, "Edge already exists", "Edge not created", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }

                    // Create edge
                    LexicalEdgeJSON lexicalEdgeJSON = new LexicalEdgeJSON();
                    lexicalEdgeJSON.setFrom(from);
                    lexicalEdgeJSON.setTo(to);
                    lexicalEdgeJSON.setValue(label);
                    lexicalMachineJSON.getEdges().add(lexicalEdgeJSON);

                    // Add entry in table
                    edgeTableModel.addRow(new Object[]{from, to, label});

                    // Refresh all
                    leftTopSideBarListener.refresh();
                }
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

        deleteStateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(stateName.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter a state name to delete", "State not deleted", JOptionPane.ERROR_MESSAGE);
                } else {

                    boolean found = false;
                    String name = stateName.getText();
                    for(int i=0; i < lexicalMachineJSON.getStates().size(); i++) {
                        if(name.equals(lexicalMachineJSON.getStates().get(i).getName())) {
                            lexicalMachineJSON.getStates().remove(i);
                            fromState.removeItemAt(i);
                            toState.removeItemAt(i);
                            found = true;
                            break;
                        }
                    }

                    if(!found) {
                        JOptionPane.showMessageDialog(frame, "State name does not exist", "State not deleted", JOptionPane.ERROR_MESSAGE);
                    } else {
                        for(int i=0; i < lexicalMachineJSON.getEdges().size();i++) {
                            LexicalEdgeJSON lexicalEdgeJSON = lexicalMachineJSON.getEdges().get(i);
                            if(lexicalEdgeJSON.getFrom().equals(name) || lexicalEdgeJSON.getTo().equals(name)) {
                                lexicalMachineJSON.getEdges().remove(i);
                                edgeTableModel.removeRow(i);
                                i--;
                            }
                        }
                    }
                }
            }
        });

        deleteEdgeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(edgesTable.getSelectedRow() < 0) {
                    JOptionPane.showMessageDialog(frame, "Please select an edge to delete", "Edge not deleted", JOptionPane.ERROR_MESSAGE);
                } else {
                    lexicalMachineJSON.getEdges().remove(edgesTable.getSelectedRow());
                    edgeTableModel.removeRow(edgesTable.getSelectedRow());
                }
            }
        });

        clearAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                while(edgeTableModel.getRowCount() > 0) {
                    edgeTableModel.removeRow(0);
                }
                lexicalMachineJSON.getEdges().clear();
                lexicalMachineJSON.getStates().clear();
                leftTopSideBarListener.refresh();
            }
        });
    }

    /**
     * Set listener
     * @param leftTopSideBarListener
     */
    public void setLeftTopSideBarListener(LeftSideBarListener leftTopSideBarListener) {
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
