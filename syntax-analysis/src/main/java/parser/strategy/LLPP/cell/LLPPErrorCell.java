package parser.strategy.LLPP.cell;

/**
 * A table cell carrying information about an error
 */

public class LLPPErrorCell extends LLPPAbstractTableCell {

    // Assign a unique id for each cell
    private static int uniqueId = 0;

    // Constants
    public static final int POP = 1;
    public static final int SCAN = 2;

    // POP | SCAN
    private int decision;

    public LLPPErrorCell(int decision) {
        super(uniqueId++);
        this.decision = decision;
    }

    public int getDecision() {
        return decision;
    }

    @Override
    public String getId() {
        return "E" + id;
    }

    @Override
    public String toString() {
        return getId() + " - " + (decision == POP ? "Pop" : "Scan");
    }
}
