package token;

/**
 * Error Tokens are the tokens accepted by the state machine and land on an error final state.
 */

public class ErrorToken extends AbstractToken {

    public ErrorToken(String token, String value, int row, int col, int position) {
        super(token, value, row, col, position);
    }
}
