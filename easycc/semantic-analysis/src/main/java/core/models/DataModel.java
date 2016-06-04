package core.models;

import token.AbstractToken;

/**
 * All classes that require the lexical token to be accessible from the model should inherit this class.
 * For example, if the action is for storing an integer into the semantic stack, then the integer model class
 * should inherit from this class.
 *
 * If no model is specified for a semantic action, then a data model will be created
 */

public class DataModel extends GenericModel {
    protected AbstractToken lexicalToken;

    public AbstractToken getLexicalToken() {
        return lexicalToken;
    }

    public void setLexicalToken(AbstractToken lexicalToken) {
        this.lexicalToken = lexicalToken;
    }
}
