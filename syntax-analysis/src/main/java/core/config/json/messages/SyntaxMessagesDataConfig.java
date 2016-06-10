package core.config.json.messages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SyntaxMessagesDataConfig {

    @JsonProperty("non-terminal")
    private String nonTerminal;

    @JsonProperty("terminal")
    private String terminal;

    @JsonProperty("message")
    private String message;

    public String getNonTerminal() {
        return nonTerminal;
    }

    public String getTerminal() {
        return terminal;
    }

    public String getMessage() {
        return message;
    }
}
