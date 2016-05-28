package parser.strategy.LLPP.cell;

import token.AbstractSyntaxToken;

import java.util.List;

/**
 * A table cell carrying information about a rule
 */

public class LLPPRuleCell extends LLPPAbstractTableCell {

    // Assign a unique id for each cell
    private static int uniqueId = 0;
    private List<AbstractSyntaxToken> production;
    private String nonTerminal;
    private int id;

    public LLPPRuleCell(String nonTerminal, List<AbstractSyntaxToken> production) {
        id = uniqueId++;
        this.production = production;
        this.nonTerminal = nonTerminal;
    }

    public List<AbstractSyntaxToken> getProduction() {
        return production;
    }

    /**
     * Get the LHS non-terminal
     * @return LHS non-terminal
     */
    public String getNonTerminal() {
        return nonTerminal;
    }

    /**
     * Get rule id
     * @return id
     */
    public int getId() {
        return id;
    }
}
