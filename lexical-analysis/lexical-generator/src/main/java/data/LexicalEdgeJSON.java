package data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LexicalEdgeJSON {

    @JsonProperty("from")
    private String from;

    @JsonProperty("to")
    private String to;

    @JsonProperty("value")
    private String value;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
