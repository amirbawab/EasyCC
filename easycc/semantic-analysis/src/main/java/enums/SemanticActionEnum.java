package enums;

/**
 * Enum of the semantic actions used in the grammar
 * For example, if you have A -> B #action# C, then you should add the `action` in the enum
 */

public enum SemanticActionEnum {
    CREATE_CLASS_TABLE_AND_ENTRY("createClassTableAndEntry")
    ;

    private String name;
    SemanticActionEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
