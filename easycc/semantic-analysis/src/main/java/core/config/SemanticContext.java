package core.config;

import core.models.GenericModel;
import core.structure.symbol.table.entry.SymbolTableGenericEntry;

/**
 * This class is responsible for passing the information from the syntax analyzer
 * to the semantic actions
 */

public class SemanticContext {
    private GenericModel model;
    private SymbolTableGenericEntry entry;

    public GenericModel getModel() {
        return model;
    }

    public SymbolTableGenericEntry getEntry() {
        return entry;
    }

    public void setModel(GenericModel model) {
        this.model = model;
    }

    public void setEntry(SymbolTableGenericEntry entry) {
        this.entry = entry;
    }
}
