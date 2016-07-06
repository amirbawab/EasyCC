package parser.strategy.LR.structure.table.cell;

import parser.strategy.LR.structure.machine.item.LRItem;

/**
 * Table cell for reduce
 */

public class LRReduceCell extends LRAbstractTableCell {
    private LRItem item;
    private int ruleId;

    public LRItem getItem() {
        return item;
    }

    public void setItem(LRItem item) {
        this.item = item;
    }

    public int getRuleId() {
        return ruleId;
    }

    public void setRuleId(int ruleId) {
        this.ruleId = ruleId;
    }
}
