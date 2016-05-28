package parser.strategy.LLPP;

import grammar.Grammar;
import parser.strategy.LLPP.cell.LLPPAbstractTableCell;

import java.util.HashMap;
import java.util.Map;

/**
 * This class represent the table structure and data. It's used by the predictive parser to decide what
 * would be the next step
 */

public class LLPPTable {

    private LLPPAbstractTableCell table[][];
    private Map<String, Integer> terminalIndexMap, nonTerminalIndexMap;

    public LLPPTable(Grammar grammar) {
        table = new LLPPAbstractTableCell[grammar.getNonTerminals().size()][grammar.getTerminals().size()];
        terminalIndexMap = new HashMap<>();
        nonTerminalIndexMap = new HashMap<>();
    }

    
}
