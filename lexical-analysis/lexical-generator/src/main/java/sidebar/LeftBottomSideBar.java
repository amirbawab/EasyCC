package sidebar;

import javax.swing.*;
import java.awt.*;

public class LeftBottomSideBar extends JPanel {
    public LeftBottomSideBar() {

        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.BASELINE;
    }
}
