package parser.strategy.LLPP;

import com.bethecoder.ascii_table.ASCIITable;
import config.SyntaxConfig;
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
    private Map<String, Integer> messageCellMap;
    private Grammar grammar;

    public LLPPTable(Grammar grammar) {
        table = new LLPPAbstractTableCell[grammar.getNonTerminals().size()][grammar.getTerminals().size()];
        terminalIndexMap = new HashMap<>();
        nonTerminalIndexMap = new HashMap<>();
        ruleCellList = new ArrayList<>();
        messageCellMap = new LinkedHashMap<>();
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

                // Copy into terminal set
                Set<String> ruleFirstSet = grammar.getRuleFirstSetMap().get(production);
                for (String str : ruleFirstSet) {
                    if (!str.equals(SyntaxHelper.EPSILON)) {
                        terminalsSet.add(str);
                    }
                }

                // If has epsilon
                if(ruleFirstSet.contains(SyntaxHelper.EPSILON)) {
                    Set<String> followSet = grammar.getFollowSetMap().get(pair.getKey());
                    terminalsSet.addAll(followSet);
                }

                // Prepare new rule
                LLPPRuleCell ruleCell = new LLPPRuleCell(nonTerminal, production);
                ruleCellList.add(ruleCell);

                // Add cells
                for(String terminal : terminalsSet){
                    table[nonTerminalIndexMap.get(nonTerminal)][terminalIndexMap.get(terminal)] = ruleCell;
                }
            }
        }

        // Create entry for the default message
        messageCellMap.put(SyntaxConfig.getInstance().getSyntaxMessageConfig().getDefaultMessage(), 0);

        // Put error message for all the remaining cells
        for(String nonTerminal : grammar.getNonTerminals()) {
            for(String terminal : grammar.getTerminals()) {
                if(table[nonTerminalIndexMap.get(nonTerminal)][terminalIndexMap.get(terminal)] == null) {

                    int decision = LLPPErrorCell.SCAN;

                    // If terminal is in the follow set or it's EOS, then it's a pop
                    if(terminal.equals(SyntaxHelper.END_OF_STACK) || grammar.getFollowSetMap().get(nonTerminal).contains(terminal))
                        decision = LLPPErrorCell.POP;

                    String message = SyntaxConfig.getInstance().getMessage(nonTerminal, terminal);
                    messageCellMap.putIfAbsent(message, messageCellMap.size());

                    table[nonTerminalIndexMap.get(nonTerminal)][terminalIndexMap.get(terminal)] = new LLPPErrorCell(decision, message);
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
     * Get all rules
     * @return rules list
     */
    public List<LLPPRuleCell> getRulesList() {
        return ruleCellList;
    }

    /**
     * Get all errors
     * @return errors set
     */
    public Set<String> getMessagesSet() {
        return messageCellMap.keySet();
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

                if(table[row][col-1] instanceof LLPPRuleCell) {
                    LLPPRuleCell cell = (LLPPRuleCell) table[row][col-1];
                    data[row][col] = "R" + cell.getId();

                } else if(table[row][col-1] instanceof LLPPErrorCell) {
                    LLPPErrorCell cell = (LLPPErrorCell) table[row][col-1];
                    data[row][col] = "E" + messageCellMap.get(cell.getMessage()) + " - " + (cell.getDecision() == LLPPErrorCell.POP ? "Pop" : "Scan");
                }
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

        output += "\nERRORS:\n";
        for(String message : messageCellMap.keySet()) {
            output += messageCellMap.get(message) + ": " + message + "\n";
        }

        return output;
    }
}
