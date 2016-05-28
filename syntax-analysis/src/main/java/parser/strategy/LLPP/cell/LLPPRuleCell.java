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

    public LLPPRuleCell(String nonTerminal, List<AbstractSyntaxToken> production) {
        super(uniqueId++);
        this.production = production;
        this.nonTerminal = nonTerminal;
    }

    public List<AbstractSyntaxToken> getProduction() {
        return production;
    }

    public String getNonTerminal() {
        return nonTerminal;
    }

    @Override
    public String getId() {
        return "R" + id;
    }

    @Override
    public String toString() {
        return getId();
    }
}
