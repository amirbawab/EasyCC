package core.helper;

import core.config.SemanticConfig;
import enums.SemanticMessageEnum;
import helper.LexicalHelper;
import token.AbstractToken;

/**
 * Common semantic variables.
 * Variables and functions in this file should be static.
 */

public class SemanticHelper {

    /**
     * Get the message of a token
     * @param token
     * @return message
     */
    public static String tokenMessage(SemanticMessageEnum semanticMessageEnum, AbstractToken token) {
        return LexicalHelper.messageReplace(SemanticConfig.getInstance().getSemanticMessagesConfig().getMessage(semanticMessageEnum.getName()), token);
    }
}
