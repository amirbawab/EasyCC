package parser.strategy.SLR.data;

import parser.strategy.SLR.structure.parse.stack.LRAbstractStackEntry;
import parser.strategy.SLR.structure.parse.stack.LRLexicalEntry;
import parser.strategy.SLR.structure.parse.stack.LRSyntaxEntry;
import token.AbstractSyntaxToken;
import token.AbstractToken;

import java.util.List;

/**
 * Data structure for storing a step in LR
 */

public abstract class LRDataEntry {

    // Components
    private int stepNumber;
    private List<LRAbstractStackEntry> stackSyntax;
    private AbstractToken lexicalToken;

    public LRDataEntry(int stepNumber, List<LRAbstractStackEntry> stackSyntax, AbstractToken lexicalToken) {
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
        for(LRAbstractStackEntry stackEntry : stackSyntax){
            if(stackEntry instanceof LRSyntaxEntry) {
                LRSyntaxEntry syntaxEntry = (LRSyntaxEntry) stackEntry;
                content += syntaxEntry.getSyntaxToken().getOriginalValue() + " " + syntaxEntry.getNode().getId() + " ";
            } else if(stackEntry instanceof LRLexicalEntry) {
                LRLexicalEntry lexicalEntry = (LRLexicalEntry) stackEntry;
                content += lexicalEntry.getLexicalToken().getValue() + " " + lexicalEntry.getNode().getId() + " ";
            }
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
