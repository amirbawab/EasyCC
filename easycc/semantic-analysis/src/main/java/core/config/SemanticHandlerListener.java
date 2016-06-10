package core.config;

import core.structure.symbol.SymbolTableTree;
import token.ActionToken;

/**
 * Listener for semantic handler calls
 */

public interface SemanticHandlerListener {
    void generateCode(ActionToken actionToken, int phase, SemanticContext semanticContext, SymbolTableTree symbolTableTree);
}
