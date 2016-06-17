package parser.strategy.SLR.structure.machine;

import token.AbstractSyntaxToken;

/**
 * Edge between two nodes
 */

public class LRTransition {
    private LRProductionNode fromProductionNode, toProductionNode;
    private AbstractSyntaxToken value;

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

    public AbstractSyntaxToken getValue() {
        return value;
    }

    public void setValue(AbstractSyntaxToken value) {
        this.value = value;
    }
}
