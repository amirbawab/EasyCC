package sidebar;

import machine.StateMachine;

public interface LeftSideBarListener {
    void refresh();
    void updateStateMachine(StateMachine stateMachine);
    String getJSON();
    void reset();
}
