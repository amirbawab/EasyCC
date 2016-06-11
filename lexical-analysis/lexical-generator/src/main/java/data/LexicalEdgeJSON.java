package data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LexicalEdgeJSON {

    @JsonProperty("from")
    private String from;

    @JsonProperty("to")
    private String to;

    @JsonProperty("value")
    private String value;

    private LexicalStateJSON fromState, toState;

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

    public String getLabel() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public LexicalStateJSON getFromState() {
        return fromState;
    }

    public void setFromState(LexicalStateJSON fromState) {
        this.fromState = fromState;
    }

    public LexicalStateJSON getToState() {
        return toState;
    }

    public void setToState(LexicalStateJSON toState) {
        this.toState = toState;
    }
}
