package parser.strategy.SLR.structure.machine;

import grammar.Grammar;
import token.AbstractSyntaxToken;

import java.util.ArrayList;
import java.util.List;

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

    public void construct() {
        List<List<AbstractSyntaxToken>> startProductions = grammar.getProductions().get(grammar.getStart());

    }
}
