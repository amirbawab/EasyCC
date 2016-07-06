package parser.strategy.LL.data;

import token.AbstractSyntaxToken;
import token.AbstractToken;

import java.util.List;

/**
 * Error entry
 */

public class LLDataErrorEntry extends LLDataEntry {

    // Component
    private String message;

    public LLDataErrorEntry(int stepNumber, List<AbstractSyntaxToken> stackSyntax, AbstractToken lexicalToken, String message) {
        super(stepNumber, stackSyntax, lexicalToken);
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
