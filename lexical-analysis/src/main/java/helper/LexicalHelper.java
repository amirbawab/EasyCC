package helper;

import config.LexicalConfig;
import token.AbstractToken;

/**
 * Common lexical variables.
 * Variables and functions in this file should be static.
 */

public class LexicalHelper {

    // Define End Of Line and End Of File symbols
    public static final char EOL = '\n';
    public static final char EOF = '\0';

    /**
     * Get the message of a token
     * @param token
     * @return message
     */
    public static String tokenMessage(AbstractToken token) {
        return LexicalConfig.getInstance().getLexicalMessagesConfig().getMessage(token.getToken()).
                replace(InputHelper.VALUE, token.getValue()).
                replace(InputHelper.LINE, Integer.toString(token.getRow())).
                replace(InputHelper.COL, Integer.toString(token.getCol()));
    }
}
