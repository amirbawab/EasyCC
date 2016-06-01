package parser.strategy;

import token.AbstractSyntaxToken;
import token.AbstractToken;

public interface ParseStrategyListener {
    public void actionCall(AbstractSyntaxToken syntaxToken, AbstractToken lexicalToken, int phase);
}
