package parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import parser.json.Edge;
import parser.json.Lexical_Analysis;
import parser.json.State;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class StateMachine {

    // Variables
    private final org.apache.logging.log4j.Logger l = LogManager.getLogger();
    private Lexical_Analysis lexical_analysis;
    private Map<String, State> statesMap;
    private Set<String> tokens;
    private Set<String> characters;
    private State initialState;

    public StateMachine(String filename) throws IOException {
        // Init variables
        statesMap = new HashMap<>();
        tokens = new HashSet<>();
        characters = new HashSet<>();

        // Parse JSON
        ObjectMapper mapper = new ObjectMapper();
        InputStream file = getClass().getResourceAsStream(filename);
        lexical_analysis = mapper.readValue(file, Lexical_Analysis.class);

        // Store states in a map
        for(int i = 0; i < lexical_analysis.getStates().size(); i++) {
            State current = lexical_analysis.getStates().get(i);
            statesMap.put(current.getName(), current);
            current.setId(i);
            if(current.getType() == State.Type.FINAL)
                tokens.add(current.getToken());
            else if(current.getType() == State.Type.INITIAL)
                initialState = current;
        }

        // Create graph
        for (Edge edge : lexical_analysis.getEdges()) {
            edge.setFromState(statesMap.get(edge.getFrom()));
            edge.setToState(statesMap.get(edge.getTo()));
            edge.getFromState().getOutEdges().add(edge);
            characters.add(edge.getValue());
        }

        // Verify that the state machine is structured correctly
        verify();
    }

    /**
     * Get all states
     * @return collection of states
     */
    public Collection<State> getAllStates() {
        return statesMap.values();
    }

    /**
     * Get all tokens
     * @return set of tokens
     */
    public Set<String> getTokens() { return tokens; }

    /**
     * Get all characters
     * @return characters
     */
    public Set<String> getCharacters() {
        return characters;
    }

    /**
     * Get initial state
     * @return initial state
     */
    public State getInitialState() {
        return initialState;
    }

    @Override
    public String toString() {
        return lexical_analysis.toString();
    }

    /**
     * Verify that the state machine is valid
     * Note: This state machine has custom rules
     */
    private void verify() {

        // One initial state only
        boolean hasInitial = false;
        for(State state : getAllStates()) {
            if(state.getType() == State.Type.INITIAL) {
                if(hasInitial) {
                    throw new StateMachineException("A state machine should have exactly one initial state");
                } else {
                    hasInitial = true;
                }
            }

            if(state.getType() == State.Type.FINAL && state.getToken() == null) {
                throw new StateMachineException("Each final state should have a token value");
            }

            if(state.getType() != State.Type.FINAL && state.getToken() != null) {
                throw new StateMachineException("A non final state should not have a token value");
            }

            if(state.getType() == State.Type.FINAL && state.getOutEdges().size() > 0) {
                throw new StateMachineException("A final state should not have outgoing edges");
            }

            if(state.getType() != State.Type.FINAL && state.getStateOn(Edge.Special.OTHER.getValue()) == null) {
                throw new StateMachineException("Each non final state should have a move on: " + Edge.Special.OTHER.getValue());
            }

            if(!Arrays.asList(State.Type.values()).contains(state.getType())) {
                throw new StateMachineException("State type should be: initial, normal or final");
            }
        }

        // If no initial state
        if(!hasInitial) {
            throw new StateMachineException("One state should be of type: initial");
        }
    }
}

