package sidebar;

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
    private MachineGraph lexical_analysis;

    // Listener
    private LeftSideBarListener leftTopSideBarListener;

    // Values
    private String[] typeValues = {State.Type.INITIAL.getValue(), State.Type.NORMAL.getValue(), State.Type.FINAL.getValue()};
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
        updateStateButton = new JButton("Update state by Name");
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
        fileChooser = new JFileChooser();

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

        addStateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                if( (stateType.getSelectedIndex() == 2 && finalStateToken.getText().isEmpty() ) || stateName.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill all the state fields", "State not created", JOptionPane.ERROR_MESSAGE);
                } else {

                    // Get data
                    String name = stateName.getText();
                    String type = (String) stateType.getSelectedItem();
                    boolean backtrack = stateBacktrack.getSelectedIndex() == 0;
                    String token = finalStateToken.getText();

                    for(State state : lexical_analysis.getStates()) {

                        // If more than one initial
                        if(type.equals(State.Type.INITIAL.getValue()) && state.getType().equals(State.Type.INITIAL)) {
                            JOptionPane.showMessageDialog(frame, "Cannot have more than one initial state", "State not created", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        // If already exists
                        if(state.getName().equals(name)) {
                            JOptionPane.showMessageDialog(frame, "State name already exists", "State not created", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }

                    // Create state
                    State state = new State();
                    state.setName(name);
                    state.setType(type);

                    if(type.equals(State.Type.FINAL.getValue())) {
                        state.setBacktrack(backtrack);
                        state.setToken(token);
                    }
                    lexical_analysis.getStates().add(state);

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
                    for(Edge edge : lexical_analysis.getEdges()) {
                        if(edge.getFromState().getName().equals(from) && edge.getToState().getName().equals(to) && edge.getValue().equals(label)) {
                            JOptionPane.showMessageDialog(frame, "Edge already exists", "Edge not created", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }

                    // Create edge
                    Edge edge = new Edge();
                    edge.setFrom(fromState.getSelectedItem().toString());
                    edge.setTo(toState.getSelectedItem().toString());
                    edge.setFromState(lexical_analysis.getStates().get(fromState.getSelectedIndex()));
                    edge.setToState(lexical_analysis.getStates().get(toState.getSelectedIndex()));
                    edge.setValue(label);
                    lexical_analysis.getEdges().add(edge);

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
                    for(int i = 0; i < lexical_analysis.getStates().size(); i++) {
                        if(name.equals(lexical_analysis.getStates().get(i).getName())) {
                            lexical_analysis.getStates().remove(i);
                            fromState.removeItemAt(i);
                            toState.removeItemAt(i);
                            found = true;
                            break;
                        }
                    }

                    if(!found) {
                        JOptionPane.showMessageDialog(frame, "State name does not exist", "State not deleted", JOptionPane.ERROR_MESSAGE);
                    } else {
                        for(int i = 0; i < lexical_analysis.getEdges().size(); i++) {
                            Edge edge = lexical_analysis.getEdges().get(i);
                            if(edge.getFromState().getName().equals(name) || edge.getToState().getName().equals(name)) {
                                lexical_analysis.getEdges().remove(i);
                                edgeTableModel.removeRow(i);
                                i--;
                            }
                        }

                        // Refresh all
                        leftTopSideBarListener.refresh();
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
                    lexical_analysis.getEdges().remove(edgesTable.getSelectedRow());
                    edgeTableModel.removeRow(edgesTable.getSelectedRow());

                    // Refresh all
                    leftTopSideBarListener.refresh();
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

                    State targetSate = null;

                    for(State state : lexical_analysis.getStates()) {

                        // If not the same state
                        if(!state.getName().equals(name))
                        {
                            // If more than one initial
                            if (type.equals(State.Type.INITIAL.getValue()) && state.getType().equals(State.Type.INITIAL)) {
                                JOptionPane.showMessageDialog(frame, "Cannot have more than one initial state", "State not updated", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                        } else {
                            targetSate = state;
                        }
                    }

                    if(targetSate != null) {

                        if(type.equals(State.Type.FINAL.getValue()) && finalStateToken.getText().isEmpty()) {
                            JOptionPane.showMessageDialog(frame, "Please fill the token value", "State not updated", JOptionPane.ERROR_MESSAGE);
                            return;
                        } else {
                            targetSate.setType(type);

                            if (type.equals(State.Type.FINAL.getValue())) {
                                targetSate.setBacktrack(backtrack);
                                targetSate.setToken(token);
                            }
                        }

                    } else {
                        JOptionPane.showMessageDialog(frame, "State name does not exist", "State not updated", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Refresh all
                    leftTopSideBarListener.refresh();
                }
            }
        });

        clearAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                while(edgeTableModel.getRowCount() > 0) {
                    edgeTableModel.removeRow(0);
                }
                while(fromState.getItemCount() > 0) {
                    fromState.removeItemAt(0);
                    toState.removeItemAt(0);
                }
                lexical_analysis.getEdges().clear();
                lexical_analysis.getStates().clear();
                leftTopSideBarListener.refresh();
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
     * Set listener
     * @param leftTopSideBarListener
     */
    public void setLeftTopSideBarListener(LeftSideBarListener leftTopSideBarListener) {
        this.leftTopSideBarListener = leftTopSideBarListener;
    }

    /**
     * Set lexical machine
     * @param lexical_analysis
     */
    public void setLexical_analysis(MachineGraph lexical_analysis) {
        this.lexical_analysis = lexical_analysis;
    }
}
