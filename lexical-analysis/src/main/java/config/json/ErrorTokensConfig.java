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

    /**
     * Checks the type of a token
     * @param tokenName
     * @return true if error token
     */
    public boolean isErrorToken(String tokenName) {
        return ((prefix != null && !prefix.isEmpty() && tokenName.startsWith(prefix)) || include.contains(tokenName)) && !exclude.contains(tokenName);
    }
}