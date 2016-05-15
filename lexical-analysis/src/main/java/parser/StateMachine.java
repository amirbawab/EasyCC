package parser;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class StateMachine {

    private final org.apache.logging.log4j.Logger l = LogManager.getLogger();
    private Lexical_Analysis lexical_analysis;
    private Map<String, State> statesMap;

    public StateMachine(String filename) {
        try {

            // Init variables
            statesMap = new HashMap<>();

            // Parse JSON
            ObjectMapper mapper = new ObjectMapper();
            InputStream file = getClass().getResourceAsStream(filename);
            lexical_analysis = mapper.readValue(file, Lexical_Analysis.class);

            // Store states in a map
            for(int i = 0; i < lexical_analysis.states.size(); i++) {
                statesMap.put(lexical_analysis.states.get(i).name, lexical_analysis.states.get(i));
                lexical_analysis.states.get(i).id = i;
            }

            // Create graph
            for (Edge edge : lexical_analysis.edges) {
                edge.fromState = statesMap.get(edge.from);
                edge.toState = statesMap.get(edge.to);
                edge.fromState.outEdges.add(edge);
            }

        } catch (IOException e) {
            l.error(e.getMessage());
        }
    }

    /**
     * Get all states
     * @return collection of states
     */
    public Collection<State> getAllStates() {
        return statesMap.values();
    }

    @Override
    public String toString() {
        return lexical_analysis.toString();
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Lexical_Analysis {

    @JsonProperty("states")
    public List<State> states;

    @JsonProperty("edges")
    public List<Edge> edges;

    @Override
    public String toString() {
        String result = "";
        for (State state : states)
            result += state + "\n";
        return result;
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class State {

    @JsonProperty("name")
    public String name;

    @JsonProperty("type")
    public String type;

    @JsonProperty("token")
    public String token;

    @JsonProperty("backtrack")
    public String backtrack;

    // Unique id
    public int id;

    // List of outgoing edges
    public List<Edge> outEdges = new ArrayList<>();

    /**
     * Get state when reading a value
     * @param value
     * @return new state
     */
    public State getStateOn(String value) {
        for(Edge edge : outEdges)
            if(edge.value.equals(value))
                return edge.toState;
        return null;
    }

    @Override
    public String toString() {
        String result = String.format("%s - %s\n", name, type);
        for(Edge edge : outEdges)
            result += String.format("\t%s\n", edge.toString());
        return result;
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Edge {
    @JsonProperty("from")
    public String from;

    @JsonProperty("to")
    public String to;

    @JsonProperty("value")
    public String value;

    // States
    public State fromState;
    public State toState;

    @Override
    public String toString() {
        return String.format("%s => %s (%s)", from, to, value);
    }
}