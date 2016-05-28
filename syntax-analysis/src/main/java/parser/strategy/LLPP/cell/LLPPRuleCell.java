package parser.strategy.LLPP.cell;

/**
 * A table cell carrying information about a rule
 */

public class LLPPRuleCell extends LLPPAbstractTableCell {

    // Assign a unique id for each cell
    private static int uniqueId = 0;

    public LLPPRuleCell() {
        super(uniqueId++);
    }

    @Override
    public String getId() {
        return "R" + id;
    }
}
