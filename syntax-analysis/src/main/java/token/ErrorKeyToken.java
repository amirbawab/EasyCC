package token;

/**
 * Class representing an error token
 * Objects of this class should only be created for LR grammar
 */

public class ErrorKeyToken extends AbstractSyntaxToken {
    private AbstractToken lexicalToken;

    public ErrorKeyToken(String value) {
        super(value);
    }

    /**
     * Copy constructor
     * @param errorKeyToken
     */
    public ErrorKeyToken(ErrorKeyToken errorKeyToken) {
        super(errorKeyToken);
        this.lexicalToken = errorKeyToken.lexicalToken;
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
        return new ErrorKeyToken(this);
    }
}
