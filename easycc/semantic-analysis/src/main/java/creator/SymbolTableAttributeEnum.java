package creator;

/**
 * List of symbol table attributes
 * This enum is useful only for developer to visualize the symbol tables
 */

public enum SymbolTableAttributeEnum {

    // Add your attributes here
    NAME("Name", "No name")
    ;

    // Store name and default value for an attribute
    private String name;
    private String defaultValue;
    SymbolTableAttributeEnum(String name, String defaultValue) {
        this.name = name;
        this.defaultValue = defaultValue;
    }
    SymbolTableAttributeEnum(String name) {
        this(name, "");
    }

    public String getName() {
        return name;
    }

    public String getDefaultValue() {
        return defaultValue;
    }
}
