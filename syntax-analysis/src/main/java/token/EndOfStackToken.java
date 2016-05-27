package token;

import helper.SyntaxHelper;

/**
 * Class representing end of stack token
 */

public class EndOfStackToken extends AbstractSyntaxToken {
    public EndOfStackToken() {
        super(SyntaxHelper.END_OF_STACK);
    }

    @Override
    public String getValue() {
        return getOriginalValue();
    }
}
