package core.structure.symbol.table.entry;

import core.structure.symbol.table.SymbolTable;

/**
 * A Parent entry points to another symbol table
 */

public class SymbolTableParentEntry extends SymbolTableGenericEntry {
    private SymbolTable symbolTable;

    public SymbolTableParentEntry() {
        symbolTable = new SymbolTable(this);
    }

    /**
     * Get the symbol table this entry points to
     * @return symbol table
     */
    final public SymbolTable getSymbolTable() {
        return symbolTable;
    }
}
