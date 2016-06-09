package enums;

/**
 * List of symbol table attributes
 * This enum is useful only for developer to visualize the symbol tables
 */

public enum SymbolTableAttributeEnum {

    // Add your attributes here
    NAME("Name")
    ;

    // Store name and default value for an attribute
    private String name;
    private String defaultValue;
    SymbolTableAttributeEnum(String name, String defaultValue) {
        this.name = name;
        this.defaultValue = defaultValue;
    }
    SymbolTableAttributeEnum(String name) {
        this(name, "Not specified");
    }

    public String getName() {
        return name;
    }

    public String getDefaultValue() {
        return defaultValue == null ? "" : defaultValue;
    }
}
