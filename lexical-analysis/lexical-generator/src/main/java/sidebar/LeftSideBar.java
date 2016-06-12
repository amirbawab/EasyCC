package sidebar;

import machine.StateMachine;
import machine.StateMachineException;
import machine.json.Edge;
import machine.json.MachineGraph;
import machine.json.State;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class LeftSideBar extends JPanel {

    // Components
    private JComboBox<String> stateType, stateBacktrack, fromState, toState;
    private JTextField stateName, finalStateToken, edgeLabel;
    private JButton addStateButton, deleteStateButton, deleteEdgeButton, updateStateButton, verifyDFAButton, addEdgeButton, clearAllButton, exportButton, importButton;
    private JTable edgesTable;
    private JScrollPane edgesTableSP;
    private DefaultTableModel edgeTableModel;
    private JFileChooser fileChooser;

    // Store machine
    private StateMachine stateMachine;

    // Listener
    private LeftSideBarListener leftTopSideBarListener;

    // Values
    private String[] typeValues = {State.Type.INITIAL.getValue(), State.Type.NORMAL.getValue(), State.Type.FINAL.getValue()};
    private String[] backtrackValues = { "Yes", "No"};

    public LeftSideBar() {

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
        updateStateButton = new JButton("Update state by Name");
        deleteEdgeButton = new JButton("Delete selected Edge");
        verifyDFAButton = new JButton("Verify DFA");
        clearAllButton = new JButton("Clear all");
        exportButton = new JButton("Export JSON");
        importButton = new JButton("Import JSON");
        edgeTableModel = new DefaultTableModel(null, new Object[]{"From", "To", "Label"});
        edgesTable = new JTable(edgeTableModel);
        edgesTableSP = new JScrollPane(edgesTable);
        fileChooser = new JFileChooser();
        JPanel statePanel = new JPanel();
        JPanel edgePanel = new JPanel();
        JPanel otherPanel = new JPanel();

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
        statePanel.add(updateStateButton, gc);

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

    private void configureComponents() {

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
    private void addListeners() {
        JFrame frame = (JFrame)SwingUtilities.getRoot(LeftSideBar.this);

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

        addStateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                // Get data
                String name = stateName.getText();
                String type = (String) stateType.getSelectedItem();
                boolean backtrack = stateBacktrack.getSelectedIndex() == 0;
                String token = finalStateToken.getText();

                if( (type.equals(State.Type.FINAL.getValue()) && token.isEmpty() ) || name.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill all the state fields", "State not created", JOptionPane.ERROR_MESSAGE);
                } else {

                    try {
                        // Create state
                        State state = new State();
                        state.setName(name);
                        state.setType(type);
                        if (type.equals(State.Type.FINAL.getValue())) {
                            state.setBacktrack(backtrack);
                            state.setToken(token);
                        }
                        stateMachine.addState(state);
                        leftTopSideBarListener.refresh();
                    } catch (StateMachineException e) {
                        JOptionPane.showMessageDialog(frame, e.getMessage(), "State not created", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        addEdgeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                String label = edgeLabel.getText();
                if(fromState.getSelectedIndex() < 0 || toState.getSelectedIndex() < 0 || edgeLabel.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill all the edge fields", "Edge not created", JOptionPane.ERROR_MESSAGE);
                } else {

                    // Get data
                    String from = (String) fromState.getSelectedItem();
                    String to = (String) toState.getSelectedItem();

                    try {
                        // Create edge
                        Edge edge = new Edge();
                        edge.setFrom(from);
                        edge.setTo(to);
                        edge.setValue(label);
                        stateMachine.addEdge(edge);
                        leftTopSideBarListener.refresh();
                    } catch (StateMachineException e) {
                        JOptionPane.showMessageDialog(frame, e.getMessage(), "Edge not created", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        deleteStateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                String name = stateName.getText();
                if(name.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter a state name to delete", "State not deleted", JOptionPane.ERROR_MESSAGE);
                } else {

                    if(stateMachine.removeState(name)) {
                        leftTopSideBarListener.refresh();
                    } else {
                        JOptionPane.showMessageDialog(frame, "State not found", "State not deleted", JOptionPane.ERROR_MESSAGE);
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
                    int row = edgesTable.getSelectedRow();
                    String from = (String) edgeTableModel.getValueAt(row, 0);
                    String to = (String) edgeTableModel.getValueAt(row, 1);
                    String value = (String) edgeTableModel.getValueAt(row, 2);

                    if(stateMachine.removeEdge(from, to, value)) {
                        leftTopSideBarListener.refresh();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Edge not found", "Edge not deleted", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        updateStateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                if(stateName.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter a state name to update", "State not updated", JOptionPane.ERROR_MESSAGE);
                } else {

                    // Get data
                    String name = stateName.getText();
                    String type = (String) stateType.getSelectedItem();
                    boolean backtrack = stateBacktrack.getSelectedIndex() == 0;
                    String token = finalStateToken.getText();

                    if(type.equals(State.Type.FINAL.getValue()) && finalStateToken.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Please fill the token value", "State not updated", JOptionPane.ERROR_MESSAGE);
                    } else {

                        // Get state
                        State state = stateMachine.getStateByName(name);
                        if(state == null) {
                            JOptionPane.showMessageDialog(frame, "State not found", "State not updated", JOptionPane.ERROR_MESSAGE);
                        } else {

                            // If more than one initial state
                            State initial = stateMachine.getInitialState();
                            if(type.equals(State.Type.INITIAL.getValue()) && initial != null && initial != state) {
                                JOptionPane.showMessageDialog(frame, "Cannot have more than one initial state", "State not updated", JOptionPane.ERROR_MESSAGE);
                            } else {
                                state.setType(type);
                                if (type.equals(State.Type.FINAL.getValue())) {
                                    state.setToken(token);
                                    state.setBacktrack(backtrack);
                                }
                                leftTopSideBarListener.refresh();
                            }
                        }
                    }
                }
            }
        });

        clearAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                leftTopSideBarListener.reset();
            }
        });

        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                PrintWriter print = null;
                try {
                    int returnVal = fileChooser.showSaveDialog(frame);
                    File file = fileChooser.getSelectedFile();

                    if(returnVal == JFileChooser.APPROVE_OPTION) {
                        if(file.exists()) {
                            int result = JOptionPane.showConfirmDialog(frame, "The file exists, overwrite?","Existing file",JOptionPane.YES_NO_CANCEL_OPTION);
                            switch(result){
                                case JOptionPane.YES_OPTION:
                                    break;
                                case JOptionPane.NO_OPTION:
                                case JOptionPane.CLOSED_OPTION:
                                case JOptionPane.CANCEL_OPTION:
                                    return;
                            }
                        }

                        print = new PrintWriter(file);
                        leftTopSideBarListener.refresh();
                        print.write(leftTopSideBarListener.getJSON());
                    }

                } catch (FileNotFoundException e) {
                    JOptionPane.showMessageDialog(frame, e.getMessage(), "Error saving file", JOptionPane.ERROR_MESSAGE);
                } finally {
                    if(print != null) {
                        print.close();
                    }
                }
            }
        });
    }

    /**
     * Update fields with the latest information in the state machine
     */
    public void refresh() {

        // Delete old data
        fromState.removeAllItems();
        toState.removeAllItems();
        edgeTableModel.setRowCount(0);

        // Add new data
        for(State state : stateMachine.getAllStates()) {
            fromState.addItem(state.getName());
            toState.addItem(state.getName());

            for(Edge edge : state.getOutEdges()) {
                edgeTableModel.addRow(new Object[]{edge.getFrom(), edge.getTo(), edge.getValue()});
            }
        }
    }

    /**
     * Set listener
     * @param leftTopSideBarListener
     */
    public void setLeftTopSideBarListener(LeftSideBarListener leftTopSideBarListener) {
        this.leftTopSideBarListener = leftTopSideBarListener;
    }

    /**
     * Set state machine
     * @param stateMachine
     */
    public void setStateMachine(StateMachine stateMachine) {
        this.stateMachine = stateMachine;
    }
}
