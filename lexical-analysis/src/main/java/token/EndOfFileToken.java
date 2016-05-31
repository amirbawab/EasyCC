package token;

/**
 * End Of File Tokens represent the end of file.
 * An instance of this token will always be injected at the end of the lexical tokens list
 */

public class EndOfFileToken extends AbstractToken{

    // $ in lexical and syntax analyzer are not related
    // It can be renamed to anything
    public EndOfFileToken(int row, int col, int position) {
        super("$", "$", row, col, position);
    }
}
