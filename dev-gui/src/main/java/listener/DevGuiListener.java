package listener;

import data.GenericTable;
import data.LexicalAnalysisRow;
import data.structure.ConsoleData;

import javax.swing.*;

public interface DevGuiListener {
	
	// Console
	void lexicalAnalysis(String string);
	ConsoleData<LexicalAnalysisRow> getLexicalAnalyzerOutput();
	ConsoleData<LexicalAnalysisRow> getLexicalAnalyzerError();
	void parse();
	Object[][] getParserOutput();
	Object[][] getParserError();
	long getLexicalAnalysisTime();
	long getParserTime();
	boolean doesCompile();
	Object[][][] getSymbolTables();
	String getSymbolTableName(int id);
	Object[][] getSemanticErrors();
	JPanel getParserTree();
	String getGeneratedCode();
	
	// Menu
	void setStateTable(GenericTable genericTable);
	void setLLPPTable(GenericTable genericTable);
	void setLLPPTableRules(GenericTable genericTable);
	void setLLPPTableErrors(GenericTable genericTable);
	void setFirstAndFollowSets(GenericTable genericTable);
	
}
