package core.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.config.json.SemanticMessagesConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;

/**
 * Semantic analysis configuration file
 * Load configuration data from files
 */
public class SemanticConfig {

    // Logger
    private Logger l = LogManager.getFormatterLogger(getClass());

    // Singleton instance
    private static SemanticConfig instance = new SemanticConfig();

    // Message config
    private SemanticMessagesConfig semanticMessagesConfig;

    /**
     * Private constructor
     */
    private SemanticConfig() {}

    /**
     * Get singleton instance
     * @return singleton instance
     */
    public static SemanticConfig getInstance() {
        return instance;
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
            semanticMessagesConfig = mapper.readValue(file, SemanticMessagesConfig.class);
        } catch (IOException e) {
            l.error(e.getMessage());
        }
    }

    /**
     * Get error message configuration
     * @return error message configuration
     */
    public SemanticMessagesConfig getSemanticMessagesConfig() {
        return semanticMessagesConfig;
    }
}
