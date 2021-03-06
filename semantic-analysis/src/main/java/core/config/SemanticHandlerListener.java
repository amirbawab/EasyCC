package core.config;

import core.structure.symbol.SymbolTableTree;
import token.ActionToken;

/**
 * Listener for semantic handler calls
 */

public interface SemanticHandlerListener {
    void postSemanticHandler(ActionToken actionToken, int phase, SemanticContext semanticContext, SymbolTableTree symbolTableTree);
}
