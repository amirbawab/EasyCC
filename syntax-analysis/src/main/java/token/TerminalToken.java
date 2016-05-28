package token;

/**
 * Class representing a terminal lexicalToken
 */

public class TerminalToken extends AbstractSyntaxToken {
    public TerminalToken(String value) {
        super(value);
    }

    private LexicalToken lexicalToken;

    public LexicalToken getLexicalToken() {
        return lexicalToken;
    }

    public void setLexicalToken(LexicalToken lexicalToken) {
        this.lexicalToken = lexicalToken;
    }

    @Override
    public String getValue() {
        return getOriginalValue().substring(1, getOriginalValue().length()-1);
    }
}
