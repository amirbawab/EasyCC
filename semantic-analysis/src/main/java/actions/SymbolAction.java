package actions;

import core.actions.GenericAction;
import core.annotations.ParsePhase;
import core.annotations.SemanticAction;
import core.config.SemanticContext;
import core.structure.SemanticStack;
import core.structure.symbol.SymbolTableTree;
import enums.SemanticActionEnum;

@SemanticAction(value = SemanticActionEnum.SYMBOL, stableOnly = true)
public class SymbolAction extends GenericAction {

    @ParsePhase(1)
    public void store(SemanticContext semanticContext, SemanticStack semanticStack, SymbolTableTree symbolTableTree) {

        // Push DataModel in the semantic stack
        semanticStack.push(semanticContext.getModel());
    }
}
