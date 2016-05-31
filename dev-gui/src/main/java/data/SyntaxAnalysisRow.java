package data;

import data.structure.ConsoleDataRow;

/**
 * Class representing the data that will be visible in the console
 */

public class SyntaxAnalysisRow extends ConsoleDataRow {
    private Object step;
    private Object stack;
    private Object input;
    private Object production;
    private Object derivation;

    public SyntaxAnalysisRow(Object step, Object stack, Object input, Object production, Object derivation) {
        this.step = step;
        this.stack = stack;
        this.input = input;
        this.production = production;
        this.derivation = derivation;
    }

    @Override
    public Object[] convertToObjectArray() {
        return new Object[] {step, stack, input, production, derivation};
    }
}
