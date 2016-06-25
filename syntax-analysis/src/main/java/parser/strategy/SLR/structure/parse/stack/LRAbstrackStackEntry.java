package parser.strategy.SLR.structure.parse.stack;

import parser.strategy.SLR.structure.machine.node.LRItemNode;

/**
 * Abstract class for all stack entries
 */

public abstract class LRAbstrackStackEntry {
    private LRItemNode node;

    public LRItemNode getNode() {
        return node;
    }

    public void setNode(LRItemNode node) {
        this.node = node;
    }
}
