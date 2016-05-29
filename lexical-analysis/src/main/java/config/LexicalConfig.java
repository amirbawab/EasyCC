package config;

import com.fasterxml.jackson.databind.ObjectMapper;
import config.json.LexicalMessagesConfig;
import config.json.LexicalTokensConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;

/**
 * Configuration file for the lexical analyzer.
 * This class is populated by the lexical config file
 */

public class LexicalConfig {

    // Logger
    private Logger l = LogManager.getFormatterLogger(getClass());

    // Singleton
    private static LexicalConfig instance = new LexicalConfig();

    // Components
    private LexicalTokensConfig lexicalTokensConfig;
    private LexicalMessagesConfig lexicalMessagesConfig;

    /**
     * Private constructor
     */
    private LexicalConfig(){};

    /**
     * Load tokens configuration file
     * @param filename
     */
    public void loadTokens(String filename) {
        try {
            // Parse JSON
            ObjectMapper mapper = new ObjectMapper();
            InputStream file = getClass().getResourceAsStream(filename);
            lexicalTokensConfig = mapper.readValue(file, LexicalTokensConfig.class);
        } catch (IOException e) {
            l.error(e.getMessage());
        }
    }

    /**
     * Load messages configuration file
     * @param filename
     */
    public void loadMessages(String filename) {
        try {
            // Parse JSON
            ObjectMapper mapper = new ObjectMapper();
            InputStream file = getClass().getResourceAsStream(filename);
            lexicalMessagesConfig = mapper.readValue(file, LexicalMessagesConfig.class);
        } catch (IOException e) {
            l.error(e.getMessage());
        }
    }

    /**
     * Get tokens configuration
     * @return configuration
     */
    public LexicalTokensConfig getLexicalTokensConfig() {
        return lexicalTokensConfig;
    }

    /**
     * Get message configuration
     * @return message configuration
     */
    public LexicalMessagesConfig getLexicalMessagesConfig() {
        return lexicalMessagesConfig;
    }

    /**
     * Get instance
     * @return instance
     */
    public static LexicalConfig getInstance() {
        return instance;
    }
}
