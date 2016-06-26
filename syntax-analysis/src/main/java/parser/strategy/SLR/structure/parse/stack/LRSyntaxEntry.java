package parser.strategy.SLR.structure.parse.stack;

import token.AbstractSyntaxToken;

/**
 * Stack entry containing a syntax token and a node
 */

public class LRSyntaxEntry extends LRAbstractStackEntry {
    private AbstractSyntaxToken syntaxToken;

    public AbstractSyntaxToken getSyntaxToken() {
        return syntaxToken;
    }

    public void setSyntaxToken(AbstractSyntaxToken syntaxToken) {
        this.syntaxToken = syntaxToken;
    }
}
