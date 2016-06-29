package core.config.json.messages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SyntaxMessagesLRDataConfig {

    @JsonProperty("error-key")
    private String errorKey;

    @JsonProperty("terminal")
    private String terminal;

    @JsonProperty("message")
    private String message;

    public String getErrorKey() {
        return errorKey;
    }

    public String getTerminal() {
        return terminal;
    }

    public String getMessage() {
        return message;
    }
}
