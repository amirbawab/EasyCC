package token;

import helper.SyntaxHelper;

/**
 * Class representing end of data token
 */

public class EndOfStackToken extends AbstractSyntaxToken {
    public EndOfStackToken() {
        super(SyntaxHelper.END_OF_STACK);
    }

    /**
     * Copy constructor
     * @param endOfStackToken
     */
    public EndOfStackToken(EndOfStackToken endOfStackToken) {
        super(endOfStackToken);
    }

    @Override
    public String getValue() {
        return getOriginalValue();
    }

    @Override
    public AbstractSyntaxToken copy() {
        return new EndOfStackToken(this);
    }
}
