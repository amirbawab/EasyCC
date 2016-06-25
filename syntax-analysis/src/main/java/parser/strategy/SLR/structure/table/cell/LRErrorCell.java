package parser.strategy.SLR.structure.table.cell;

/**
 * Represent an error cell in the LR Table
 */

public class LRErrorCell extends LRAbstractTableCell{

    public enum Type {
        POP,
        SCAN,
        PUSH
    }

    private Type decision;
    private String nonTerminal;
    private String message;

    public LRErrorCell(Type decision, String message) {
        this.decision = decision;
        this.message = message;
    }

    public Type getDecision() {
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
