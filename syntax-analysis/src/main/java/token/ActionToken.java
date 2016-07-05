package token;

/**
 * Class representing an action token
 */

public abstract class ActionToken extends AbstractSyntaxToken {

    // Stability marks if a syntax error occurred and can have consequences on the method attached to it
    private boolean stable;

    // Silent actions will be processed but the method attached to it will not execute
    private boolean silent;

    public ActionToken(String value) {
        super(value);
    }

    /**
     * Copy constructor
     * @param abstractSyntaxToken
     */
    public ActionToken(ActionToken abstractSyntaxToken) {
        super(abstractSyntaxToken);
        stable = abstractSyntaxToken.stable;
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

    public boolean isSilent() {
        return silent;
    }

    public void setSilent(boolean silent) {
        this.silent = silent;
    }
}
