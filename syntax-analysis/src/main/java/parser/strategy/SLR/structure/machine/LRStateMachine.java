package parser.strategy.SLR.structure.machine;

import grammar.Grammar;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import token.*;

import java.util.*;

/**
 * LR State machine
 */

public class LRStateMachine {

    private LRItemNode rootNode;
    private Grammar grammar;
    private AbstractSyntaxToken dotToken;

    // Logger
    private Logger l = LogManager.getFormatterLogger(getClass());

    public LRStateMachine(Grammar grammar) {
        this.grammar = grammar;
        dotToken = SyntaxTokenFactory.createDotToken();
    }

    public void construct() {
        List<List<AbstractSyntaxToken>> startProductions = grammar.getProductions().get(grammar.getStart());
        Map<String, LRItemNode> nodeMap = new HashMap<>();
        rootNode = new LRItemNode();

        if(startProductions.size() == 0) {
            l.error("No productions found for the start non-terminal.");
            return;
        }

        // Loop on all start non-terminal productions
        for(List<AbstractSyntaxToken> production : startProductions) {

            // Place a dot token at the beginning of the item
            LRItem item = new LRItem();
            item.setLHS(grammar.getStart());
            item.getRHS().add(dotToken);
            for(AbstractSyntaxToken syntaxToken : production) {
                if(syntaxToken instanceof TerminalToken || syntaxToken instanceof NonTerminalToken) {
                    item.getRHS().add(syntaxToken);
                }
            }

            // Add item to the node
            rootNode.addItem(item);
        }

        nodeMap.put(generateItemKey(rootNode.getItemList().get(0)), rootNode);
        Queue<LRItemNode> nodeQueue = new LinkedList<>();
        nodeQueue.offer(rootNode);

        while(!nodeQueue.isEmpty()) {

            // Dequeue node and add closure items
            LRItemNode topNode = nodeQueue.poll();
            addClosure(topNode);

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
     * Get nodes
     * @return nodes
     */
    public Set<LRItemNode> getNodes() {
        Set<LRItemNode> visitedNode = new HashSet<>();
        Queue<LRItemNode> nodeQueue = new LinkedList<>();
        nodeQueue.offer(rootNode);
        visitedNode.add(rootNode);
        while(!nodeQueue.isEmpty()) {
            LRItemNode topNode = nodeQueue.poll();
            for(LRTransition transition : topNode.getTransitionList()) {
                if(!visitedNode.contains(transition.getToItemNode())) {
                    visitedNode.add(transition.getToItemNode());
                    nodeQueue.offer(transition.getToItemNode());
                }
            }
        }
        return visitedNode;
    }

    /**
     * Generate a key for an item
     * @param item
     * @return key
     */
    private String generateItemKey(LRItem item) {
        String key = "";
        for(int i=0; i < item.getRHS().size() && !(item.getRHS().get(i) instanceof DotToken); i++) {
            key += "::" + item.getRHS().get(i).getOriginalValue();
        }
        return key;
    }

    /**
     * Generate item closure
     * @param itemNode
     */
    public void addClosure(LRItemNode itemNode) {

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
                        LRItem newItem = new LRItem();
                        newItem.setLHS(tokenAfterDot.getValue());
                        newItem.getRHS().add(dotToken);
                        for (AbstractSyntaxToken syntaxToken : production) {
                            if(syntaxToken instanceof TerminalToken || syntaxToken instanceof NonTerminalToken) {
                                newItem.getRHS().add(syntaxToken);
                            }
                        }
                        itemNode.getItemList().add(newItem);
                    }
                }
            }
        }
    }
}
