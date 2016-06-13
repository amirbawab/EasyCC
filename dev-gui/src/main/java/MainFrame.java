import bottom.BottomPanel;
import center.CenterPanel;
import data.GenericTable;
import data.LexicalAnalysisRow;
import data.SemanticErrorRow;
import data.SyntaxAnalysisRow;
import data.structure.ConsoleData;
import listener.DevGuiListener;
import menu.MainMenu;
import menu.dialogs.StateMachineDialog;
import menu.dialogs.FirstFollowDialog;
import menu.dialogs.ParsingTableDialog;
import menu.dialogs.ParsingTableErrorsDialog;
import menu.dialogs.ParsingTableRulesDialog;
import menu.dialogs.StateTableDialog;
import menu.listeners.MainMenuListener;
import tool.ToolBarPanel;
import tool.ToolBarPanel.Button;
import tool.listeners.ClickListener;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MainFrame extends JFrame {
	
	private static final long serialVersionUID = -8026416994513756565L;

	// Listener
	private DevGuiListener devGuilistener;
	
	// Components
	private CenterPanel centerPanel;
	private ToolBarPanel toolBarPanel;
	private MainMenu mainMenu;
	private BottomPanel bottomPanel;

	/**
	 * TODO Remove main method
	 * @param args
     */
	public static void main(String[] args) {
		new MainFrame("Dev-GUI");
	}

	public MainFrame(String title) {
		
		// Set application name
		setTitle(title);
		
		// Set default layout to border layout
		setLayout(new BorderLayout());
		
		// Init components
		this.centerPanel = new CenterPanel();
		this.toolBarPanel = new ToolBarPanel();
		this.mainMenu = new MainMenu(this);
		this.bottomPanel = new BottomPanel();
		
		// Configure bottom panel
		this.bottomPanel.setStyle(BottomPanel.Style.NORMAL);
		
		// Set listener
		this.toolBarPanel.setClickListener(new ClickListener() {
			
			@Override
			public void onClickListener(Button type) {
				switch(type) {
				case NEW_FILE:
					centerPanel.addNewFile();
					break;
					
				case COMPILE:
					if(devGuilistener != null) {
						
						// Prepare message
						String message = "";
						
						// Content
						String content = centerPanel.getFileContent();
						
						if(content.replace("\n", "").trim().isEmpty()) {
							
							// Show warning message
							JOptionPane.showMessageDialog(MainFrame.this, "Please enter code to compile", "Empty file", JOptionPane.WARNING_MESSAGE);
							
						// Not empty
						} else {
							
							// Analyze input
							devGuilistener.lexicalAnalysis(content);
							
							// Lexical analysis output
							ConsoleData<LexicalAnalysisRow> lexicalAnalysisRowConsoleData = devGuilistener.getLexicalAnalyzerOutput();
							Object[][] scannerOutputData = lexicalAnalysisRowConsoleData.convertToObjectTable();
							centerPanel.setTableData(CenterPanel.SCANNER_OUTPUT_TITLE, scannerOutputData);

							// Error error
							ConsoleData<LexicalAnalysisRow> lexicalAnalysisRowConsoleError = devGuilistener.getLexicalAnalyzerError();
							Object[][] scannerErrorData = lexicalAnalysisRowConsoleError.convertToObjectTable();
							centerPanel.setTableData(CenterPanel.SCANNER_ERROR_TITLE, scannerErrorData);

							// Compilation time
							long compilationTime = devGuilistener.getLexicalAnalysisTime();

							// Scanner error
							if(scannerErrorData.length > 0)
								message += String.format("Lexical analysis: %d error(s) found! ", scannerErrorData.length);

							// Parse token
							devGuilistener.parse();
							
							// Update time
							compilationTime += devGuilistener.getParserTime();
							
							// Parser output
							ConsoleData<SyntaxAnalysisRow> parserOutputData = devGuilistener.getParserOutput();
							centerPanel.setTableData(CenterPanel.PARSER_OUTPUT_TITLE, parserOutputData.convertToObjectTable());

							// If parser error found, update compiler message
							List<String> errorMessages = devGuilistener.getSyntaxErrorMessages();
							if(errorMessages.size() > 0)
								message += String.format("Parser: %d error(s) found! ", errorMessages.size());

							// Get tree
							centerPanel.setPanel(CenterPanel.PARSER_TREE_TITLE, devGuilistener.getDerivationTree());

							// Clear symbol tables
							centerPanel.removeTablesInNavigationTable(CenterPanel.SYMBOL_TABLE_TITLE);

							List<GenericTable> symbolTables = devGuilistener.getSymbolTables();
							for(int i=0; i < symbolTables.size(); i++) {
								String subTableName = symbolTables.get(i).getName();
								centerPanel.addTableToNavigationTable(CenterPanel.SYMBOL_TABLE_TITLE, subTableName, symbolTables.get(i).getHeader());
								centerPanel.setTableOfTableData(CenterPanel.SYMBOL_TABLE_TITLE, subTableName, symbolTables.get(i).getData());
							}

							// Semantic errors output
							ConsoleData<SemanticErrorRow> semanticErrors = devGuilistener.getSemanticErrors();
							centerPanel.setTableData(CenterPanel.SEMANTIC_ERROR_TITLE, semanticErrors.convertToObjectTable());

							// Insert time
							message += String.format("Total time: %d ms", compilationTime);
							
							// Set message
							bottomPanel.setCompilerMessageText(message);
							
							// Change color
							if(devGuilistener.doesCompile()) {
								bottomPanel.setStyle(BottomPanel.Style.SUCCESS);
							} else{
								bottomPanel.setStyle(BottomPanel.Style.ERROR);
							}
						}
					}
					break;
				}
			}
		});
		
		// Add menu listeners
		mainMenu.setMainMenuListener(new MainMenuListener() {
			
			@Override
			public void menuClicked(MainMenu.Button button) {
				switch (button) {
				case STATE_MACHINE:
					
					// Open dialog
					if(devGuilistener != null) {
						new StateMachineDialog(MainFrame.this, devGuilistener.getStateMachineGraph());
					}
					break;

				case FIRST_FOLLOW:
					
					if(devGuilistener != null) {
						GenericTable firstAndFollowSetTable = new GenericTable();
						devGuilistener.setFirstAndFollowSets(firstAndFollowSetTable);
						new FirstFollowDialog(MainFrame.this, firstAndFollowSetTable.getHeader(), firstAndFollowSetTable.getData());
					}
					break;

				case PARSING_TABLE:
					if(devGuilistener != null) {

						// Prepare tables
						GenericTable ppGenericTable = new GenericTable();
						GenericTable ppGenericTableRule = new GenericTable();
						GenericTable ppGenericTableError = new GenericTable();

						// Get data
						devGuilistener.setLLPPTable(ppGenericTable);
						devGuilistener.setLLPPTableRules(ppGenericTableRule);
						devGuilistener.setLLPPTableErrors(ppGenericTableError);

						new ParsingTableDialog(MainFrame.this, ppGenericTable.getHeader(), ppGenericTable.getData());
						new ParsingTableRulesDialog(MainFrame.this, ppGenericTableRule.getHeader(), ppGenericTableRule.getData());
						new ParsingTableErrorsDialog(MainFrame.this, ppGenericTableError.getHeader(), ppGenericTableError.getData());
					}
					break;

				case STATE_TABLE:
					
					if(devGuilistener != null) {
						
						// Get data
						GenericTable stateTransitionTable = new GenericTable();
						devGuilistener.setStateTable(stateTransitionTable);
						new StateTableDialog(MainFrame.this, stateTransitionTable.getHeader(), stateTransitionTable.getData());
					}
					break;
					
				case EXIT:
					MainFrame.this.dispatchEvent(new WindowEvent(MainFrame.this, WindowEvent.WINDOW_CLOSING));
					break;
				}
			}
		});
		
		// Add menu
		setJMenuBar(mainMenu);
		
		// Add components
		add(this.centerPanel, BorderLayout.CENTER);
		add(this.toolBarPanel, BorderLayout.NORTH);
		add(this.bottomPanel, BorderLayout.SOUTH);
		
		// Screen dim
		Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
		
		 // Configure the JFrame
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);        // Exit when click on X
        this.setPreferredSize(new Dimension((int) (screenDim.width*0.9), (int) (screenDim.height*0.9)));    // Frame initial size
        this.setMinimumSize(new Dimension(600, 600));        // Minimum window size
        this.setVisible(true);                               // Make the frame visible
        this.pack();                                         // Force setting the size of components
        this.setLocationRelativeTo(null);                    // Load on center of the screen
	}
	
	/**
	 * Set listener
	 * @param abIDElistener
	 */
	public void setDevGUIListener(DevGuiListener abIDElistener) {
		this.devGuilistener = abIDElistener;
	}
}
