package parser.strategy;

import token.ActionToken;

public interface ParseStrategyListener {
    void init();
    void actionCall(ActionToken actionToken, int phase);
    int getParsePhase();
    void logSymbolTables();
}
