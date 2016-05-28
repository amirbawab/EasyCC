package parser.strategy.LLPP;

import grammar.Grammar;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import parser.strategy.ParseStrategy;

/**
 * Lef to Right - Leftmost Predictive parser
 */

public class LLPP extends ParseStrategy {

    // Logger
    private Logger l = LogManager.getFormatterLogger(getClass());

    // Components
    LLPPTable llppTable;

    public LLPP(Grammar grammar) {
        super(grammar);
        llppTable = new LLPPTable(grammar);

        // Print table
        l.info("Printing Predictive parser table:\n" + llppTable);
    }

    @Override
    public boolean parse() {

        return false;
    }

    /**
     * Check if the grammar is LL
     */
    public void validate() {

    }
}
