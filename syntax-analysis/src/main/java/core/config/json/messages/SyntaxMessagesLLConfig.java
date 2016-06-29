package core.config.json.messages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SyntaxMessagesLLConfig {

    @JsonProperty("default")
    private String defaultMessage;

    @JsonProperty("messages")
    private List<SyntaxMessagesLLDataConfig> messages;

    public String getDefaultMessage() {
        return defaultMessage;
    }

    public List<SyntaxMessagesLLDataConfig> getMessages() {
        return messages;
    }
}
