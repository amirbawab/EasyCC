package token;

/**
 * Class representing a terminal lexicalToken
 */

public class TerminalToken extends AbstractSyntaxToken {
    private AbstractToken lexicalToken;

    public TerminalToken(String value) {
        super(value);
    }

    /**
     * Copy constructor
     * @param terminalToken
     */
    public TerminalToken(TerminalToken terminalToken) {
        super(terminalToken);
        this.lexicalToken = terminalToken.lexicalToken;
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
        return new TerminalToken(this);
    }
}
