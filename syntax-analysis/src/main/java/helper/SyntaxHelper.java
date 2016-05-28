package helper;

import config.SyntaxConfig;
import token.TerminalToken;

/**
 * Common syntax variables.
 * Variables and functions in this file should be static.
 */

public class SyntaxHelper {
    public static final String EPSILON = "EPSILON";
    public static final String END_OF_STACK = "$";

    /**
     * Get the message of a token
     * @param nonTerminal
     * @param terminal
     * @return message
     */
    public static String tokenMessage(String nonTerminal, String terminal, TerminalToken token) {
        return SyntaxConfig.getInstance().getMessage(nonTerminal, terminal).
                replace(InputHelper.LINE, Integer.toString(token.getLexicalToken().getRow())).
                replace(InputHelper.COL, Integer.toString(token.getLexicalToken().getCol()));
    }
}
