package parser.strategy.SLR.structure.table.cell;

/**
 * Represent an error cell in the LR Table
 */

public class LRErrorCell extends LRAbstractTableCell{

    private String message;

    public LRErrorCell(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
