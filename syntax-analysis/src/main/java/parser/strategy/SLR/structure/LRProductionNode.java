package parser.strategy.SLR.structure;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represent a node in the LR state machine
 */

public class LRProductionNode {

    // Node components
    private List<LRProduction> productionList;


    public LRProductionNode() {
        productionList = new ArrayList<>();
    }

    public void addProduction(LRProduction production) {
        productionList.add(production);
    }

    public List<LRProduction> getProductionList() {
        return productionList;
    }
}
