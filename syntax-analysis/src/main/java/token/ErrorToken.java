package token;

/**
 * Class representing an error lexicalToken
 * Objects of this class should only be created for LR grammar
 */

public class ErrorToken extends AbstractSyntaxToken {
    private AbstractToken lexicalToken;

    public ErrorToken(String value) {
        super(value);
    }

    /**
     * Copy constructor
     * @param errorToken
     */
    public ErrorToken(ErrorToken errorToken) {
        super(errorToken);
        this.lexicalToken = errorToken.lexicalToken;
    }

    public AbstractToken getLexicalToken() {
        return lexicalToken;
    }

    public void setLexicalToken(AbstractToken lexicalToken) {
        this.lexicalToken = lexicalToken;
    }

    @Override
    public String getValue() {
        return getOriginalValue().substring(1, getOriginalValue().length()-1);
    }

    @Override
    public AbstractSyntaxToken copy() {
        return new ErrorToken(this);
    }
}
