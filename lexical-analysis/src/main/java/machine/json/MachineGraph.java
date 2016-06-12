package machine.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MachineGraph {

    @JsonProperty("states")
    private List<State> states;

    @JsonProperty("edges")
    private List<Edge> edges;

    public List<State> getStates() {
        return states;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setStates(List<State> states) {
        this.states = states;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    @Override
    public String toString() {
        String result = "";
        for (State state : states)
            result += state + "\n";
        return result;
    }
}
