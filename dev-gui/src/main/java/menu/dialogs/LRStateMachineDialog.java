package menu.dialogs;

import javax.swing.*;
import java.awt.*;

public class LRStateMachineDialog extends JDialog {

	private static final long serialVersionUID = 4722229452456233559L;

	public LRStateMachineDialog(JFrame parent, JPanel panel) {
		
		// Set title
		setTitle("LR State machine");
		
		// Init components
		JScrollPane dfaScrollPane = new JScrollPane(panel);
		
		// Config scroll
		dfaScrollPane.getVerticalScrollBar().setUnitIncrement(16);
		
		// Add components
		add(dfaScrollPane);
		
		// Config dialog
		setSize(new Dimension((int) (parent.getWidth()*0.9), (int) (parent.getHeight()*0.9)));
		setLocationRelativeTo(parent);
		setVisible(true);
	}
}
