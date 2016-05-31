package sidebar;

import javax.swing.*;
import java.awt.*;

public class LeftSideBar extends JPanel {

    private LeftTopSideBar leftTopSideBar;
    private LeftBottomSideBar leftBottomSideBar;

    public LeftSideBar() {

        // Set layout
        setLayout(new GridBagLayout());

        // Init components
        leftTopSideBar = new LeftTopSideBar();
        leftBottomSideBar = new LeftBottomSideBar();
        GridBagConstraints gc = new GridBagConstraints();

        gc.fill = GridBagConstraints.BOTH;
        gc.weighty = 1;
        gc.weightx = 1;

        gc.gridx = 0;
        gc.gridy = 0;
        add(leftTopSideBar, gc);

        gc.gridx = 0;
        gc.gridy = 1;
        add(leftBottomSideBar, gc);
    }
}
