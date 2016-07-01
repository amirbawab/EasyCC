package token;

/**
 * Action token for LR parser
 */

public class LRActionToken extends ActionToken {
    private AbstractSyntaxToken abstractSyntaxToken;

    public LRActionToken(String value) {
        super(value);
    }

    public LRActionToken(LRActionToken lrActionToken) {
        super(lrActionToken);
        abstractSyntaxToken = lrActionToken.abstractSyntaxToken;
    }

    public AbstractSyntaxToken getAbstractSyntaxToken() {
        return abstractSyntaxToken;
    }

    public void setAbstractSyntaxToken(AbstractSyntaxToken abstractSyntaxToken) {
        this.abstractSyntaxToken = abstractSyntaxToken;
    }

    @Override
    public AbstractSyntaxToken copy() {
        return new LRActionToken(this);
    }
}
