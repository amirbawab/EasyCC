package token;

/**
 * Action token for LL parser
 */

public class LLActionToken extends ActionToken {
    private AbstractToken lexicalToken;

    public LLActionToken(String value) {
        super(value);
    }

    public LLActionToken(LLActionToken llActionToken) {
        super(llActionToken);
        lexicalToken = llActionToken.lexicalToken;
    }

    public AbstractToken getLexicalToken() {
        return lexicalToken;
    }

    public void setLexicalToken(AbstractToken lexicalToken) {
        this.lexicalToken = lexicalToken;
    }

    @Override
    public AbstractSyntaxToken copy() {
        return new LLActionToken(this);
    }
}
