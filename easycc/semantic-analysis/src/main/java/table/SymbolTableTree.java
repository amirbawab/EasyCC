package table;

public class SymbolTableTree {

    private SymbolTable absoluteRootSymbolTable;
    private SymbolTable relativeRootSymbolTable;

    public SymbolTableTree() {
        absoluteRootSymbolTable = new SymbolTable();
        relativeRootSymbolTable = absoluteRootSymbolTable;
    }

    /**
     * Get the absolute root symbol table
     * @return root symbol table
     */
    public SymbolTable getAbsoluteRootSymbolTable() {
        return absoluteRootSymbolTable;
    }

    /**
     * Get the relative root symbol table
     * If not set, this method will return the absolute symbol table
     * @return root symbol table which can change during parsing
     */
    public SymbolTable getRelativeRootSymbolTable() {
        return relativeRootSymbolTable;
    }

    /**
     * Set the relative root symbol table
     * @param relativeRootSymbolTable
     */
    public void setRelativeRootSymbolTable(SymbolTable relativeRootSymbolTable) {
        this.relativeRootSymbolTable = relativeRootSymbolTable;
    }
}