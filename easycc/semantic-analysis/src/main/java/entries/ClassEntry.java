package entries;

import core.annotations.SymbolTableAttribute;
import core.structure.symbol.table.entry.SymbolTableParentEntry;
import enums.SymbolTableAttributeEnum;

public class ClassEntry extends SymbolTableParentEntry {

    @Override
    @SymbolTableAttribute(SymbolTableAttributeEnum.NAME)
    public String getName() {
        return super.getName();
    }
}
