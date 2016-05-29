package config.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LexicalTokensConfig {

    @JsonProperty("error")
    private ErrorTokensConfig errorTokensConfig;

    @JsonProperty("reserved")
    private List<ReservedTokensConfig> reservedTokensConfig;

    @JsonProperty("ignore")
    private List<IgnoreTokensConfig> ignoreConfig;

    public ErrorTokensConfig getErrorTokensConfig() {
        return errorTokensConfig;
    }

    public List<ReservedTokensConfig> getReservedTokensConfig() {
        return reservedTokensConfig;
    }

    public List<IgnoreTokensConfig> getIgnoreConfig() {
        return ignoreConfig;
    }

    /**
     * Search if an identifier is a reserved word
     * @param value
     * @param defaultToken
     * @return if value found, return reserved word token. Otherwise, return the default token
     */
    // TODO Store the values in a map
    public String getReservedTokenOrDefault(String value, String defaultToken) {
        for(ReservedTokensConfig reserved : reservedTokensConfig) {
            if(reserved.getValue().equals(value)) {
                return reserved.getToken();
            }
        }
        return defaultToken;
    }
}
