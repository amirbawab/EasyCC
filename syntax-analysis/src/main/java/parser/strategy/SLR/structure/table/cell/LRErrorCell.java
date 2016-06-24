package parser.strategy.SLR.structure.table.cell;

/**
 * Represent an error cell in the LR Table
 */

public class LRErrorCell extends LRAbstractTableCell{
    public static final int POP = 1;
    public static final int SCAN = 2;

    private int decision;
    private String nonTerminal;
    private String message;

    public LRErrorCell(int decision, String message) {
        this.decision = decision;
        this.message = message;
    }

    public int getDecision() {
        return decision;
    }

    public String getNonTerminal() {
        return nonTerminal;
    }

    public void setNonTerminal(String nonTerminal) {
        this.nonTerminal = nonTerminal;
    }

    public String getMessage() {
        return message;
    }
}
