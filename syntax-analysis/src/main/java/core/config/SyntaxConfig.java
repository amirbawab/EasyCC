package core.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.config.json.messages.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Configuration for syntax analysis.
 */

public class SyntaxConfig {

    // Logger
    private Logger l = LogManager.getFormatterLogger(getClass());

    public static SyntaxConfig instance = new SyntaxConfig();

    private SyntaxMessagesConfig syntaxMessageConfig;

    private Map<String, String> llMessagesMap;
    private Map<String, String> lrMessagesMap;

    /**
     * Private constructor
     */
    private SyntaxConfig() {
        llMessagesMap = new HashMap<>();
        lrMessagesMap = new HashMap<>();
    }

    /**
     * Load message from file
     * @param filename
     */
    public void loadMessages(String filename) {
        try {
            // Parse JSON
            ObjectMapper mapper = new ObjectMapper();
            InputStream file = getClass().getResourceAsStream(filename);
            syntaxMessageConfig = mapper.readValue(file, SyntaxMessagesConfig.class);
        } catch (IOException e) {
            l.error(e.getMessage());
        }

        if(syntaxMessageConfig.getSyntaxMessagesLLConfig() != null) {
            for (SyntaxMessagesLLDataConfig data : syntaxMessageConfig.getSyntaxMessagesLLConfig().getMessages()) {
                llMessagesMap.put(getMessageKey(data.getNonTerminal(), data.getTerminal()), data.getMessage());
            }
        }

        if(syntaxMessageConfig.getSyntaxMessagesLRConfig() != null) {
            for (SyntaxMessagesLRDataConfig data : syntaxMessageConfig.getSyntaxMessagesLRConfig().getMessages()) {
                llMessagesMap.put(getMessageKey(data.getErrorKey(), data.getTerminal()), data.getMessage());
            }
        }
    }

    /**
     * Generate a key given major and aminor strings
     * @param major
     * @param minor
     * @return key
     */
    private String getMessageKey(String major, String minor) {
        if(major == null) {
            l.error("Major key cannot be null");
            return null;
        }

        if(minor == null) {
            return major;
        }

        return major + "::" + minor;
    }

    /**
     * Return singleton instance
     * @return instance
     */
    public static SyntaxConfig getInstance() {
        return instance;
    }

    /**
     * Get LL message based on non-terminal and terminal
     * @param nonTerminal
     * @param terminal
     * @return custom message
     */
    public String getLLMessage(String nonTerminal, String terminal) {

        String key = getMessageKey(nonTerminal, terminal);
        if(llMessagesMap.containsKey(key))
            return llMessagesMap.get(key);

        if(llMessagesMap.containsKey(nonTerminal))
            return llMessagesMap.get(nonTerminal);

        return syntaxMessageConfig.getSyntaxMessagesLLConfig().getDefaultMessage();
    }

    /**
     * Get LR message based on error key and terminal
     * @param errorKey
     * @param terminal
     * @return custom message
     */
    public String getLRMessage(String errorKey, String terminal) {

        String key = getMessageKey(errorKey, terminal);
        if(lrMessagesMap.containsKey(key))
            return lrMessagesMap.get(key);

        if(lrMessagesMap.containsKey(errorKey))
            return lrMessagesMap.get(errorKey);

        return syntaxMessageConfig.getSyntaxMessagesLRConfig().getDefaultMessage();
    }

    /**
     * Get message config for LL parser
     * @return message config
     */
    public SyntaxMessagesLLConfig getLLSyntaxMessageConfig() {
        return syntaxMessageConfig.getSyntaxMessagesLLConfig();
    }

    /**
     * Get message config for LR parser
     * @return message config
     */
    public SyntaxMessagesLRConfig getLRSyntaxMessageConfig() {
        return syntaxMessageConfig.getSyntaxMessagesLRConfig();
    }
}
