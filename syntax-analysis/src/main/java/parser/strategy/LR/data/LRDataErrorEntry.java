package parser.strategy.LR.data;

import parser.strategy.LR.structure.parse.stack.LRAbstractStackEntry;
import token.AbstractToken;

import java.util.List;

/**
 * Error entry
 */

public class LRDataErrorEntry extends LRDataEntry {

    // Component
    private String message;

    public LRDataErrorEntry(int stepNumber, List<LRAbstractStackEntry> parseStack, AbstractToken lexicalToken, String message) {
        super(stepNumber, parseStack, lexicalToken);
        this.message = message;
    }

    /**
     * Get error message
     * @return message
     */
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return super.toString() + ", Message: " + getMessage();
    }
}
