package actions;

import core.actions.GenericAction;
import core.annotations.ParsePhase;
import core.annotations.SemanticAction;
import core.config.SemanticContext;
import core.structure.SemanticStack;
import core.structure.symbol.SymbolTableTree;
import enums.SemanticActionEnum;
import models.OpModel;

@SemanticAction(value = SemanticActionEnum.OP, stableOnly = true)
public class OpAction extends GenericAction {

    @ParsePhase(1)
    public void calc(SemanticContext semanticContext, SemanticStack semanticStack, SymbolTableTree symbolTableTree) {

        // Pop two IntegerModel
        OpModel opModel = (OpModel) semanticContext.getModel();
        opModel.setRightInt(semanticStack.pop());
        opModel.setSymbol(semanticStack.pop());
        opModel.setLeftInt(semanticStack.pop());
        semanticStack.push(opModel);
    }
}
