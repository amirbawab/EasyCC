package config.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReservedTokensConfig {

    @JsonProperty("token")
    private String token;

    @JsonProperty("value")
    private String value;

    public String getToken() {
        return token;
    }

    public String getValue() {
        return value;
    }
}
