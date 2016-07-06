package parser.strategy.LR.structure.parse.stack;

import parser.strategy.LR.structure.machine.node.LRItemNode;

/**
 * Abstract class for all stack entries
 */

public abstract class LRAbstractStackEntry {
    private LRItemNode node;

    public LRItemNode getNode() {
        return node;
    }

    public void setNode(LRItemNode node) {
        this.node = node;
    }
}
