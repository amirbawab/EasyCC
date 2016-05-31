package parser.strategy.LLPP.table.cell;

import token.AbstractSyntaxToken;

import java.util.List;
import java.util.stream.Collectors;

/**
 * A table cell carrying information about a rule
 */

public class LLPPRuleCell extends LLPPAbstractTableCell {

    // Assign a unique id for each cell
    private static int uniqueId = 0;
    private List<AbstractSyntaxToken> rule;
    private String nonTerminal;
    private int id;

    public LLPPRuleCell(String nonTerminal, List<AbstractSyntaxToken> rule) {
        id = uniqueId++;
        this.rule = rule;
        this.nonTerminal = nonTerminal;
    }

    /**
     * Get rule
     * Note: This method should be used carefully.
     * Usually a copy of the rule is what is really needed.
     * @see #getRuleCopy()
     * @return rule
     */
    public List<AbstractSyntaxToken> getRule() {
        return rule;
    }

    /**
     * Get a copy of a rule
     * @return rule
     */
    public List<AbstractSyntaxToken> getRuleCopy() {
        return rule.stream().map(AbstractSyntaxToken::copy).collect(Collectors.toList());
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
