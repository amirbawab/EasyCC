package helper;

import config.SyntaxConfig;
import token.AbstractSyntaxToken;
import token.LexicalToken;
import token.NonTerminalToken;
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
     * @param nonTerminalToken
     * @param lexicalToken
     * @return message
     */
    public static String tokenMessage(NonTerminalToken nonTerminalToken, LexicalToken lexicalToken) {
        return replaceInString(SyntaxConfig.getInstance().getMessage(nonTerminalToken.getValue(), lexicalToken.getToken()), lexicalToken);
    }

    /**
     * Get the default message
     * @param lexicalToken
     * @return message
     */
    public static String tokenDefaultMessage(LexicalToken lexicalToken) {
        return replaceInString(SyntaxConfig.getInstance().getSyntaxMessageConfig().getDefaultMessage(), lexicalToken);
    }

    /**
     * Replace all the placeholders by meaningful values
     * @param text
     * @param lexicalToken
     * @return meaningful message
     */
    private static String replaceInString(String text, LexicalToken lexicalToken) {
        return text.
                replace(InputHelper.VALUE, lexicalToken.getValue()).
                replace(InputHelper.LINE, Integer.toString(lexicalToken.getRow())).
                replace(InputHelper.COL, Integer.toString(lexicalToken.getCol()));
    }
}
