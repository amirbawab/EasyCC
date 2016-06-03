package models;

import core.models.GenericModel;

public class IntegerModel extends GenericModel {
    public int getIntValue() {
        return Integer.parseInt(getLexicalToken().getValue());
    }
}
