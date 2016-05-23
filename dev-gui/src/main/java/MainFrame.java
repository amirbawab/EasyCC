import bottom.BottomPanel;
import center.CenterPanel;
import data.LexicalAnalysisRow;
import data.structure.ConsoleData;
import listener.DevGuiListener;
import menu.MainMenu;
import menu.dialogs.StateMachineDialog;
import menu.dialogs.FirstFollowDialog;
import menu.dialogs.ParsingTableDialog;
import menu.dialogs.ParsingTableErrorsDialog;
import menu.dialogs.ParsingTableRulesDialog;
import menu.dialogs.StateTableDialog;
import menu.dialogs.StateTableInfoDialog;
import menu.listeners.MainMenuListener;
import tool.ToolBarPanel;
import tool.ToolBarPanel.Button;
import tool.listeners.ClickListener;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;

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
							Object[][] parserOutputData = devGuilistener.getParserOutput();
							centerPanel.setTableData(CenterPanel.PARSER_OUTPUT_TITLE, parserOutputData);
							
							// Parser error
							Object[][] parserErrorData = devGuilistener.getParserError();
							centerPanel.setTableData(CenterPanel.PARSER_ERROR_TITLE, parserErrorData);
							
							// If parser error found, update compiler message
							if(parserErrorData.length > 0)
								message += String.format("Parser: %d error(s) found! ", parserErrorData.length);

							// Get tree
							centerPanel.setPanel(CenterPanel.PARSER_TREE_TITLE, devGuilistener.getParserTree());

							// Clear symbol tables
							centerPanel.removeTablesInNavigationTable(CenterPanel.SYMBOL_TABLE_TITLE);

							// Clear semantic errors
							centerPanel.resetTable(CenterPanel.SEMANTIC_ERROR_TITLE);

							// Clear code generation
							centerPanel.setText(CenterPanel.CODE_GENERATION_TITLE, null);

							// If no parsing errors
							if(parserErrorData.length == 0){
								Object[][][] symbolTables = devGuilistener.getSymbolTables();
								for(int i=0; i < symbolTables.length; i++) {
									String subTableName = devGuilistener.getSymbolTableName(i);
									centerPanel.addTableToNavigationTable(CenterPanel.SYMBOL_TABLE_TITLE, subTableName, CenterPanel.SYMBOL_TABLE_HEADER);
									centerPanel.setTableOfTableData(CenterPanel.SYMBOL_TABLE_TITLE, subTableName, symbolTables[i]);
								}

								// Semantic errors
								Object[][] semanticErrorData = devGuilistener.getSemanticErrors();
								centerPanel.setTableData(CenterPanel.SEMANTIC_ERROR_TITLE, semanticErrorData);

								// Code generation
								centerPanel.setText(CenterPanel.CODE_GENERATION_TITLE, devGuilistener.getGeneratedCode());

								// If semantic errors, update compiler message
								if(semanticErrorData.length > 0)
									message	+= String.format("Semantic: %d error(s) found! ", semanticErrorData.length);
							}

							// Insert time
							message += String.format("Total time: %d ms", compilationTime);
							
							// Set message
							bottomPanel.setCompilerMessageText(message);
							
							// Change color
							if(devGuilistener.doesCompile())
								bottomPanel.setStyle(BottomPanel.Style.SUCCESS);
							else
								bottomPanel.setStyle(BottomPanel.Style.ERROR);
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
					new StateMachineDialog(MainFrame.this);
					break;

				case FIRST_FOLLOW:
					
					if(devGuilistener != null) {
						
						// Get data
						Object[][] firstFollowData = devGuilistener.getFirstAndFollowSets();
						
						if(firstFollowData.length > 0)
							new FirstFollowDialog(MainFrame.this, firstFollowData);
					}
					break;

				case PARSING_TABLE:
					if(devGuilistener != null) {
						
						// Get data
						Object[][] parsingData = devGuilistener.getParsingTable();
						Object[][] parsingRulesData = devGuilistener.getParsingTableRules();
						Object[][] parsingErrorData = devGuilistener.getParsingTableErrors();
						
						if(parsingData.length > 0) {
							new ParsingTableDialog(MainFrame.this, parsingData);
							new ParsingTableRulesDialog(MainFrame.this, parsingRulesData);
							new ParsingTableErrorsDialog(MainFrame.this, parsingErrorData);
						}
					}
					break;

				case STATE_TABLE:
					
					if(devGuilistener != null) {
						
						// Get data
						Object[][] stateTableData = devGuilistener.getStateTable();
						
						if(stateTableData.length > 0) {
							new StateTableDialog(MainFrame.this, stateTableData);
							new StateTableInfoDialog(MainFrame.this);
						}
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
