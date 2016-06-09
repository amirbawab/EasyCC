package creator;

import core.annotations.SymbolTableEntry;
import core.structure.symbol.table.entry.SymbolTableGenericEntry;
import entries.ClassEntry;
import enums.SemanticActionEnum;

/**
 * Factory class to create symbol table entries
 * All methods in this class should be static
 */

public class EntriesFactory {

    @SymbolTableEntry(SemanticActionEnum.CREATE_CLASS_TABLE_AND_ENTRY)
    public static SymbolTableGenericEntry createClassEntry() {
        return new ClassEntry();
    }
}
