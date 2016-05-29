package menu.dialogs;

import java.awt.Dimension;

import javax.swing.*;

public class StateMachineDialog extends JDialog {

	private static final long serialVersionUID = 4722229452456233559L;

	public StateMachineDialog(JFrame parent, JPanel panel) {
		
		// Set title
		setTitle("State machine");
		
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
