package parser.strategy.SLR.structure.table;

import parser.strategy.SLR.structure.machine.LRStateMachine;

/**
 * This class construct the table given a LR State machine
 */

public class LRTable {

    private LRStateMachine stateMachine;

    public LRTable(LRStateMachine stateMachine) {
        this.stateMachine = stateMachine;
    }
}
