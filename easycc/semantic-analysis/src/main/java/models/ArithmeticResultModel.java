package models;

import core.models.GenericModel;
import core.models.LogicalModel;

/**
 * This class will store the models involved in the arithmetic operation
 */

public class ArithmeticResultModel extends LogicalModel {
    private GenericModel leftModel, rightModel;

    public GenericModel getLeftModel() {
        return leftModel;
    }

    public void setLeftModel(GenericModel leftModel) {
        this.leftModel = leftModel;
    }

    public GenericModel getRightModel() {
        return rightModel;
    }

    public void setRightModel(GenericModel rightModel) {
        this.rightModel = rightModel;
    }
}
