package config.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TokensConfig {

    @JsonProperty("errorTokens")
    private ErrorTokensConfig errorTokensConfig;

    @JsonProperty("reserved")
    private List<ReservedConfig> reservedConfig;

    public ErrorTokensConfig getErrorTokensConfig() {
        return errorTokensConfig;
    }

    public List<ReservedConfig> getReservedConfig() {
        return reservedConfig;
    }

    /**
     * Search if an identifier is a reserved word
     * @param value
     * @param defaultToken
     * @return if value found, return reserved word token. Otherwise, return the default token
     */
    // TODO Store the values in a map
    public String getReservedTokenOrDefault(String value, String defaultToken) {
        for(ReservedConfig reserved : reservedConfig) {
            if(reserved.getValue().equals(value)) {
                return reserved.getToken();
            }
        }
        return defaultToken;
    }
}
