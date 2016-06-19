package parser.strategy.SLR;

import grammar.Grammar;
import org.apache.commons.lang3.StringUtils;
import parser.strategy.ParseStrategy;
import parser.strategy.ParseStrategyListener;
import parser.strategy.SLR.structure.machine.LRItem;
import parser.strategy.SLR.structure.machine.LRItemNode;
import parser.strategy.SLR.structure.machine.LRStateMachine;
import token.AbstractToken;
import token.NonTerminalToken;

public class SLR extends ParseStrategy {

    private LRStateMachine stateMachine;

    public SLR(Grammar grammar) {
        super(grammar);
        stateMachine = new LRStateMachine(grammar);
    }

    /**
     * Get LR state machine
     * @return LR state machine
     */
    public LRStateMachine getStateMachine() {
        return stateMachine;
    }

    @Override
    public boolean parse(AbstractToken lexicalTokenList) {
        return false;
    }

    @Override
    public NonTerminalToken getDerivationRoot() {
        return null;
    }
}
