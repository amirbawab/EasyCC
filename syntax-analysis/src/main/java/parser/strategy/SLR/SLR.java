package parser.strategy.SLR;

import grammar.Grammar;
import parser.strategy.ParseStrategy;
import parser.strategy.SLR.structure.machine.SLRStateMachine;
import token.AbstractToken;
import token.NonTerminalToken;

public class SLR extends ParseStrategy {

    private SLRStateMachine stateMachine;

    public SLR(Grammar grammar) {
        super(grammar);
        stateMachine = new SLRStateMachine(grammar);
    }

    /**
     * Get LR state machine
     * @return LR state machine
     */
    public SLRStateMachine getStateMachine() {
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
