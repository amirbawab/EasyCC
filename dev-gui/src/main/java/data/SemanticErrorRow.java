package data;

import data.structure.ConsoleDataRow;

/**
 * Semantic error console row
 */

public class SemanticErrorRow extends ConsoleDataRow {

    private Object message;

    public SemanticErrorRow(Object message) {
        this.message = message;
    }

    @Override
    public Object[] convertToObjectArray() {
        return new Object[]{message};
    }
}
