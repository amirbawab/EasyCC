package data;

import data.structure.ConsoleDataRow;

/**
 * Class representing the data that will be visible in the console
 */
public class LexicalAnalysisRow extends ConsoleDataRow {
    private Object token;
    private Object value;
    private Object row;
    private Object col;
    private Object position;
    private Object message;

    public LexicalAnalysisRow(Object token, Object value, Object row, Object col, Object position) {
        this(token, value, row, col, position, null);
    }

    public LexicalAnalysisRow(Object token, Object value, Object row, Object col, Object position, Object message) {
        this.token = token;
        this.value = value;
        this.row = row;
        this.col = col;
        this.position = position;
        this.message = message;
    }

    @Override
    public Object[] convertToObjectArray() {
        if(message == null)
            return new Object[] {token, value, row, col, position};
        return new Object[] {token, value, row, col, position, message};
    }
}
