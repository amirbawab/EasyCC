package token;

/**
 * Factory class to help creating token of different types
 */

public class AbstractTokenFactory {

    /**
     * Create a lexical token
     * @param token
     * @param value
     * @param row
     * @param col
     * @param position
     * @return lexical token
     */
    public static AbstractToken createLexicalToken(String token, String value, int row, int col, int position) {
        return new LexicalToken(token, value, row, col, position);
    }

    /**
     * Create an error token
     * @param token
     * @param value
     * @param row
     * @param col
     * @param position
     * @return error token
     */
    public static AbstractToken createErrorToken(String token, String value, int row, int col, int position) {
        return new ErrorToken(token, value, row, col, position);
    }

    /**
     * Create an end of file token
     * @param row
     * @param col
     * @param position
     * @return end of file token
     */
    public static AbstractToken createEndOfFileToken(int row, int col, int position) {
        return new EndOfFileToken(row, col, position);
    }
}
