package parser.strategy.LLPP.table.cell;

/**
 * A table cell carrying information about an error
 */

public class LLPPErrorCell extends LLPPAbstractTableCell {

    // Constants
    public static final int POP = 1;
    public static final int SCAN = 2;

    // POP | SCAN
    private int decision;
    private String message;

    public LLPPErrorCell(int decision, String message) {
        this.decision = decision;
        this.message = message;
    }

    /**
     * Get decision
     * @return decision
     */
    public int getDecision() {
        return decision;
    }

    /**
     * Get error message
     * @return error message
     */
    public String getMessage() {
        return message;
    }
}
