package machine;

import machine.json.Edge;
import machine.json.State;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StateMachineTest {

    @Test
    public void test_addState_manualMachine_success() {
        StateMachine stateMachine = new StateMachine();
        stateMachine.addState(new State());
        assertEquals(1, stateMachine.getAllStates().size());
    }

    @Test(expected=StateMachineException.class)
    public void test_addState_manualMachine_duplicateState() {
        StateMachine stateMachine = new StateMachine();
        State A = new State();
        State duplicate = new State();
        A.setName("A");
        duplicate.setName(A.getName());
        stateMachine.addState(A);
        stateMachine.addState(duplicate);
    }

    @Test(expected=StateMachineException.class)
    public void test_addState_manualMachine_multipleInitialStates() {
        StateMachine stateMachine = new StateMachine();
        State A = new State();
        State B = new State();
        A.setName("A");
        A.setType(State.Type.INITIAL.getValue());
        B.setName("B");
        B.setType(State.Type.INITIAL.getValue());
        stateMachine.addState(A);
        stateMachine.addState(B);
    }

    @Test
    public void test_addEdge_manualMachine_success() {
        StateMachine stateMachine = new StateMachine();
        State A = new State();
        State B = new State();
        A.setName("A");
        B.setName("B");
        Edge edge = new Edge();
        edge.setFrom(A.getName());
        edge.setTo(B.getName());
        stateMachine.addState(A);
        stateMachine.addState(B);
        stateMachine.addEdge(edge);
        assertEquals(1, stateMachine.getStateByName(A.getName()).getOutEdges().size());
    }

    @Test(expected=StateMachineException.class)
    public void test_addEdge_manualMachine_duplicateEdge() {
        StateMachine stateMachine = new StateMachine();
        State A = new State();
        State B = new State();
        A.setName("A");
        B.setName("B");
        Edge edge = new Edge();
        edge.setFrom(A.getName());
        edge.setTo(B.getName());
        edge.setValue("value");
        Edge duplicate = new Edge();
        duplicate.setFrom(A.getName());
        duplicate.setTo(B.getName());
        duplicate.setValue(edge.getValue());
        stateMachine.addState(A);
        stateMachine.addState(B);
        stateMachine.addEdge(edge);
        stateMachine.addEdge(duplicate);
        assertEquals(1, stateMachine.getStateByName(A.getName()).getOutEdges().size());
    }

    @Test
    public void test_removeState_manualMachine1() {
        StateMachine stateMachine = new StateMachine();
        State state = new State();
        state.setName("A");
        stateMachine.addState(state);
        stateMachine.removeState(state.getName());
        assertEquals(0, stateMachine.getAllStates().size());
    }

    @Test
    public void test_removeState_manualMachine2() {
        StateMachine stateMachine = new StateMachine();
        State A = new State();
        State B = new State();
        A.setName("A");
        B.setName("B");
        Edge edge = new Edge();
        edge.setFrom(A.getName());
        edge.setTo(B.getName());
        stateMachine.addState(A);
        stateMachine.addState(B);
        stateMachine.addEdge(edge);
        stateMachine.removeState(B.getName());
        assertEquals(0, stateMachine.getAllStates().iterator().next().getOutEdges().size());
    }

    @Test
    public void test_removeEdge_manualMachine_success() {
        StateMachine stateMachine = new StateMachine();
        State A = new State();
        State B = new State();
        A.setName("A");
        B.setName("B");
        Edge edge = new Edge();
        edge.setFrom(A.getName());
        edge.setTo(B.getName());
        edge.setValue("value");
        stateMachine.addState(A);
        stateMachine.addState(B);
        stateMachine.addEdge(edge);
        stateMachine.removeEdge(edge.getFrom(), edge.getTo(), edge.getValue());
        assertEquals(2, stateMachine.getAllStates().size());
        for(State state : stateMachine.getAllStates()) {
            assertEquals(0, state.getOutEdges().size());
        }
    }
}
