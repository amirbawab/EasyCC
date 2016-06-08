package entries;

import core.structure.symbol.table.entry.SymbolTableParentEntry;

public class ClassEntry extends SymbolTableParentEntry {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
