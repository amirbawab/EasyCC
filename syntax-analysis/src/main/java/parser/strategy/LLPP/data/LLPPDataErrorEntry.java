package parser.strategy.LLPP.data;

import token.AbstractSyntaxToken;
import token.AbstractToken;

import java.util.List;

/**
 * Error entry
 */

public class LLPPDataErrorEntry extends LLPPDataEntry {

    // Component
    private String message;

    public LLPPDataErrorEntry(int stepNumber, List<AbstractSyntaxToken> stackSyntax, AbstractToken lexicalToken, String message) {
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
