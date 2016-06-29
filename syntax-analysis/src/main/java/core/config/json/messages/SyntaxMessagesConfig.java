package core.config.json.messages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Wrapper class for the LL and LR messages config
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class SyntaxMessagesConfig {

    @JsonProperty("LL")
    private SyntaxMessagesLLConfig syntaxMessagesLLConfig;

    @JsonProperty("LR")
    private SyntaxMessagesLRConfig syntaxMessagesLRConfig;

    public SyntaxMessagesLLConfig getSyntaxMessagesLLConfig() {
        return syntaxMessagesLLConfig;
    }

    public SyntaxMessagesLRConfig getSyntaxMessagesLRConfig() {
        return syntaxMessagesLRConfig;
    }
}
