package parser.strategy.SLR;

import grammar.Grammar;
import parser.strategy.ParseStrategy;
import parser.strategy.ParseStrategyListener;
import token.AbstractToken;
import token.NonTerminalToken;

public class SLR extends ParseStrategy {
    public SLR(Grammar grammar) {
        super(grammar);
    }

    @Override
    public boolean parse(AbstractToken lexicalTokenList) {
        return false;
    }

    @Override
    public NonTerminalToken getDerivationRoot() {
        return null;
    }

    @Override
    public void setParseStrategyListener(ParseStrategyListener parseStrategyListener) {
        super.setParseStrategyListener(parseStrategyListener);
    }

    @Override
    public ParseStrategyListener getParseStrategyListener() {
        return super.getParseStrategyListener();
    }
}
