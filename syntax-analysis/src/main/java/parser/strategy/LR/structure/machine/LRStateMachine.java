package parser.strategy.LR.structure.machine;

import grammar.Grammar;
import parser.strategy.LR.structure.machine.node.LRItemNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class from which all other state machines should inherit from
 */

public abstract class LRStateMachine {
    List<LRItemNode> nodes;
    LRItemNode rootNode;
    Grammar grammar;

    LRStateMachine(Grammar grammar) {
        this.grammar = grammar;
        nodes = new ArrayList<>();
    }
    abstract void addClosure(LRItemNode itemNode);

    /**
     * Get nodes
     * @return nodes
     */
    public List<LRItemNode> getNodes() {
        return nodes;
    }

    /**
     * Get state machine grammar
     * @return grammar
     */
    public Grammar getGrammar() {
        return grammar;
    }
}
