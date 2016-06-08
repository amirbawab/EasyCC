package core.structure.symbol.table.entry;

import core.structure.symbol.table.SymbolTable;

/**
 * A Parent entry points to another core.structure.symbol core.structure.symbol.table
 */

public class SymbolTableParentEntry extends SymbolTableGenericEntry {
    private SymbolTable symbolTable;

    public SymbolTableParentEntry() {
        symbolTable = new SymbolTable();
    }

    /**
     * Get the core.structure.symbol core.structure.symbol.table this entry points to
     * @return core.structure.symbol core.structure.symbol.table
     */
    final public SymbolTable getSymbolTable() {
        return symbolTable;
    }
}
