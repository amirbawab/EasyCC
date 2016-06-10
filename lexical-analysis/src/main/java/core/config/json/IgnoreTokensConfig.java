package core.config.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IgnoreTokensConfig {

    @JsonProperty("prefix")
    private String prefix;

    @JsonProperty("suffix")
    private String suffix;

    @JsonProperty("include")
    private List<String> include;

    @JsonProperty("exclude")
    private List<String> exclude;

    public String getPrefix() {
        return prefix;
    }

    public String getSuffix() {
        return suffix;
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
     * @return true if ignore token
     */
    public boolean isIgnoreToken(String tokenName) {
        return (
                (prefix != null && !prefix.isEmpty() && tokenName.startsWith(prefix)) ||
                        (suffix != null && !suffix.isEmpty() && tokenName.endsWith(suffix)) ||
                        include.contains(tokenName))
                && !exclude.contains(tokenName);
    }
}