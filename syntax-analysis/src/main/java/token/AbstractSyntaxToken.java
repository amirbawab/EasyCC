package token;

/**
 * Abstract token is a template for different type of tokens
 */
public abstract class AbstractSyntaxToken {

    private String value;

    public AbstractSyntaxToken(String value) {
        this.value = value;
    }

    public abstract String getValue();

    final public String getOriginalValue() {
        return value;
    }
}
