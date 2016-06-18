package parser.strategy.SLR.structure.machine;

import grammar.Grammar;
import token.*;

import java.util.*;

/**
 * LR State machine
 */

public class LRStateMachine {

    private List<LRItemNode> nodes;
    private Grammar grammar;

    public LRStateMachine(Grammar grammar) {
        nodes = new ArrayList<>();
        this.grammar = grammar;
    }

    public List<LRItemNode> getNodes() {
        return nodes;
    }

    // TODO Override this one ?
    public List<LRItem> getItemClosure(LRItem item) {
        List<LRItem> itemList = new ArrayList<>();
        return itemList;
    }

    @Override
    public void construct() {
        List<List<AbstractSyntaxToken>> startProductions = grammar.getProductions().get(grammar.getStart());
        LRItemNode initialNode = new LRItemNode();

        // Loop on all start non-terminal productions
        for(List<AbstractSyntaxToken> production : startProductions) {

            // Place a dot token at the beginning of the item
            LRItem item = new LRItem();
            item.getRHS().add(SyntaxTokenFactory.createDotToken());
            for(AbstractSyntaxToken token : production) {
                item.getRHS().add(token);
            }

            // Add item to the node
            initialNode.addHeaderItem(item);
        }

        Queue<LRItemNode> nodeQueue = new LinkedList<>();
        Set<LRItemNode> visitedNodeMap = new HashSet<>(); // Make this a map for visiting and visited ?
        nodeQueue.add(initialNode);
        while(!nodeQueue.isEmpty()) {

            LRItemNode topNode = nodeQueue.poll();
            visitedNodeMap.add(topNode);

            // Find closure here ??

            // Loop on all items for the top node
            Map<String, LRTransition> transitionMap = new HashMap<>();
            for(LRItem item : topNode.getItemList()) {
                AbstractSyntaxToken tokenAfterDot = item.getTokenAfterDot();
                if(tokenAfterDot != null) {
                    if(transitionMap.containsKey(tokenAfterDot.getOriginalValue())) {

                    }
                }
            }
        }
    }


}
