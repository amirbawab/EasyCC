package core.config;

import core.structure.symbol.SymbolTableTree;

/**
 * Listener for methods performed by a Semantic context object
 */

public interface SemanticContextListener {
    void generateCode(SemanticContext semanticContext);
}
