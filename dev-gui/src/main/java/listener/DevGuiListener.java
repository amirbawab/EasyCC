package listener;

import data.GenericTable;
import data.SyntaxAnalysisRow;
import data.LexicalAnalysisRow;
import data.structure.ConsoleData;

import javax.swing.*;
import java.util.List;

public interface DevGuiListener {
	
	// Console
	void lexicalAnalysis(String string);
	ConsoleData<LexicalAnalysisRow> getLexicalAnalyzerOutput();
	ConsoleData<LexicalAnalysisRow> getLexicalAnalyzerError();
	void parse();
	ConsoleData<SyntaxAnalysisRow> getParserOutput();
	List<String> getSyntaxErrorMessages();
	long getLexicalAnalysisTime();
	long getParserTime();
	boolean doesCompile();
	Object[][][] getSymbolTables();
	String getSymbolTableName(int id);
	Object[][] getSemanticErrors();
	JPanel getDerivationTree();
	String getGeneratedCode();
	
	// Menu
	void setStateTable(GenericTable genericTable);
	void setLLPPTable(GenericTable genericTable);
	void setLLPPTableRules(GenericTable genericTable);
	void setLLPPTableErrors(GenericTable genericTable);
	void setFirstAndFollowSets(GenericTable genericTable);
	JPanel getStateMachineGraph();
	
}
