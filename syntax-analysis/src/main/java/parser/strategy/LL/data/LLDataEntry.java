package parser.strategy.LL.data;

import token.AbstractSyntaxToken;
import token.AbstractToken;

import java.util.List;

/**
 * Data structure for storing a step in the LL PP syntax tokens data
 */

public abstract class LLDataEntry {

    // Components
    private int stepNumber;
    private List<AbstractSyntaxToken> stackSyntax;
    private AbstractToken lexicalToken;

    public LLDataEntry(int stepNumber, List<AbstractSyntaxToken> stackSyntax, AbstractToken lexicalToken) {
        this.stepNumber = stepNumber;
        this.stackSyntax = stackSyntax;
        this.lexicalToken = lexicalToken;
    }

    /**
     * Get the step number
     * @return step number
     */
    public int getStepNumber() {
        return stepNumber;
    }

    /**
     * Get stack content
     * @return stack content
     */
    public String getStackContent() {
        String content = "";
        for(AbstractSyntaxToken syntaxToken : stackSyntax){
            content += syntaxToken.getOriginalValue() + " ";
        }
        return content;
    }

    /**
     * Get remaining user input
     * @return user input
     */
    public String getInputContent() {
        String content = "";
        AbstractToken tokenIter = lexicalToken;
        while(tokenIter != null) {
            content += tokenIter.getValue() + " ";
            tokenIter = tokenIter.getNext();
        }
        return content;
    }

    @Override
    public String toString() {
        return "Step: " + stepNumber + ", Stack: " + getStackContent() + ", Input: " + getInputContent();
    }
}
