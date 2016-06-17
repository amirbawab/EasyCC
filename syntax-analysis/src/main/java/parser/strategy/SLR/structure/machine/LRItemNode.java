package parser.strategy.SLR.structure.machine;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represent a node in the LR state machine
 */

public class LRItemNode {

    // Node components
    private List<LRItem> itemList;
    private List<LRTransition> transitionList;

    public LRItemNode() {
        itemList = new ArrayList<>();
        transitionList = new ArrayList<>();
    }

    public void addItem(LRItem item) {
        itemList.add(item);
    }

    public List<LRItem> getItemList() {
        return itemList;
    }

    public void addTransition(LRTransition transition) {
        transitionList.add(transition);
    }

    public List<LRTransition> getTransitionList() {
        return transitionList;
    }
}
