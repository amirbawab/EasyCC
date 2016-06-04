package models;

import core.models.DataModel;

public class IntegerModel extends DataModel {
    public int getIntValue() {
        return Integer.parseInt(getLexicalToken().getValue());
    }
}
