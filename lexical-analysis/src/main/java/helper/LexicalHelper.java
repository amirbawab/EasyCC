package helper;

import core.config.LexicalConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import token.AbstractToken;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Common lexical variables.
 * Variables and functions in this file should be static.
 */

public class LexicalHelper {

    // Logger
    private static Logger l = LogManager.getFormatterLogger(LexicalHelper.class);

    // Define End Of Line and End Of File symbols
    public static final char EOL = '\n';
    public static final char EOF = '\0';

    // Input regex
    private static final String LEXICAL_INPUT_REGEX = "\\$\\{(lexical(:?\\.(?:next|previous))*\\.(?:token|value|line|column))\\}";

    /**
     * Get the message of a token
     * @param token
     * @return message
     */
    public static String tokenMessage(AbstractToken token) {
        return messageReplace(LexicalConfig.getInstance().getLexicalMessagesConfig().getMessage(token.getToken()), token);
    }

    /**
     * Add meaning to a text
     * @param message
     * @param token
     * @return meaningful text
     */
    public static String messageReplace(String message, AbstractToken token) {
        Pattern pattern = Pattern.compile(LEXICAL_INPUT_REGEX);
        Matcher matcher = pattern.matcher(message);

        while (matcher.find()) {
            if(matcher.groupCount() == 2) {
                String placeholder = matcher.group(0);
                String[] dotSplit = matcher.group(1).split("\\.");

                // Values
                AbstractToken tokenIter = token;
                String value = null;

                try {
                    for (int i = 0; i < dotSplit.length; i++) {
                        switch (dotSplit[i]) {
                            case "next":
                                tokenIter = tokenIter.getNext();
                                break;
                            case "previous":
                                tokenIter = tokenIter.getPrevious();
                                break;
                            case "value":
                                value = tokenIter.getValue();
                                break;
                            case "token":
                                value = tokenIter.getToken();
                                break;
                            case "line":
                                value = tokenIter.getRow() + "";
                                break;
                            case "column":
                                value = tokenIter.getCol() + "";
                                break;
                            case "lexical":
                                break;
                        }
                    }

                    if (value != null) {
                        message = message.replace(placeholder, value);
                    }

                } catch (NullPointerException e) {
                    l.error("Couldn't access lexical token in: " + placeholder);
                } catch (RuntimeException e) {
                    l.error(e.getMessage());
                }
            }
        }
        return message;
    }
}
