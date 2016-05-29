package parser.strategy.LLPP;

import grammar.Grammar;
import helper.SyntaxHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import parser.strategy.ParseStrategy;
import token.AbstractSyntaxToken;
import token.LexicalToken;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        // Check if the grammar is correct
        validate();

        // Print table
        l.info("Printing Predictive parser table:\n" + llppTable);
    }

    @Override
    public boolean parse(List<LexicalToken> lexicalTokenList) {

        return false;
    }

    /**
     * Check if the grammar is LL
     * A -> B | C
     * Cond 1: First(B) ^ First(C) = {}
     * Cond 2: If First(A) contains "", then First(A) & Follow(A) = {}
     */
    public void validate() {

        // Loop on non-terminal
        for(String nonTerminal : grammar.getProductions().keySet()) {

            // Cond 1
            Set<String> uniqueFirstSetValues = new HashSet<>();
            for(List<AbstractSyntaxToken> rule : grammar.getProductions().get(nonTerminal)) {
                for(String token : grammar.getRuleFirstSetMap().get(rule)) {
                    if(uniqueFirstSetValues.contains(token)) {
                        String message = "The first set of the rules of the non-terminal: " + nonTerminal + " intersect at " + token;
                        l.error(message);
                        throw new LLPPException(message);
                    } else {
                        uniqueFirstSetValues.add(token);
                    }
                }
            }

            // Cond 2
            if(uniqueFirstSetValues.contains(SyntaxHelper.EPSILON) && (uniqueFirstSetValues.retainAll(grammar.getFollowSetOfNonTerminal(nonTerminal)) & !uniqueFirstSetValues.isEmpty())) {
                String message = "The first and follow sets of the non-terminal: " + nonTerminal + " intersect at " + uniqueFirstSetValues;
                l.error(message);
                throw new LLPPException(message);
            }
        }
    }

    /**
     * Get PP Table
     * @return PP table
     */
    public LLPPTable getLlppTable() {
        return llppTable;
    }
}
