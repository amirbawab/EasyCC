package token;

/**
 * Class representing a dot token used in the LR State machine
 */

public class DotToken extends AbstractSyntaxToken {

    public DotToken() {
        super(".");
    }

    public DotToken(AbstractSyntaxToken abstractSyntaxToken) {
        super(abstractSyntaxToken);
    }

    @Override
    public String getValue() {
        return getOriginalValue();
    }

    @Override
    public AbstractSyntaxToken copy() {
        return new DotToken(this);
    }
}
