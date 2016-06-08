package core.structure.symbol;

import core.structure.symbol.table.SymbolTable;

public class SymbolTableTree {

    private SymbolTable absoluteRootSymbolTable;
    private SymbolTable relativeRootSymbolTable;

    public SymbolTableTree() {
        absoluteRootSymbolTable = new SymbolTable();
        relativeRootSymbolTable = absoluteRootSymbolTable;
    }

    /**
     * Get the absolute root core.structure.symbol core.structure.symbol.table
     * @return root core.structure.symbol core.structure.symbol.table
     */
    public SymbolTable getAbsoluteRootSymbolTable() {
        return absoluteRootSymbolTable;
    }

    /**
     * Get the relative root core.structure.symbol core.structure.symbol.table
     * If not set, this method will return the absolute core.structure.symbol core.structure.symbol.table
     * @return root core.structure.symbol core.structure.symbol.table which can change during parsing
     */
    public SymbolTable getRelativeRootSymbolTable() {
        return relativeRootSymbolTable;
    }

    /**
     * Set the relative root core.structure.symbol core.structure.symbol.table
     * @param relativeRootSymbolTable
     */
    public void setRelativeRootSymbolTable(SymbolTable relativeRootSymbolTable) {
        this.relativeRootSymbolTable = relativeRootSymbolTable;
    }
}