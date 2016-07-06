package parser.strategy.LR.structure.machine.transition;

import parser.strategy.LR.structure.machine.node.LRItemNode;
import token.AbstractSyntaxToken;

/**
 * Edge between two nodes
 */

public class LRTransition {
    private LRItemNode fromItemNode, toItemNode;
    private AbstractSyntaxToken value;

    public LRItemNode getFromItemNode() {
        return fromItemNode;
    }

    public void setFromItemNode(LRItemNode fromItemNode) {
        this.fromItemNode = fromItemNode;
    }

    public LRItemNode getToItemNode() {
        return toItemNode;
    }

    public void setToItemNode(LRItemNode toItemNode) {
        this.toItemNode = toItemNode;
    }

    public AbstractSyntaxToken getValue() {
        return value;
    }

    public void setValue(AbstractSyntaxToken value) {
        this.value = value;
    }
}
