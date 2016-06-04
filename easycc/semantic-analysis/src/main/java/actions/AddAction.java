package actions;

import core.actions.GenericAction;
import core.annotations.ParsePhase;
import core.annotations.SemanticAction;
import core.config.SemanticContext;
import core.structure.SemanticStack;
import models.ArithmeticResultModel;

@SemanticAction("add")
public class AddAction extends GenericAction {

    @ParsePhase(1)
    public void addIntegers(SemanticStack semanticStack, SemanticContext semanticContext) {
        ArithmeticResultModel arithmeticResultModel = ((ArithmeticResultModel)semanticContext.getModel());
        arithmeticResultModel.setRightModel(semanticStack.pop());
        arithmeticResultModel.setLeftModel(semanticStack.pop());
        semanticStack.push(arithmeticResultModel);
    }
}
