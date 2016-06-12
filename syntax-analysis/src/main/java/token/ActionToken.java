package token;

/**
 * Class representing an action token
 */

public class ActionToken extends AbstractSyntaxToken {

    private boolean stable;
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

    /**
     * Check if the semantic was created while the parser is in panic mode
     * @return false if the parse is in panic mode
     */
    public boolean isStable() {
        return stable;
    }

    /**
     * Set the token stability
     * @param stable
     */
    public void setStable(boolean stable) {
        this.stable = stable;
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
