package config.json.messages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SyntaxMessagesConfig {

    @JsonProperty("default")
    private String defaultMessage;

    @JsonProperty("messages")
    private List<SyntaxMessageDataConfig> messages;

    public String getDefaultMessage() {
        return defaultMessage;
    }

    public List<SyntaxMessageDataConfig> getMessages() {
        return messages;
    }
}
