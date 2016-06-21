package parser.strategy.SLR;

import grammar.Grammar;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import parser.strategy.ParseStrategy;
import parser.strategy.SLR.structure.machine.SLRStateMachine;
import parser.strategy.SLR.structure.table.LRTable;
import token.AbstractToken;
import token.NonTerminalToken;

public class SLR extends ParseStrategy {

    // Logger
    private Logger l = LogManager.getFormatterLogger(getClass());

    private SLRStateMachine stateMachine;
    private LRTable table;

    public SLR(Grammar grammar) {
        super(grammar);
        stateMachine = new SLRStateMachine(grammar);
        table = new LRTable(stateMachine);

        // Print table
        l.info("Printing SLR parser table:\n" + table);
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
