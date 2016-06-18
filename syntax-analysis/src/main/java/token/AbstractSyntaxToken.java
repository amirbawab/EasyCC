package token;

/**
 * Abstract token is a template for different type of tokens
 */
public abstract class AbstractSyntaxToken {

    protected String value;

    public AbstractSyntaxToken(String value) {
        this.value = value;
    }

    public AbstractSyntaxToken(AbstractSyntaxToken abstractSyntaxToken) {
        this.value = abstractSyntaxToken.value;
    }

    public abstract String getValue();

    final public String getOriginalValue() {
        return value;
    }

    @Override
    public String toString() {
        return getOriginalValue();
    }

    /**
     * Make a copy of a token
     * @return copy of a token
     */
    public abstract AbstractSyntaxToken copy();
}
