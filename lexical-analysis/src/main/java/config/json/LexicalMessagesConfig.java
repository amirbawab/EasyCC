package config.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LexicalMessagesConfig {

    @JsonProperty("messages")
    private Map<String, String> messages;

    public Map<String, String> getMessages() {
        return messages;
    }

    public String getMessage(String token) {
        return messages.get(token);
    }
}
