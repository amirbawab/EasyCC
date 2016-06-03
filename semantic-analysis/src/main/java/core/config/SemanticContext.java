package core.config;

import core.models.GenericModel;

/**
 * This class is responsible for passing the information from the syntax analyzer
 * to the semantic actions
 */

public class SemanticContext {
    GenericModel model;

    public GenericModel getModel() {
        return model;
    }

    public void setModel(GenericModel model) {
        this.model = model;
    }
}
