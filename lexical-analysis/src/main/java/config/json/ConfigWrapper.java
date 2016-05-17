package config.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ConfigWrapper {

    @JsonProperty("tokensConfig")
    private TokensConfig tokensConfig;

    public TokensConfig getTokensConfig() {
        return tokensConfig;
    }

}
