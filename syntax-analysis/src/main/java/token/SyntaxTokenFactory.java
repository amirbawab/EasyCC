package token;

/**
 * Factory class to help create token of different types
 */

public class SyntaxTokenFactory {

    /**
     * Create end of stack syntax token
     * @return A new end of stack syntax token
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
     * Create an action syntax token
     * @param value
     * @return A new action syntax token
     */
    public static ActionToken createActionToken(String value) {
        return new ActionToken(value);
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
}
