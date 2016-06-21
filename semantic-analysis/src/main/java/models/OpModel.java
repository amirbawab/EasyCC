package models;

import core.models.GenericModel;
import core.models.LogicalModel;

public class OpModel extends LogicalModel {
    private GenericModel leftInt, rightInt, symbol;

    public GenericModel getLeftInt() {
        return leftInt;
    }

    public void setLeftInt(GenericModel leftInt) {
        this.leftInt = leftInt;
    }

    public GenericModel getRightInt() {
        return rightInt;
    }

    public void setRightInt(GenericModel rightInt) {
        this.rightInt = rightInt;
    }

    public GenericModel getSymbolModel() {
        return symbol;
    }

    public void setSymbol(GenericModel symbol) {
        this.symbol = symbol;
    }
}
