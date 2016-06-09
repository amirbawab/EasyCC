package core.structure.symbol.table;

import core.structure.symbol.table.entry.SymbolTableGenericEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Symbol core.structure.symbol.table contains a list of entries
 */

public class SymbolTable {

    private SymbolTableGenericEntry originEntry;
    private List<SymbolTableGenericEntry> entryList;

    public SymbolTable(SymbolTableGenericEntry originEntry) {
        this.originEntry = originEntry;
        entryList = new ArrayList<>();
    }

    /**
     * Get the list of entries
     * Note: Do no use this method to add entries
     * @see #addEntry(SymbolTableGenericEntry)
     * @return list of entries
     */
    public List<SymbolTableGenericEntry> getEntryList() {
        return entryList;
    }

    /**
     * Add entry to the symbol table
     * @param entry
     */
    public void addEntry(SymbolTableGenericEntry entry) {
        entry.setOriginSymbolTable(this);
        entryList.add(entry);
    }

    /**
     * Get origin entry
     * @return origin entry
     */
    public SymbolTableGenericEntry getOriginEntry() {
        return originEntry;
    }

    /**
     * Get origin entry name
     * @return origin entry name
     */
    public String getName() {
        return originEntry.getName();
    }

    /**
     * Get symbol table path
     * @return symbol table path
     */
    public String getPath() {
        if(originEntry.getOriginSymbolTable() == null) {
            return getName();
        }
        return originEntry.getOriginSymbolTable().getPath() + " > " + getName();
    }

    /**
     * Prettify data
     * @return prettify data
     */
    public String[][] prettifyData() {
        String[][] data = new String[entryList.size()][];
        for(int i=0; i < data.length; i++) {
            data[i] = entryList.get(i).prettifyData();
        }
        return data;
    }
}
