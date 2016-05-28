package config;

import com.fasterxml.jackson.databind.ObjectMapper;
import config.json.messages.SyntaxMessageConfig;
import config.json.messages.SyntaxMessageDataConfig;
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

    private SyntaxMessageConfig syntaxMessageConfig;

    private Map<String, String> messagesMap;

    /**
     * Private constructor
     */
    private SyntaxConfig() {
        messagesMap = new HashMap<>();
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
            syntaxMessageConfig = mapper.readValue(file, SyntaxMessageConfig.class);
        } catch (IOException e) {
            l.error(e.getMessage());
        }

        for(SyntaxMessageDataConfig data : syntaxMessageConfig.getMessages()) {
            messagesMap.put(getMessageKey(data.getNonTerminal(), data.getTerminal()), data.getMessage());
        }
    }

    /**
     * Generate a key given non-terminal and terminal
     * @param nonTerminal
     * @param terminal
     * @return key
     */
    private String getMessageKey(String nonTerminal, String terminal) {
        if(nonTerminal == null) {
            l.error("Non terminal cannot be null");
            return null;
        }

        if(terminal == null) {
            return nonTerminal;
        }

        return nonTerminal + "::" + terminal;
    }

    /**
     * Return singleton instance
     * @return instance
     */
    public static SyntaxConfig getInstance() {
        return instance;
    }

    /**
     * Get message based on non-terminal and terminal
     * @param nonTerminal
     * @param terminal
     * @return custom message
     */
    public String getMessage(String nonTerminal, String terminal) {

        String key = getMessageKey(nonTerminal, terminal);
        if(messagesMap.containsKey(key))
            return messagesMap.get(key);

        if(messagesMap.containsKey(nonTerminal))
            return messagesMap.get(nonTerminal);

        return syntaxMessageConfig.getDefaultMessage();
    }

    /**
     * Get message config
     * @return message config
     */
    public SyntaxMessageConfig getSyntaxMessageConfig() {
        return syntaxMessageConfig;
    }
}
