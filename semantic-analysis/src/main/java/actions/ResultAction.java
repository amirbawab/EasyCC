package actions;

import core.actions.GenericAction;
import core.annotations.ParsePhase;
import core.annotations.SemanticAction;
import core.config.SemanticContext;
import core.structure.SemanticStack;
import core.structure.symbol.SymbolTableTree;
import enums.SemanticActionEnum;
import models.ResultModel;

@SemanticAction(value = SemanticActionEnum.RESULT, stableOnly = true)
public class ResultAction extends GenericAction {

    @ParsePhase(1)
    public void solve(SemanticContext semanticContext, SemanticStack semanticStack, SymbolTableTree symbolTableTree) {
        ResultModel resultModel = (ResultModel) semanticContext.getModel();
        resultModel.setResultModel(semanticStack.pop());
    }
}
