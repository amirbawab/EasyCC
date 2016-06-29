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
     * Get the message of a token for LL parser
     * @param nonTerminalToken
     * @param lexicalToken
     * @return message
     */
    public static String tokenMessageLL(NonTerminalToken nonTerminalToken, AbstractToken lexicalToken) {
        return LexicalHelper.messageReplace(SyntaxConfig.getInstance().getLLMessage(nonTerminalToken.getValue(), lexicalToken.getToken()), lexicalToken);
    }

    /**
     * Get the default message for LL parser
     * @param lexicalToken
     * @return message
     */
    public static String tokenDefaultMessageLL(AbstractToken lexicalToken) {
        return LexicalHelper.messageReplace(SyntaxConfig.getInstance().getLLSyntaxMessageConfig().getDefaultMessage(), lexicalToken);
    }

    /**
     * Get the message of a token for LR parser
     * @param errorKeyToken
     * @param lexicalToken
     * @return message
     */
    public static String tokenMessageLR(ErrorKeyToken errorKeyToken, AbstractToken lexicalToken) {
        return LexicalHelper.messageReplace(SyntaxConfig.getInstance().getLRMessage(errorKeyToken.getValue(), lexicalToken.getToken()), lexicalToken);
    }

    /**
     * Get the default message for LR parser
     * @param lexicalToken
     * @return message
     */
    public static String tokenDefaultMessageLR(AbstractToken lexicalToken) {
        return LexicalHelper.messageReplace(SyntaxConfig.getInstance().getLRSyntaxMessageConfig().getDefaultMessage(), lexicalToken);
    }
}
