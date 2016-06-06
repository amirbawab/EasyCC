package parser.strategy;

import token.AbstractSyntaxToken;
import token.AbstractToken;

public interface ParseStrategyListener {
    void actionCall(AbstractSyntaxToken syntaxToken, AbstractToken lexicalToken, int phase);
    int getParsePhase();
}
