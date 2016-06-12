package actions;

import core.actions.GenericAction;
import core.annotations.ParsePhase;
import core.annotations.SemanticAction;
import core.config.SemanticContext;
import core.structure.SemanticStack;
import core.structure.symbol.SymbolTableTree;
import enums.SemanticActionEnum;
import models.IntegerModel;

@SemanticAction(value = SemanticActionEnum.INTEGER, stable = true)
public class IntegerAction extends GenericAction {

    @ParsePhase(1)
    public void store(SemanticContext semanticContext, SemanticStack semanticStack, SymbolTableTree symbolTableTree) {

        // Push IntegerModel in the semantic stack
        semanticStack.push(semanticContext.getModel());
    }
}
