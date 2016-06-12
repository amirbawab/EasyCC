package creator;

import core.annotations.ActionModel;
import core.models.DataModel;
import core.models.GenericModel;
import enums.SemanticActionEnum;
import models.IntegerModel;
import models.OpModel;
import models.ResultModel;

/**
 * All models that are expected to be created should be defined in this Factory class
 * Each time a semantic action is called, the corresponding model will be created. If a semantic action is not defined,
 * a generic one will be created
 */

public class ModelsFactory {

    @ActionModel(SemanticActionEnum.OP)
    public static GenericModel createOpModel() {
        return new OpModel();
    }

    @ActionModel(SemanticActionEnum.INTEGER)
    public static GenericModel createIntegerModel() {
        return new IntegerModel();
    }

    @ActionModel(SemanticActionEnum.RESULT)
    public static GenericModel createResultModel() {
        return new ResultModel();
    }

    @ActionModel(SemanticActionEnum.SYMBOL)
    public static GenericModel createSymbolModel() {
        return new DataModel();
    }
}
