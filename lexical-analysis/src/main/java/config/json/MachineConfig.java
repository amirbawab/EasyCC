package config.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MachineConfig {

    @JsonProperty("tokens")
    private TokensConfig tokensConfig;

    public TokensConfig getTokensConfig() {
        return tokensConfig;
    }

}
