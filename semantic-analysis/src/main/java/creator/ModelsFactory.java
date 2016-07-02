package creator;

import core.annotations.ActionModel;
import core.models.ASTModel;
import core.models.DataModel;
import core.models.GenericModel;
import enums.SemanticActionEnum;
import models.IntegerModel;
import models.OpModel;
import models.ResultModel;

/**
 * All models that are expected to be created should be defined in this Factory class
 * Each time a semantic action is called, the corresponding model will be created.
 */

public class ModelsFactory {

    @ActionModel(SemanticActionEnum.PRINT)
    public static GenericModel createPrintModel() {
        return new ASTModel();
    }
}
