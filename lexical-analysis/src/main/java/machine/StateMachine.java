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
     * Get state by name
     * @param name
     * @return state
     */
    public State getStateByName(String name) {
        return statesMap.get(name);
    }

    /**
     * Add state to the state machine
     * @param state
     */
    public void addState(State state) {
        if(statesMap.containsKey(state.getName())) {
            throw new StateMachineException("State name must be unique");
        } else {
            state.setId(stateId++);
            if (state.getType() == State.Type.INITIAL) {
                if(getInitialState() != null) {
                    throw new StateMachineException("Only one initial state is allowed");
                }
            }
            statesMap.put(state.getName(), state);
        }
    }

    /**
     * Add edge to the state machine
     * @param edge
     */
    public void addEdge(Edge edge) {
        // Check if a similar edge was already added
        for(Edge e : statesMap.get(edge.getFrom()).getOutEdges()) {
            if(e.equals(edge)) {
                throw new StateMachineException("Edge already exists between " + e.getFrom() + " and " + e.getTo() + " with value: " + e.getValue());
            }
        }
        edge.setFromState(statesMap.get(edge.getFrom()));
        edge.setToState(statesMap.get(edge.getTo()));
        edge.getFromState().getOutEdges().add(edge);
    }

    /**
     * Remove state from state machine
     * @param name
     * @return true if removed
     */
    public boolean removeState(String name) {

        if(statesMap.containsKey(name)) {

            State deleteState = statesMap.get(name);

            // Remove all in edges
            for(State state : statesMap.values()) {
                for(int i=0; i < state.getOutEdges().size(); i++) {
                    Edge edge = state.getOutEdges().get(i);
                    if(edge.getToState() == deleteState) {
                        state.getOutEdges().remove(i);
                    }
                }
            }

            // Remove state from map
            statesMap.remove(name);
            return true;
        }
        return false;
    }

    /**
     * Remove edge from the state machine
     * @param from
     * @param to
     * @param value
     * @return true if removed
     */
    public boolean removeEdge(String from, String to, String value) {
        State fromState = statesMap.get(from);
        State toState = statesMap.get(to);
        for(Edge edge : fromState.getOutEdges()) {
            if(edge.getFromState() == fromState && edge.getToState() == toState && edge.getValue().equals(value)) {
                edge.getFromState().getOutEdges().remove(edge);
                return true;
            }
        }
        return false;
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
        for(State state : statesMap.values()) {
            if(state.getType() == State.Type.INITIAL) {
                return state;
            }
        }
        return null;
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

