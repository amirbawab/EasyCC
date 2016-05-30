package token;

/**
 * Lexical Tokens are the tokens accepted by the state machine and land on a non-error final state.
 */

public class LexicalToken extends AbstractToken {

    public LexicalToken(String token, String value, int row, int col, int position) {
        super(token, value, row, col, position);
    }
}
