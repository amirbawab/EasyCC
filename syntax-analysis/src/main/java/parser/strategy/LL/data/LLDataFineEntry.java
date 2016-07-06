package parser.strategy.LL.data;

import token.AbstractSyntaxToken;
import token.AbstractToken;
import token.NonTerminalToken;

import java.util.List;

/**
 * Non error data entry
 */

public class LLDataFineEntry extends LLDataEntry {

    // Components
    private NonTerminalToken productionLHS;
    private List<AbstractSyntaxToken> productionRHS;
    private List<AbstractSyntaxToken> derivation;

    public LLDataFineEntry(int stepNumber, List<AbstractSyntaxToken> stackSyntax, AbstractToken lexicalToken, NonTerminalToken productionLHS, List<AbstractSyntaxToken> productionRHS, List<AbstractSyntaxToken> derivation) {
        super(stepNumber, stackSyntax, lexicalToken);
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
            String content = productionLHS.getValue() + " =>";
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
            for (AbstractSyntaxToken syntaxToken : derivation) {
                content += syntaxToken.getValue() + " ";
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
