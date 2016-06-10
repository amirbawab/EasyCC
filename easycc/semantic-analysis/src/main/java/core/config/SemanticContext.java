package core.config;

import core.models.GenericModel;
import core.structure.symbol.table.entry.SymbolTableGenericEntry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * This class is responsible for passing the information from the syntax analyzer
 * to the semantic actions
 */

public class SemanticContext {
    private GenericModel model;
    private SymbolTableGenericEntry entry;
    private SemanticContextListener semanticContextListener;

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

    public void setSemanticContextListener(SemanticContextListener semanticContextListener) {
        this.semanticContextListener = semanticContextListener;
    }

    public void generateCode() {
        semanticContextListener.generateCode(this);
    }
}
