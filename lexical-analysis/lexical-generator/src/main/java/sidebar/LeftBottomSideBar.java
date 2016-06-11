package sidebar;

import javax.swing.*;
import java.awt.*;

public class LeftBottomSideBar extends JPanel {

    // Components
    private JButton exportButton, loadButton;

    public LeftBottomSideBar() {

        // Init components
        exportButton = new JButton("Export to JSON");
        loadButton = new JButton("Load from JSON");

        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.BASELINE;
        gc.insets = new Insets(5,5,5,5);

        gc.gridx=0;
        gc.gridy++;
        add(exportButton, gc);

        gc.gridx=0;
        gc.gridy++;
        add(loadButton, gc);
    }
}
