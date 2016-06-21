package actions;

import core.actions.GenericAction;
import core.annotations.ParsePhase;
import core.annotations.SemanticAction;
import core.config.SemanticContext;
import core.models.LogicalErrorModel;
import core.structure.SemanticStack;
import core.structure.symbol.SymbolTableTree;
import enums.SemanticActionEnum;

@SemanticAction(value = SemanticActionEnum.INTEGER, stableOnly = false)
public class IntegerAction extends GenericAction {

    @ParsePhase(1)
    public void store(SemanticContext semanticContext, SemanticStack semanticStack, SymbolTableTree symbolTableTree) {

        if(semanticContext.isStable()) {
            // Push IntegerModel in the semantic stack
            semanticStack.push(semanticContext.getModel());
        } else {
            // Push LogicalErrorModel in the semantic stack
            semanticStack.push(new LogicalErrorModel());
        }
    }
}
