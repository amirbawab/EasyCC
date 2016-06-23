package parser.strategy.SLR.structure.machine;

import grammar.Grammar;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import parser.strategy.SLR.exceptions.SLRException;
import parser.strategy.SLR.structure.machine.item.LRItem;
import parser.strategy.SLR.structure.machine.node.LRItemNode;
import parser.strategy.SLR.structure.machine.transition.LRTransition;
import token.*;

import java.util.*;

/**
 * LR State machine
 */

public class SLRStateMachine extends LRStateMachine {

    // Logger
    private Logger l = LogManager.getFormatterLogger(getClass());

    public SLRStateMachine(Grammar grammar) {
        super(grammar);

        // Construct the state machine based  on the grammar
        construct();
    }

    /**
     * Construct the nodes and transition of the state machine
     */
    private void construct() {
        List<List<AbstractSyntaxToken>> startProductions = grammar.getProductions().get(grammar.getStart());
        Map<String, LRItemNode> nodeMap = new HashMap<>();
        rootNode = new LRItemNode();

        if(startProductions.size() == 0) {
            String message = "No productions found for the start non-terminal.";
            l.error(message);
            throw new SLRException(message);
        }

        // Loop on all start non-terminal productions
        for(List<AbstractSyntaxToken> production : startProductions) {

            // Add item to the node
            rootNode.addItem(new LRItem(grammar.getStart(), production));
        }

        nodeMap.put(generateItemKey(rootNode.getItemList().get(0)), rootNode);
        Queue<LRItemNode> nodeQueue = new LinkedList<>();
        nodeQueue.offer(rootNode);

        while(!nodeQueue.isEmpty()) {

            // Dequeue node and add closure items
            LRItemNode topNode = nodeQueue.poll();
            addClosure(topNode);
            topNode.setId(nodes.size());
            nodes.add(topNode);

            // Loop on all items
            for(LRItem item : topNode.getItemList()) {
                AbstractSyntaxToken tokenAfterDot = item.getTokenAfterDot();

                // If dot token is not at the end
                if(tokenAfterDot != null) {

                    // Get transition if found
                    LRTransition newTransition = topNode.getTransition(tokenAfterDot);

                    // Clone item
                    LRItem cloneItem = item.clone();
                    cloneItem.shiftDotRight();

                    // If transition not found, create one
                    if(newTransition == null) {
                        newTransition = new LRTransition();
                        newTransition.setFromItemNode(topNode);
                        newTransition.setValue(tokenAfterDot);

                        // Check if node is already created
                        String key = generateItemKey(cloneItem);
                        if(!nodeMap.containsKey(key)) {
                            newTransition.setToItemNode(new LRItemNode());
                            nodeQueue.offer(newTransition.getToItemNode());
                            nodeMap.put(key, newTransition.getToItemNode());
                        } else {
                            newTransition.setToItemNode(nodeMap.get(key));
                        }
                        topNode.addTransition(newTransition);
                    }

                    // If item doesn't already exists, add it
                    if(!newTransition.getToItemNode().hasItem(cloneItem)) {
                        newTransition.getToItemNode().addItem(cloneItem);
                    }
                }
            }
        }
    }

    /**
     * Generate item closure
     * @param itemNode
     */
    @Override
    void addClosure(LRItemNode itemNode) {

        Set<String> LHSVisited = new HashSet<>();
        // Loop on all items of the node
        // Note: - itemNode.getItemList().size() value changes the more items are added in the loop
        //       - The order of items is important to select the destination node
        for(int i=0; i < itemNode.getItemList().size(); i++) {
            LRItem item = itemNode.getItemList().get(i);

            // Get the non-terminal token after the dot
            AbstractSyntaxToken tokenAfterDot = item.getTokenAfterDot();
            if (tokenAfterDot != null && tokenAfterDot instanceof NonTerminalToken) {

                // If non-terminal was not already visited
                if(!LHSVisited.contains(tokenAfterDot.getValue())) {

                    // Mark visited
                    LHSVisited.add(tokenAfterDot.getValue());

                    // Get and loop on productions for the non-terminal token
                    List<List<AbstractSyntaxToken>> productions = grammar.getProductions().get(tokenAfterDot.getValue());
                    for (List<AbstractSyntaxToken> production : productions) {
                        itemNode.getItemList().add(new LRItem(tokenAfterDot.getValue(), production));
                    }
                }
            }
        }
    }
}
