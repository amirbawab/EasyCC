package token;

/**
 * Class representing a terminal token
 */

public class TerminalToken extends AbstractSyntaxToken {
    public TerminalToken(String value) {
        super(value);
    }

    @Override
    public String getValue() {
        return getOriginalValue().substring(1, getOriginalValue().length()-1);
    }
}
