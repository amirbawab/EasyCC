package creator;

import core.annotations.SymbolTableEntry;
import core.structure.symbol.table.entry.SymbolTableGenericEntry;
import entries.ClassEntry;

/**
 * Factory class to create symbol table entries
 * All methods in this class should be static
 */

public class EntriesFactory {

    @SymbolTableEntry("createClassTableAndEntry")
    public static SymbolTableGenericEntry createClassEntry() {
        return new ClassEntry();
    }
}
