package machine;

import com.bethecoder.ascii_table.ASCIITable;
import helper.LexicalHelper;
import machine.json.Edge;
import machine.json.State;

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
     * Get state a given row
     * @param row
     * @return state
     */
    public State getStateAtRow(int row) {
        return states[row];
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
        if(c == LexicalHelper.EOL)
            return transitionTable[state][headerIndexMap.get(Edge.Special.END_OF_LINE)];

        // End of file character
        // Note: The lexical analyzer will automatically inject \0 at the end of the input
        if(c == LexicalHelper.EOF)
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

        // Store data
        String[] modHeader = new String[header.length + 4];
        String[][] data = new String[states.length][header.length+4];

        // Create header
        modHeader[0] = "";
        for(int i=0; i < header.length; i++) {
            modHeader[i+1] = header[i];
        }
        modHeader[header.length+1] = "Backtrack";
        modHeader[header.length+2] = "Final";
        modHeader[header.length+3] = "AbstractToken";

        for(int row=0; row < states.length; row++) {
            data[row][0] = row+"";
            for(int col=0; col < header.length; col++) {
                data[row][col+1] = transitionTable[row][col]+"";
            }
            data[row][header.length+1] = states[row].shouldBacktrack() ? "Yes" : "No";
            data[row][header.length+2] = states[row].getType() == State.Type.FINAL ? "Yes" : "No";
            data[row][header.length+3] = states[row].getType() == State.Type.FINAL ? states[row].getToken() : "";
        }

        return ASCIITable.getInstance().getTable(modHeader, data);
    }
}
