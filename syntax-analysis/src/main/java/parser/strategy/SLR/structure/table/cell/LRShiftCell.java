package parser.strategy.SLR.structure.table.cell;

import parser.strategy.SLR.structure.machine.node.LRItemNode;

/**
 * Table cell for shift
 */

public class LRShiftCell extends LRAbstractTableCell {
    private LRItemNode node;
    private int nodeId;

    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public LRItemNode getNode() {
        return node;
    }

    public void setNode(LRItemNode node) {
        this.node = node;
    }
}
