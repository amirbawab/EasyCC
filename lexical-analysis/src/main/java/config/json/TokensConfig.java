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
}
