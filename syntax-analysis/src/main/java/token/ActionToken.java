package token;

/**
 * Class representing an action token
 */

public class ActionToken extends AbstractSyntaxToken {
    public ActionToken(String value) {
        super(value);
    }

    @Override
    public String getValue() {
        return getOriginalValue().substring(1, getOriginalValue().length()-1);
    }
}
