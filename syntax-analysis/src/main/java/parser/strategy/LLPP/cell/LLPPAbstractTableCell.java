package parser.strategy.LLPP.cell;

/**
 * Abstract table cell
 */

public abstract class LLPPAbstractTableCell {

    protected int id;

    public LLPPAbstractTableCell(int id) {
        this.id = id;
    }

    public abstract String getId();
}
