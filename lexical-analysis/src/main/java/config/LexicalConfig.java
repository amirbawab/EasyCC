package config;

import com.fasterxml.jackson.databind.ObjectMapper;
import config.json.MachineConfig;
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
    private MachineConfig machineConfig;

    /**
     * Private constructor
     */
    private LexicalConfig(){};

    /**
     * Load configuration file
     * @param filename
     */
    public void load(String filename) {
        try {
            // Parse JSON
            ObjectMapper mapper = new ObjectMapper();
            InputStream file = getClass().getResourceAsStream(filename);
            machineConfig = mapper.readValue(file, MachineConfig.class);
        } catch (IOException e) {
            l.error(e.getMessage());
        }
    }

    /**
     * Get configuration
     * @return configuration
     */
    public MachineConfig getMachineConfig() {
        return machineConfig;
    }

    /**
     * Get instance
     * @return instance
     */
    public static LexicalConfig getInstance() {
        return instance;
    }
}