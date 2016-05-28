package parser.strategy.LLPP;

import com.bethecoder.ascii_table.ASCIITable;
import grammar.Grammar;
import helper.SyntaxHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import parser.strategy.LLPP.cell.LLPPAbstractTableCell;
import parser.strategy.LLPP.cell.LLPPErrorCell;
import parser.strategy.LLPP.cell.LLPPRuleCell;
import token.AbstractSyntaxToken;

import java.util.*;

/**
 * This class represent the table structure and data. It's used by the predictive parser to decide what
 * would be the next step
 */

public class LLPPTable {

    // Logger
    private Logger l = LogManager.getFormatterLogger(getClass());

    private LLPPAbstractTableCell table[][];
    private Map<String, Integer> terminalIndexMap, nonTerminalIndexMap;
    private List<LLPPRuleCell> ruleCellList;
    private List<LLPPErrorCell> errorCellList;
    private Grammar grammar;

    public LLPPTable(Grammar grammar) {
        table = new LLPPAbstractTableCell[grammar.getNonTerminals().size()][grammar.getTerminals().size()];
        terminalIndexMap = new HashMap<>();
        nonTerminalIndexMap = new HashMap<>();
        ruleCellList = new ArrayList<>();
        errorCellList = new ArrayList<>();
        this.grammar = grammar;

        // Cache terminal index
        for(String terminal : grammar.getTerminals())
            terminalIndexMap.put(terminal, terminalIndexMap.size());

        // Cache non-terminal index
        for(String nonTerminal : grammar.getNonTerminals())
            nonTerminalIndexMap.put(nonTerminal, nonTerminalIndexMap.size());

        buildTable();
    }

    /**
     * Build table
     */
    private void buildTable() {

        // Rules iterator
        Iterator<Map.Entry<String, List<List<AbstractSyntaxToken>>>> it = grammar.getProductions().entrySet().iterator();

        // While more productions
        while (it.hasNext()) {

            // Cache
            Map.Entry<String, List<List<AbstractSyntaxToken>>> pair = it.next();

            // Non terminal
            String nonTerminal = pair.getKey();

            // Loop on productions
            for(List<AbstractSyntaxToken> production : pair.getValue()) {

                // Prepare hash set
                Set<String> terminalsSet = new HashSet<>();

                // Set i
                int i = 0;

                // Loop on production tokens
                for(; i < production.size(); ++i) {

                    // Get first set
                    Set<String> firstSet = grammar.getFirstSetOf(production.get(i));

                    // If first set exists
                    if(firstSet != null) {

                        // Copy into terminal set
                        for (String str : firstSet)
                            if (!str.equals(SyntaxHelper.EPSILON))
                                terminalsSet.add(str);

                        // If doesn't have epsilon
                        if (!firstSet.contains(SyntaxHelper.EPSILON))
                            break;
                    }
                }

                // If the first(last token) has epsilon
                if(i == production.size()) {
                    Set<String> followSet = grammar.getFollowSetMap().get(pair.getKey());
                    terminalsSet.addAll(followSet);
                }

                // Prepare new rule
                LLPPRuleCell ruleCell = new LLPPRuleCell(nonTerminal, production);
                ruleCellList.add(ruleCell);

                // Add cells
                for(String terminal : terminalsSet){

                    // Log
                    if(!nonTerminalIndexMap.containsKey(nonTerminal)) {
                        l.error("Non terminal '%s' not found in table", nonTerminal);
                    }

                    // Log
                    if(!terminalIndexMap.containsKey(terminal)) {
                        l.error("Terminal '%s' not found in table", terminal);
                    }

                    table[nonTerminalIndexMap.get(nonTerminal)][terminalIndexMap.get(terminal)] = ruleCell;
                }
            }
        }

        // Put error message for all the remaining cells
        for(String nonTerminal : grammar.getNonTerminals()) {
            for(String terminal : grammar.getTerminals()) {
                if(table[nonTerminalIndexMap.get(nonTerminal)][terminalIndexMap.get(terminal)] == null) {

                    int decision = LLPPErrorCell.SCAN;

                    // If terminal is in the follow set or it's EOS, then it's a pop
                    if(terminal.equals(SyntaxHelper.END_OF_STACK) || grammar.getFollowSetMap().get(nonTerminal).contains(terminal))
                        decision = LLPPErrorCell.POP;

                    LLPPErrorCell errorCell = new LLPPErrorCell(decision);
                    table[nonTerminalIndexMap.get(nonTerminal)][terminalIndexMap.get(terminal)] = errorCell;
                }
            }
        }
    }

    /**
     * Get table cell at [nonterminal] [terminal]
     * @param nonTerminal
     * @param terminal
     * @return Table cell
     */
    public LLPPAbstractTableCell getCell(String nonTerminal, String terminal) {
        return table[nonTerminalIndexMap.get(nonTerminal)][terminalIndexMap.get(terminal)];
    }

    /**
     * Prettify predictive parser table data
     * @return Object table data
     */
    public String[][] prettifyPPTableData() {
        String[][] data = new String[table.length][grammar.getTerminals().size()+1];

        for(String nonTerminal : grammar.getNonTerminals()) {
            data[nonTerminalIndexMap.get(nonTerminal)][0] = nonTerminal;
            for(String terminal : grammar.getTerminals()) {
                int row = nonTerminalIndexMap.get(nonTerminal);
                int col = terminalIndexMap.get(terminal)+1;
                data[row][col] = table[row][col-1].toString();
            }
        }
        return data;
    }

    /**
     * Prettify predictive parser table header
     * @return Object table header
     */
    public String[] prettifyPPTableHeader() {
        String[] modHeader = new String[grammar.getTerminals().size() + 1];

        // Create header
        modHeader[0] = "";
        for(String terminal : grammar.getTerminals()) {
            modHeader[terminalIndexMap.get(terminal)+1] = terminal;
        }
        return modHeader;
    }

    /**
     * Formatted table
     */
    public String toString() {
        String output = ASCIITable.getInstance().getTable(prettifyPPTableHeader(), prettifyPPTableData());

        output += "RULES:\n";
        for(LLPPRuleCell ruleCell : ruleCellList) {
            output += ruleCell.getId() + ": " + ruleCell.getNonTerminal() + " => " + StringUtils.join(ruleCell.getProduction(), " ") + "\n";
        }

        return output;
    }
}
