package core.structure.symbol.table.entry;

import core.annotations.SymbolTableAttribute;
import core.structure.symbol.table.SymbolTable;
import creator.SymbolTableAttributeEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Generic core.structure.symbol core.structure.symbol.table entry from which all other entry types inherit from
 */

public abstract class SymbolTableGenericEntry {

    // Logger
    private Logger l = LogManager.getFormatterLogger(getClass());

    private SymbolTable originSymbolTable;
    private String name;

    /**
     * Get the symbol table the entry belongs to
     * @return
     */
    public SymbolTable getOriginSymbolTable() {
        return originSymbolTable;
    }

    /**
     * Set the symbol table the entry belons to
     * @param originSymbolTable
     */
    public void setOriginSymbolTable(SymbolTable originSymbolTable) {
        this.originSymbolTable = originSymbolTable;
    }

    /**
     * Get entry name
     * @return entry name
     */
    public String getName() {
        return name;
    }

    /**
     * Set entry name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Prettify entry data
     * @return array of data
     */
    public String[] prettifyData() {
        SymbolTableAttributeEnum[] enumValues = SymbolTableAttributeEnum.values();
        String[] data = new String[enumValues.length];
        for(Method method : this.getClass().getMethods()) {
            SymbolTableAttribute symbolTableAttribute = method.getAnnotation(SymbolTableAttribute.class);
            if(symbolTableAttribute != null) {
                for(int i=0; i < enumValues.length; i++) {
                    SymbolTableAttributeEnum attributeEnum = enumValues[i];
                    if(symbolTableAttribute.value() == attributeEnum) {
                        try {
                            data[i] = (String) method.invoke(this);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            l.error(e.getMessage());
                            data[i] = attributeEnum.getDefaultValue();
                        }
                        break;
                    }
                }

            }
        }
        return data;
    }
}
