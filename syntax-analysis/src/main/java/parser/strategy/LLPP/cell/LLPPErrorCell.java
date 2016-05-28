package parser.strategy.LLPP.cell;

/**
 * A table cell carrying information about an error
 */

public class LLPPErrorCell extends LLPPAbstractTableCell {

    // Assign a unique id for each cell
    private static int uniqueId = 0;

    public LLPPErrorCell() {
        super(uniqueId++);
    }

    @Override
    public String getId() {
        return "E" + id;
    }
}
