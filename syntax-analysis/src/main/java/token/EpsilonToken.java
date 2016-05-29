package token;

import helper.SyntaxHelper;

/**
 * Class representing an epsilon token
 */

public class EpsilonToken extends AbstractSyntaxToken {
    public EpsilonToken() {
        super(SyntaxHelper.EPSILON);
    }

    /**
     * Copy constructor
     * @param epsilonToken
     */
    public EpsilonToken(EpsilonToken epsilonToken) {
        super(epsilonToken);
    }

    @Override
    public String getValue() {
        return getOriginalValue();
    }

    @Override
    public AbstractSyntaxToken copy() {
        return new EpsilonToken(this);
    }
}
