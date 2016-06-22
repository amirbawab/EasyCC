package parser.strategy.SLR;

import grammar.Grammar;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import parser.strategy.ParseStrategy;
import parser.strategy.SLR.exceptions.SLRException;
import parser.strategy.SLR.structure.machine.SLRStateMachine;
import parser.strategy.SLR.structure.table.LRTable;
import token.AbstractSyntaxToken;
import token.AbstractToken;
import token.NonTerminalToken;

import java.util.List;

public class SLR extends ParseStrategy {

    // Logger
    private Logger l = LogManager.getFormatterLogger(getClass());

    private SLRStateMachine stateMachine;
    private LRTable table;

    public SLR(Grammar grammar) {
        super(grammar);

        // Check if the grammar is correct
        validate();

        // Initial components
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

    /**
     * Check if the grammar is LR
     * Cond 1: Start non-terminal should have only one production of the form A -> B
     * Cond 2: No other production should call the start non-terminal
     * TODO Check if action tokens should be allowed in initial production [Cond 1]
     */
    public void validate() {

        // Cond 1
        List<List<AbstractSyntaxToken>> startProductions = grammar.getProductions().get(grammar.getStart());
        if(startProductions.size() != 1 || startProductions.get(0).size() != 1) {
            String message = "The initial production should be of the form A -> B";
            l.error(message);
            throw new SLRException(message);
        }

        // Cond 2
        for(List<List<AbstractSyntaxToken>> productionList : grammar.getProductions().values()) {
            for(List<AbstractSyntaxToken> production : productionList) {
                for(AbstractSyntaxToken syntaxToken : production) {
                    if(syntaxToken instanceof NonTerminalToken && syntaxToken.getValue().equals(grammar.getStart())) {
                        String message = "No production should include the starting non-terminal: " + grammar.getStart();
                        l.error(message);
                        throw new SLRException(message);
                    }
                }
            }
        }
    }

    @Override
    public NonTerminalToken getDerivationRoot() {
        return null;
    }
}
