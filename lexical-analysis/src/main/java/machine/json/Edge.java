package machine.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by amir on 5/15/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Edge {

    public enum Special {
        OTHER("OTHER"), // Any other character in the language
        SPACE("SPACE"), // Space includes single space and tab
        END_OF_LINE("EOL"), // End of line
        END_OF_FILE("EOF"), // End of file
        LETTER("LETTER"), // A-Za-z
        NONZERO("NONZERO"); // 1-9

        private String value;
        Special(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    @JsonProperty("from")
    private String from;

    @JsonProperty("to")
    private String to;

    @JsonProperty("value")
    private String value;

    // States
    private State fromState;
    private State toState;

    public void setFromState(State fromState) {
        this.fromState = fromState;
    }

    public void setToState(State toState) {
        this.toState = toState;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getValue() {
        return value;
    }

    public State getFromState() {
        return fromState;
    }

    public State getToState() {
        return toState;
    }

    @Override
    public String toString() {
        return String.format("%s => %s (%s)", from, to, value);
    }
}
