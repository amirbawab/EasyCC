package machine;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import machine.json.Edge;
import machine.json.MachineGraph;
import machine.json.State;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class StateMachine {

    // Variables
    private Logger l = LogManager.getLogger();
    private Map<String, State> statesMap;
    private State initialState;
    private int stateId = 0;

    /**
     * Read a Machine Graph file and converts it into a state machine
     * @param filename
     * @throws IOException
     */
    public StateMachine(String filename) throws IOException {

        // Default constructor
        this();

        // Parse JSON
        ObjectMapper mapper = new ObjectMapper();
        InputStream file = getClass().getResourceAsStream(filename);
        MachineGraph machineGraph = mapper.readValue(file, MachineGraph.class);

        // Store states in a map
        for(State state : machineGraph.getStates()) {
            addState(state);
        }

        // Create graph
        for (Edge edge : machineGraph.getEdges()) {
            addEdge(edge);
        }
    }

    /**
     * Create an empty state machine
     */
    public StateMachine() {
        // Init variables
        statesMap = new HashMap<>();
    }

    /**
     * Add state to the state machine
     * @param state
     */
    public void addState(State state) {
        if(statesMap.containsKey(state.getName())) {
            throw new StateMachineException("State name must be unique");
        } else {
            statesMap.put(state.getName(), state);
            state.setId(stateId++);
            if (state.getType() == State.Type.INITIAL) {
                if(initialState != null) {
                    throw new StateMachineException("Only one initial state is allowed");
                } else {
                    initialState = state;
                }
            }
        }
    }

    /**
     * Add edge to the state machine
     * @param edge
     */
    public void addEdge(Edge edge) {
        edge.setFromState(statesMap.get(edge.getFrom()));
        edge.setToState(statesMap.get(edge.getTo()));
        edge.getFromState().getOutEdges().add(edge);
    }

    /**
     * Remove state from state machine
     * @param state
     */
    public void removeState(State state) {

        // Update initial state
        if(initialState == state) {
            initialState = null;
        }

        // Remove state from map
        statesMap.remove(state.getName());
    }

    /**
     * Remove edge from the state machine
     * @param edge
     */
    public void removeEdge(Edge edge) {
        edge.getFromState().getOutEdges().remove(edge);
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
    public Set<String> getTokens() {
        Set<String> tokens = new HashSet<>();
        for(State state : statesMap.values()) {
            if(state.getType() == State.Type.FINAL) {
                tokens.add(state.getToken());
            }
        }
        return tokens;
    }

    /**
     * Get all characters
     * @return characters
     */
    public Set<String> getCharacters() {
        Set<String> characters = new HashSet<>();
        for(State state : statesMap.values()) {
            for(Edge edge : state.getOutEdges()) {
                characters.add(edge.getValue());
            }
        }
        return characters;
    }

    /**
     * Get initial state
     * @return initial state
     */
    public State getInitialState() {
        return initialState;
    }

    /**
     * Verify that the state machine is valid
     * Note: This state machine has custom rules
     */
    public void verify() {

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

