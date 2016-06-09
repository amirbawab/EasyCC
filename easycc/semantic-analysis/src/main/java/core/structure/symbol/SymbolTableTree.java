package core.structure.symbol;

import com.bethecoder.ascii_table.ASCIITable;
import core.structure.symbol.table.SymbolTable;
import core.structure.symbol.table.entry.SymbolTableGenericEntry;
import core.structure.symbol.table.entry.SymbolTableParentEntry;
import creator.SymbolTableAttributeEnum;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SymbolTableTree {

    private SymbolTableParentEntry rootEntry;
    private SymbolTable absoluteRootSymbolTable;
    private SymbolTable relativeRootSymbolTable;

    public SymbolTableTree() {
        rootEntry = new SymbolTableParentEntry();
        rootEntry.setName("Global");
        absoluteRootSymbolTable = new SymbolTable(rootEntry);
        relativeRootSymbolTable = absoluteRootSymbolTable;
    }

    /**
     * Get the list of all symbol tables using Breadth-First-Traversal
     * @return list of symbol tables
     */
    public List<SymbolTable> getSymbolTables() {

        // Prepare list of symbol tables
        List<SymbolTable> tables = new ArrayList<>();

        // Start BFT
        Queue<SymbolTable> tableQueue = new LinkedList<>();
        tableQueue.offer(absoluteRootSymbolTable);
        while(!tableQueue.isEmpty()) {
            SymbolTable symbolTable = tableQueue.poll();
            tables.add(symbolTable);
            for(SymbolTableGenericEntry genericEntry : symbolTable.getEntryList()) {
                if(genericEntry instanceof SymbolTableParentEntry) {
                    tableQueue.offer(((SymbolTableParentEntry) genericEntry).getSymbolTable());
                }
            }
        }
        return tables;
    }

    /**
     * Get the absolute root symbol table
     * @return root symbol table
     */
    public SymbolTable getAbsoluteRootSymbolTable() {
        return absoluteRootSymbolTable;
    }

    /**
     * Get the relative root symbol table
     * @return root symbol table which can change during parsing
     */
    public SymbolTable getRelativeRootSymbolTable() {
        return relativeRootSymbolTable;
    }

    /**
     * Set the relative root symbol table
     * @param relativeRootSymbolTable
     */
    public void setRelativeRootSymbolTable(SymbolTable relativeRootSymbolTable) {
        this.relativeRootSymbolTable = relativeRootSymbolTable;
    }

    /**
     * Prettify symbol table header
     * @return symbol table header
     */
    public String[] prettifyHeader() {
        SymbolTableAttributeEnum[] values = SymbolTableAttributeEnum.values();
        String[] header = new String[values.length];
        for(int i=0; i < header.length; i++) {
            header[i] = values[i].getName();
        }
        return header;
    }

    /**
     * Prettify all tables
     * @return all tables
     */
    public String toString() {
        String output = "";
        List<SymbolTable> tables = getSymbolTables();
        String[] header = prettifyHeader();
        for(int i=0; i < tables.size(); i++) {
            SymbolTable table = tables.get(i);
            String[][] tableData = table.prettifyData();
            output += table.getPath() + "\n";
            if(tableData.length == 0) {
                output += "No entries found\n";
            } else {
                output += ASCIITable.getInstance().getTable(header, tableData) + "\n";
            }
        }
        return output;
    }
}