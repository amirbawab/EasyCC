package code;

import core.annotation.Code;
import core.annotations.ParsePhase;
import core.code.GenericCodeGeneration;
import core.config.SemanticContext;
import core.models.DataModel;
import core.models.GenericModel;
import core.structure.symbol.SymbolTableTree;
import enums.SemanticActionEnum;
import models.IntegerModel;
import models.OpModel;
import models.ResultModel;

@Code(SemanticActionEnum.RESULT)
public class ResultCode extends GenericCodeGeneration {

    @ParsePhase(1)
    public void solve(SemanticContext semanticContext, SymbolTableTree symbolTableTree) {
        postOrder(((ResultModel) semanticContext.getModel()).getResultModel());
    }

    private void postOrder(GenericModel genericModel) {
        if(genericModel instanceof OpModel) {
            OpModel opModel = (OpModel) genericModel;
            postOrder(opModel.getLeftInt());
            postOrder(opModel.getRightInt());
            System.out.println(((DataModel)opModel.getSymbolModel()).getLexicalToken().getValue());
        } else if(genericModel instanceof IntegerModel) {
            IntegerModel integerModel = (IntegerModel) genericModel;
            System.out.println(integerModel.getInteger());
        }
    }
}
