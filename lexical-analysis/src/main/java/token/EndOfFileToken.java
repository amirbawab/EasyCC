package token;

/**
 * End Of File Tokens represent the end of file.
 * An instance of this token will always be injected at the end of the lexical tokens list
 */

public class EndOfFileToken extends AbstractToken{

    public EndOfFileToken(int row, int col, int position) {
        super("End of file", "End of file", row, col, position);
    }
}
