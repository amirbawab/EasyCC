package parser.strategy.SLR.data;

import parser.strategy.SLR.structure.parse.stack.LRAbstractStackEntry;
import parser.strategy.SLR.structure.parse.stack.LRLexicalEntry;
import parser.strategy.SLR.structure.parse.stack.LRSyntaxEntry;
import token.AbstractSyntaxToken;
import token.AbstractToken;
import token.NonTerminalToken;

import java.util.List;

/**
 * Non error data entry
 */

public class LRDataFineEntry extends LRDataEntry {

    // Components
    private LRSyntaxEntry productionLHS;
    private List<AbstractSyntaxToken> productionRHS;
    private List<LRAbstractStackEntry> derivation;

    public LRDataFineEntry(int stepNumber, List<LRAbstractStackEntry> parseStack, AbstractToken lexicalToken, LRSyntaxEntry productionLHS, List<AbstractSyntaxToken> productionRHS, List<LRAbstractStackEntry> derivation) {
        super(stepNumber, parseStack, lexicalToken);
        this.productionLHS = productionLHS;
        this.productionRHS = productionRHS;
        this.derivation = derivation;
    }

    /**
     * Get production content
     * @return production | empty string
     */
    public String getProductionContent() {
        if(productionLHS != null) {
            String content = productionLHS.getSyntaxToken().getValue() + " =>";
            for (AbstractSyntaxToken syntaxToken : productionRHS) {
                content += " " + syntaxToken.getValue();
            }
            return content;
        }
        return "";
    }

    /**
     * Get derivation content
     * @return derivation content | empty string
     */
    public String getDerivationContent() {
        if(derivation != null) {
            String content = "";
            for (LRAbstractStackEntry stackEntry : derivation) {
                if(stackEntry instanceof LRLexicalEntry) {
                    content += ((LRLexicalEntry) stackEntry).getLexicalToken().getValue() + " ";

                } else if(stackEntry instanceof LRSyntaxEntry) {
                    content += ((LRSyntaxEntry) stackEntry).getSyntaxToken().getOriginalValue() + " ";
                }
            }
            return content;
        }
        return "";
    }

    @Override
    public String toString() {
        return super.toString() + ", Production: " + getProductionContent() + ", Derivation: " + getDerivationContent();
    }
}
