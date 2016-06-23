package parser.strategy.SLR.structure.machine.node;

import parser.strategy.SLR.structure.machine.transition.LRTransition;
import parser.strategy.SLR.structure.machine.item.LRItem;
import token.AbstractSyntaxToken;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represent a node in the LR state machine
 */

public class LRItemNode {

    // Node components
    private int id;
    private List<LRItem> itemList;
    private List<LRTransition> transitionList;

    public LRItemNode() {
        itemList = new ArrayList<>();
        transitionList = new ArrayList<>();
    }

    /**
     * Add item
     * @param item
     */
    public void addItem(LRItem item) {
        itemList.add(item);
    }

    public void addTransition(LRTransition transition) {
        transitionList.add(transition);
    }

    /**
     * Check if node has an item
     * @param item
     * @return true if item exists in node
     */
    public boolean hasItem(LRItem item) {
        for(LRItem nodeItem : itemList) {
            if(nodeItem.equals(item)) {
                return true;
            }
        }
        return false;
    }

    public List<LRTransition> getTransitionList() {
        return transitionList;
    }

    /**
     * Get transition by token
     * @param token
     * @return Transition if found. Otherwise null
     */
    public LRTransition getTransition(AbstractSyntaxToken token) {
        for(LRTransition transition : transitionList) {
            if(transition.getValue().getOriginalValue().equals(token.getOriginalValue())) {
                return transition;
            }
        }
        return null;
    }

    /**
     * Get all items
     * Order of items is important
     * @return list of items
     */
    public List<LRItem> getItemList() {
        return itemList;
    }

    /**
     * Get node id
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Set node id
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }
}
