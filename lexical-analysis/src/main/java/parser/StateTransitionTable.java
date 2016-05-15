package parser;

import java.util.HashMap;
import java.util.Map;

public class StateTransitionTable {

    // Variables
    private int[][] transitionTable;
    private String[] header;
    private State[] states;
    private Map<String, Integer> headerIndexMap;
    private int initialStateId;

    public StateTransitionTable(StateMachine stateMachine) {

        // Init variables
        this.transitionTable = new int[stateMachine.getAllStates().size()][stateMachine.getCharacters().size()];
        this.header = new String[stateMachine.getCharacters().size()];
        this.headerIndexMap = new HashMap<>();
        this.states = new State[stateMachine.getAllStates().size()];

        // Populate table header
        int index = 0;
        for(String character : stateMachine.getCharacters()) {
            headerIndexMap.put(character, index);
            header[index++] = character;
        }

        // Store states
        for(State state : stateMachine.getAllStates()) {
            states[state.getId()] = state;
        }

        // Store initial state id
        initialStateId = stateMachine.getInitialState().getId();

        // Construct machine
        construct();
    }

    /**
     * Construct the state transition table
     */
    public void construct() {

        for(State state : states) {

            // If state is final, then return to the initial state
            if(state.getType() == State.Type.FINAL) {
                for(int i=0; i < header.length; i++) {
                    transitionTable[state.getId()][i] = initialStateId;
                }

            } else {

                // On other state
                State onOther = state.getStateOn(Edge.Special.OTHER.getValue());

                // Populate table columns
                for(int i=0; i < header.length; i++) {
                    State newState = state.getStateOn(header[i]);

                    // If no state is defined for this move, use other move
                    if(newState == null) {
                        transitionTable[state.getId()][i] = onOther.getId();
                    } else {
                        transitionTable[state.getId()][i] = newState.getId();
                    }
                }
            }
        }
    }

    /**
     * Look up next state
     * @param state
     * @param c
     * @return state index
     */
    public int lookup(int state, char c) {

        // a-zA-Z
        if( (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))
            return transitionTable[state][headerIndexMap.get(Edge.Special.LETTER)];

        // 1-9
        if(c >= '1' && c <= '9')
            return transitionTable[state][headerIndexMap.get(Edge.Special.NONZERO)];

        // Space
        if(c == ' ' || c == '\t')
            return transitionTable[state][headerIndexMap.get(Edge.Special.SPACE)];

        // New line character
        // Note: All possible new lines will be replaced by \n. No need to cover other cases
        if(c == '\n')
            return transitionTable[state][headerIndexMap.get(Edge.Special.END_OF_LINE)];

        // End of file character
        // Note: The lexical analyzer will automatically inject \0 at the end of the input
        if(c == '\0')
            return transitionTable[state][headerIndexMap.get(Edge.Special.END_OF_FILE)];

        // Other characters
        for(int col=0; col<header.length; col++) {
            if((c+"").equals(header[col])) {
                return transitionTable[state][col];
            }
        }

        // Other ASCII
        return states[state].getStateOn(Edge.Special.OTHER.getValue()).getId();
    }

    /**
     * Formatted table
     */
    public String toString() {
        String output = "\t";

        for(int col = 0; col < header.length; col++)
            output += header[col] + "\t";

        output += "Bt\t";
        output += "Final [token]";

        output += "\n";
        for(int row = 0; row < states.length; row++) {
            output += row + "\t";
            for(int col = 0; col < header.length; col++) {
                output += transitionTable[row][col] + "\t";
            }
            output += (states[row].shouldBacktrack() ? "yes" : "no") + "\t";

            if(states[row].getType() == State.Type.FINAL)
                output += "yes [" + states[row].getToken() +"]";
            else
                output += "no";

            output += "\n";
        }
        return output;
    }
}
