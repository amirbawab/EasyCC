package core.models;

import token.AbstractToken;

/**
 * A generic model from which all other models should inherit
 */

public class GenericModel {

    protected AbstractToken lexicalToken;

    public AbstractToken getLexicalToken() {
        return lexicalToken;
    }

    public void setLexicalToken(AbstractToken lexicalToken) {
        this.lexicalToken = lexicalToken;
    }
}
