package table;

import table.entry.SymbolTableGenericEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Symbol table contains a list of entries
 */

public class SymbolTable {
    private List<SymbolTableGenericEntry> entryList;

    public SymbolTable() {
        entryList = new ArrayList<>();
    }

    /**
     * Get the list of entries
     * @return list of entries
     */
    public List<SymbolTableGenericEntry> getEntryList() {
        return entryList;
    }
}
