package parser.strategy.SLR.data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import parser.strategy.SLR.structure.parse.stack.LRAbstractStackEntry;
import parser.strategy.SLR.structure.parse.stack.LRLexicalEntry;
import parser.strategy.SLR.structure.parse.stack.LRSyntaxEntry;
import token.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * List of data entries.
 */

public class LRData {

    // Logger
    private Logger l = LogManager.getFormatterLogger(getClass());

    private List<LRDataEntry> entryList;
    private ArrayList<LRAbstractStackEntry> derivationList;
    private List<String> errorMessages;

    public LRData() {
        entryList = new ArrayList<>();
        derivationList = new ArrayList<>();
        errorMessages = new ArrayList<>();
    }

    /**
     * Add fine entry
     * @param parseStack
     * @param lexicalToken
     * @param productionLHS
     * @param productionRHS
     */
    public void addFineEntry(Stack<LRAbstractStackEntry> parseStack, AbstractToken lexicalToken, LRSyntaxEntry productionLHS, List<LRAbstractStackEntry> productionRHS) {

        // New derivation list
        List<LRAbstractStackEntry> derivationClone = null;

        // Copy derivation list
        derivationClone = new ArrayList<>(derivationList);

        // Clone stack
        List<LRAbstractStackEntry> stackList = new ArrayList<>(parseStack);

        // Create entry
        LRDataFineEntry fineEntry = new LRDataFineEntry(entryList.size()+1, stackList, lexicalToken, productionLHS, productionRHS, derivationClone);
        l.debug(fineEntry);
        entryList.add(fineEntry);
    }

    /**
     * Add fine entry
     * @param stackSyntax
     * @param lexicalToken
     */
    public void addFineEntry(Stack<LRAbstractStackEntry> stackSyntax, AbstractToken lexicalToken) {
        addFineEntry(stackSyntax, lexicalToken, null, null);
    }

    /**
     * Add error entry
     * @param stackSyntax
     * @param lexicalToken
     * @param message
     */
    public void addErrorEntry(Stack<LRAbstractStackEntry> stackSyntax, AbstractToken lexicalToken, String message) {

        // Clone stack
        List<LRAbstractStackEntry> stackList = new ArrayList<>(stackSyntax);

        // Create entry
        LRDataErrorEntry errorEntry = new LRDataErrorEntry(entryList.size()+1, stackList, lexicalToken, message);
        l.debug(errorEntry);
        entryList.add(errorEntry);
        errorMessages.add(message);
    }

    /**
     * Get entry list
     * @return entry list
     */
    public List<LRDataEntry> getEntryList() {
        return entryList;
    }

    /**
     * Get the derivation list
     * @return derivation list
     */
    public List<LRAbstractStackEntry> getDerivationList() {
        return derivationList;
    }

    /**
     * Get error messages
     * @return error messages
     */
    public List<String> getErrorMessages() {
        return errorMessages;
    }
}
