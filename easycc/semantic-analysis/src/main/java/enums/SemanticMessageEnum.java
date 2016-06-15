package enums;

/**
 * Enum of the semantic actions messages defined in the message configuration file
 */

public enum SemanticMessageEnum {

    ;

    private String name;
    SemanticMessageEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
