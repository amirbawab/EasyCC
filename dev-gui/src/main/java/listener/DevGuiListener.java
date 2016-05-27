package listener;

import data.GenericTable;
import data.LexicalAnalysisRow;
import data.structure.ConsoleData;

import javax.swing.*;

public interface DevGuiListener {
	
	// Console
	public void lexicalAnalysis(String string);
	public ConsoleData<LexicalAnalysisRow> getLexicalAnalyzerOutput();
	public ConsoleData<LexicalAnalysisRow> getLexicalAnalyzerError();
	public void parse();
	public Object[][] getParserOutput();
	public Object[][] getParserError();
	public long getLexicalAnalysisTime();
	public long getParserTime();
	public boolean doesCompile();
	public Object[][][] getSymbolTables();
	public String getSymbolTableName(int id);
	public Object[][] getSemanticErrors();
	public JPanel getParserTree();
	public String getGeneratedCode();
	
	// Menu
	public void setStateTable(GenericTable genericTable);
	public Object[][] getParsingTable();
	public Object[][] getParsingTableRules();
	public Object[][] getParsingTableErrors();
	public void setFirstAndFollowSets(GenericTable genericTable);
	
}
