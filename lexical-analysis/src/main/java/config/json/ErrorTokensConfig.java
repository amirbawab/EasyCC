package config.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorTokensConfig {

    @JsonProperty("prefix")
    private String prefix;

    @JsonProperty("include")
    private List<String> include;

    @JsonProperty("exclude")
    private List<String> exclude;

    public String getPrefix() {
        return prefix;
    }

    public List<String> getInclude() {
        return include;
    }

    public List<String> getExclude() {
        return exclude;
    }
}