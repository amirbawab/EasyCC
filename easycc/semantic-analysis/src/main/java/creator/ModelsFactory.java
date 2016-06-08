package creator;

import core.annotations.ActionModel;
import core.models.DataModel;
import core.models.GenericModel;

/**
 * All models that are expected to be created should be defined in this Factory class
 * Each time a semantic action is called, the corresponding model will be created. If a semantic action is not defined,
 * a generic one will be created
 */

public class ModelsFactory {

    @ActionModel("createClassTableAndEntry")
    public static GenericModel createCreateClassModel() {
        return new DataModel();
    }
}
