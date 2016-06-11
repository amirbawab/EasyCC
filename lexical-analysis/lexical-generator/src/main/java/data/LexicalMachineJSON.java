package data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class LexicalMachineJSON {

    @JsonProperty("states")
    List<LexicalStateJSON> states = new ArrayList<>();

    @JsonProperty("edges")
    List<LexicalEdgeJSON> edges = new ArrayList<>();

    public List<LexicalStateJSON> getStates() {
        return states;
    }

    public void setStates(List<LexicalStateJSON> states) {
        this.states = states;
    }

    public List<LexicalEdgeJSON> getEdges() {
        return edges;
    }

    public void setEdges(List<LexicalEdgeJSON> edges) {
        this.edges = edges;
    }
}
