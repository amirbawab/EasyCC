package models;

import core.models.DataModel;

public class IntegerModel extends DataModel {

    public int getInteger() {
        return Integer.parseInt(lexicalToken.getValue());
    }
}
