package parser.strategy;

import grammar.Grammar;
import token.AbstractToken;

import java.util.List;

/**
 * Different parsing strategies should implement this interface
 */

public abstract class ParseStrategy {
    protected Grammar grammar;
    public ParseStrategy(Grammar grammar) {
        this.grammar = grammar;
    }

    public abstract boolean parse(List<AbstractToken> lexicalTokenList);
}
