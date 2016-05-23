package menu;

import menu.listeners.MainMenuListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MainMenu extends JMenuBar{
	
	private static final long serialVersionUID = 856681923844911926L;

	// Components
	private JMenu fileMenu, lexicalAnalysisMenu, parserMenu;
	private JMenuItem stateMachineMenuItem, stateTableMenuItem, firstFollowMenuItem, parsingTable, exitMenuItem;
	private JFrame parent;
	private MainMenuListener mainMenuListener;
	
	// Enum
	public enum Button {
		STATE_MACHINE,
		STATE_TABLE,
		FIRST_FOLLOW,
		PARSING_TABLE,
		EXIT
	}
	
	public MainMenu(JFrame parent) {
		
		// Set parent
		this.parent = parent;
		
		// Init components
		this.fileMenu = new JMenu("File");
		this.lexicalAnalysisMenu = new JMenu("Lexical Analysis");
		this.parserMenu = new JMenu("Parser");
		this.stateMachineMenuItem = new JMenuItem("State machine");
		this.stateTableMenuItem = new JMenuItem("State transition table");
		this.firstFollowMenuItem = new JMenuItem("First and follow sets");
		this.parsingTable = new JMenuItem("Parsing table");
		this.exitMenuItem = new JMenuItem("Exit");
		
		// Add submenu
		this.lexicalAnalysisMenu.add(stateMachineMenuItem);
		this.lexicalAnalysisMenu.add(stateTableMenuItem);
		this.parserMenu.add(firstFollowMenuItem);
		this.parserMenu.add(parsingTable);
		this.fileMenu.add(exitMenuItem);
		
		// On STATE_MACHINE
		this.stateMachineMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(mainMenuListener != null)
					mainMenuListener.menuClicked(Button.STATE_MACHINE);
			}
		});
		
		// State transition table
		this.stateTableMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(mainMenuListener != null)
					mainMenuListener.menuClicked(Button.STATE_TABLE);
			}
		});
		
		// First and follow sets
		this.firstFollowMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(mainMenuListener != null)
					mainMenuListener.menuClicked(Button.FIRST_FOLLOW);
			}
		});
		
		// Parsing table
		this.parsingTable.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(mainMenuListener != null)
					mainMenuListener.menuClicked(Button.PARSING_TABLE);
			}
		});
		
		
		// On exit
		this.exitMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(mainMenuListener != null)
					mainMenuListener.menuClicked(Button.EXIT);
			}
		});
		
		// Add menu
		add(fileMenu);
		add(lexicalAnalysisMenu);
		add(parserMenu);
	}
	
	/**
	 * Set listener
	 * @param mainMenuListener
	 */
	public void setMainMenuListener(MainMenuListener mainMenuListener) {
		this.mainMenuListener = mainMenuListener;
	}
}
