package core.config.json.messages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SyntaxMessagesLRConfig {

    @JsonProperty("default")
    private String defaultMessage;

    @JsonProperty("messages")
    private List<SyntaxMessagesLRDataConfig> messages;

    public String getDefaultMessage() {
        return defaultMessage;
    }

    public List<SyntaxMessagesLRDataConfig> getMessages() {
        return messages;
    }
}
