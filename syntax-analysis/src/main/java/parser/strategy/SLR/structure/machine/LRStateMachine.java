package parser.strategy.SLR.structure.machine;

import grammar.Grammar;
import parser.strategy.SLR.structure.machine.item.LRItem;
import parser.strategy.SLR.structure.machine.node.LRItemNode;
import token.AbstractSyntaxToken;
import token.DotToken;
import token.SyntaxTokenFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class from which all other state machines should inherit from
 */

public abstract class LRStateMachine {
    List<LRItemNode> nodes;
    LRItemNode rootNode;
    Grammar grammar;

    LRStateMachine(Grammar grammar) {
        this.grammar = grammar;
        nodes = new ArrayList<>();
    }
    abstract void addClosure(LRItemNode itemNode);

    /**
     * Get nodes
     * @return nodes
     */
    public List<LRItemNode> getNodes() {
        return nodes;
    }

    /**
     * Generate a key for an item
     * @param item
     * @return key
     */
    String generateItemKey(LRItem item) {
        String key = "";
        for(int i = 0; i < item.getRHS().size() && !(item.getRHS().get(i) instanceof DotToken); i++) {
            key += "::" + item.getRHS().get(i).getOriginalValue();
        }
        return key;
    }

    /**
     * Get state machine grammar
     * @return grammar
     */
    public Grammar getGrammar() {
        return grammar;
    }
}
