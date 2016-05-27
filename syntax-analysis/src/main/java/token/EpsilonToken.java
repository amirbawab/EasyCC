package token;

import helper.SyntaxHelper;

/**
 * Class representing an epsilon token
 */

public class EpsilonToken extends AbstractSyntaxToken {
    public EpsilonToken() {
        super(SyntaxHelper.EPSILON);
    }

    @Override
    public String getValue() {
        return getOriginalValue();
    }
}
