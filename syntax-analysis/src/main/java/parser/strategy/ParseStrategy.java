package parser.strategy;

import grammar.Grammar;
import token.AbstractToken;

import java.util.List;

/**
 * Different parsing strategies should implement this interface
 */

public abstract class ParseStrategy {
    protected Grammar grammar;
    protected ParseStrategyListener parseStrategyListener;

    public ParseStrategy(Grammar grammar) {
        this.grammar = grammar;
    }

    /**
     * Parse lexical tokens
     * @param lexicalTokenList
     * @return true if parse is successful
     */
    public abstract boolean parse(AbstractToken lexicalTokenList);

    /**
     * Set listener instance
     * @param parseStrategyListener
     */
    public void setParseStrategyListener(ParseStrategyListener parseStrategyListener) {
        this.parseStrategyListener = parseStrategyListener;
    }
}
