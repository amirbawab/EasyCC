package parser.strategy.SLR.structure.parse.stack;

import token.AbstractToken;

/**
 * Stack entry containing a lexical token and a node
 */

public class LRLexicalEntry extends LRAbstrackStackEntry {
    private AbstractToken lexicalToken;

    public AbstractToken getLexicalToken() {
        return lexicalToken;
    }

    public void setLexicalToken(AbstractToken lexicalToken) {
        this.lexicalToken = lexicalToken;
    }
}
