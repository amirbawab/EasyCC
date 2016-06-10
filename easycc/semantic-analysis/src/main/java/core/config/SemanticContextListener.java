package core.config;

import core.structure.symbol.SymbolTableTree;

/**
 * Listener for methods performed by a Semantic context object
 */

public interface SemanticContextListener {
    void error(String message);
    void generateCode(SemanticContext semanticContext);
}
