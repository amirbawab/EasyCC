package parser.strategy.SLR.structure.machine;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represent a node in the LR state machine
 */

public class LRProductionNode {

    // Node components
    private List<LRProduction> productionList;
    private List<LRTransition> transitionList;

    public LRProductionNode() {
        productionList = new ArrayList<>();
        productionList = new ArrayList<>();
    }

    public void addProduction(LRProduction production) {
        productionList.add(production);
    }

    public List<LRProduction> getProductionList() {
        return productionList;
    }

    public void addTransition(LRTransition transition) {
        transitionList.add(transition);
    }

    public List<LRTransition> getTransitionList() {
        return transitionList;
    }
}
