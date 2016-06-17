package parser.strategy.SLR.structure;

import token.AbstractToken;

/**
 * Edge between two nodes
 */

public class LRTransition {
    private LRProductionNode fromProductionNode, toProductionNode;
    private AbstractToken value;

    public LRProductionNode getFromProductionNode() {
        return fromProductionNode;
    }

    public void setFromProductionNode(LRProductionNode fromProductionNode) {
        this.fromProductionNode = fromProductionNode;
    }

    public LRProductionNode getToProductionNode() {
        return toProductionNode;
    }

    public void setToProductionNode(LRProductionNode toProductionNode) {
        this.toProductionNode = toProductionNode;
    }

    public AbstractToken getValue() {
        return value;
    }

    public void setValue(AbstractToken value) {
        this.value = value;
    }
}
