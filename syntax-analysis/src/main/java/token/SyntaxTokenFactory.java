package token;

/**
 * Factory class to help create token of different types
 */

public class SyntaxTokenFactory {

    /**
     * Create end of data syntax token
     * @return A new end of data syntax token
     */
    public static EndOfStackToken createEndOfStackToken() {
        return new EndOfStackToken();
    }

    /**
     * Create epsilon syntax token
     * @return A new epsilon syntax token
     */
    public static EpsilonToken createEpsilonToken() {
        return new EpsilonToken();
    }

    /**
     * Create an LL action syntax token
     * @param value
     * @return A new action syntax token
     */
    public static ActionToken createLLActionToken(String value) {
        return new LLActionToken(value);
    }

    /**
     * Create an LR action syntax token
     * @param value
     * @return A new action syntax token
     */
    public static ActionToken createLRActionToken(String value) {
        return new LRActionToken(value);
    }

    /**
     * Create a non-terminal syntax token
     * @param value
     * @return A new non-terminal syntax token
     */
    public static NonTerminalToken createNonTerminalToken(String value) {
        return new NonTerminalToken(value);
    }

    /**
     * Create a terminal syntax token
     * @param value
     * @return A new terminal syntax token
     */
    public static TerminalToken createTerminalToken(String value) {
        return new TerminalToken(value);
    }

    /**
     * Create an error syntax token
     * @param value
     * @return A new error syntax token
     */
    public static ErrorKeyToken createErrorToken(String value) {
        return new ErrorKeyToken(value);
    }

    /**
     * Create a dot syntax token
     * @return A new dot syntax token
     */
    public static DotToken createDotToken() {
        return new DotToken();
    }
}
