package sidebar;

import javax.swing.*;
import java.awt.*;

/**
 * Created by amir on 5/31/16.
 */
public class LeftTopSideBar extends JPanel {

    // Components
    private JComboBox<String> stateType, stateBacktrack;
    private JTextField finalStateToken;
    private JButton addStateButton, exportButton, loadButton;

    // Values
    private String[] typeValues = { "Initial", "Normal", "Final"};
    private String[] backtrackValues = { "Yes", "No"};

    public LeftTopSideBar() {

        // Set layout
        setLayout(new GridBagLayout());

        // Init components
        stateType = new JComboBox<>(typeValues);
        stateBacktrack = new JComboBox<>(backtrackValues);
        finalStateToken = new JTextField(10);
        addStateButton = new JButton("Add new state");
        exportButton = new JButton("Export to JSON");
        loadButton = new JButton("Load from JSON");

        GridBagConstraints gc = new GridBagConstraints();

        // Set border
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        gc.fill = GridBagConstraints.NONE;
        gc.weightx = 1;
        gc.weighty = 1;

        // Add components
        gc.gridx=0;
        gc.gridy=0;
        add(new JLabel("Type: "), gc);
        gc.gridx=1;
        add(stateType, gc);

        gc.gridx=0;
        gc.gridy=1;
        add(new JLabel("Backtrack: "), gc);
        gc.gridx=1;
        add(stateBacktrack, gc);

        gc.gridx=0;
        gc.gridy=2;
        add(new JLabel("Token: "), gc);
        gc.gridx=1;
        add(finalStateToken, gc);

        gc.gridx=0;
        gc.gridy=3;
        gc.gridwidth = 2;
        add(addStateButton, gc);

        gc.gridx=0;
        gc.gridy=4;
        gc.gridwidth = 2;
        add(exportButton, gc);

        gc.gridx=0;
        gc.gridy=5;
        gc.gridwidth = 2;
        add(loadButton, gc);

    }
}
