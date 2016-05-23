package tool;

import tool.components.ToolVButton;
import tool.listeners.ClickListener;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ToolBarPanel extends JPanel {
	
	private static final long serialVersionUID = -8385951480562168262L;

	// Components
	private ToolVButton newFileBtn, compileBtn;
	
	// Listener
	private ClickListener clickListener;
	
	// Enum
	public enum Button {
		NEW_FILE,
		COMPILE
	}
	
	public ToolBarPanel() {

		// Set layout
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

        // Init components
        this.newFileBtn = new ToolVButton("New File", new ImageIcon(getClass().getResource("/images/top_menu/new_file.png")));
        this.compileBtn = new ToolVButton("Compile", new ImageIcon(getClass().getResource("/images/top_menu/run.png")));
        
        // Add action
        this.newFileBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(clickListener != null)
					clickListener.onClickListener(Button.NEW_FILE);
			}
		});
        
        // Add action
        this.compileBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(clickListener != null)
					clickListener.onClickListener(Button.COMPILE);
			}
		});
        
        // Add components
        int spacing = 20;
        this.add(Box.createRigidArea(new Dimension(10, 0)));
        this.add(this.newFileBtn);
        this.add(Box.createRigidArea(new Dimension(spacing, 0)));
        this.add(this.compileBtn);
        
        this.setPreferredSize(new Dimension(0, 80)); // Size of the JPanel (Width is automatically set)
	}
	
	/**
	 * Set click listener
	 * @param clickListener
	 */
	public void setClickListener(ClickListener clickListener) {
		this.clickListener = clickListener;
	}
}
