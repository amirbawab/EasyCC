package models;

import core.models.GenericModel;
import core.models.LogicalModel;

public class ResultModel extends LogicalModel {
    private GenericModel resultModel;

    public GenericModel getResultModel() {
        return resultModel;
    }

    public void setResultModel(GenericModel resultModel) {
        this.resultModel = resultModel;
    }
}
