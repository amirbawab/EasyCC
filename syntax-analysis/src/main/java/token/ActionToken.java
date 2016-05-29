package token;

/**
 * Class representing an action token
 */

public class ActionToken extends AbstractSyntaxToken {
    public ActionToken(String value) {
        super(value);
    }

    /**
     * Copy constructor
     * @param abstractSyntaxToken
     */
    public ActionToken(ActionToken abstractSyntaxToken) {
        super(abstractSyntaxToken);
    }

    @Override
    public String getValue() {
        return getOriginalValue().substring(1, getOriginalValue().length()-1);
    }

    @Override
    public AbstractSyntaxToken copy() {
        return new ActionToken(this);
    }
}
