package creator;

import core.annotations.ActionModel;
import core.models.GenericModel;
import models.ArithmeticResultModel;
import models.IntegerModel;

/**
 * All models that are expected to be created should be defined in this Factory class
 * Each time a semantic action is called, the corresponding model will be created. If a semantic action is not defined,
 * a generic one will be created
 */

public class ModelsFactory {

    @ActionModel("integer")
    public static GenericModel createIntegerModel() {
        return new IntegerModel();
    }

    @ActionModel("add")
    public static GenericModel createArithmeticResultModel() {return new ArithmeticResultModel();}
}
