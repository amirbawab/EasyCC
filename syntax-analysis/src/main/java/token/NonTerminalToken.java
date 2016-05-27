package token;

/**
 * Class representing a non-terminal token
 */

public class NonTerminalToken extends AbstractSyntaxToken {
    public NonTerminalToken(String value) {
        super(value);
    }

    @Override
    public String getValue() {
        return getOriginalValue();
    }
}
