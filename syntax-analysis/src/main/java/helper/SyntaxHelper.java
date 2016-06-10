package helper;

import core.config.SyntaxConfig;
import token.*;

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
    public static String tokenMessage(NonTerminalToken nonTerminalToken, AbstractToken lexicalToken) {
        return LexicalHelper.messageReplace(SyntaxConfig.getInstance().getMessage(nonTerminalToken.getValue(), lexicalToken.getToken()), lexicalToken);
    }

    /**
     * Get the default message
     * @param lexicalToken
     * @return message
     */
    public static String tokenDefaultMessage(AbstractToken lexicalToken) {
        return LexicalHelper.messageReplace(SyntaxConfig.getInstance().getSyntaxMessageConfig().getDefaultMessage(), lexicalToken);
    }
}
