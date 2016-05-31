package parser.strategy.LLPP.data;

import com.bethecoder.ascii_table.ASCIITable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import token.AbstractSyntaxToken;
import token.AbstractToken;
import token.NonTerminalToken;
import token.TerminalToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * List of data entries.
 */

public class LLPPData {

    // Logger
    private Logger l = LogManager.getFormatterLogger(getClass());

    private List<LLPPDataEntry> entryList;
    private ArrayList<AbstractSyntaxToken> derivationList;
    private List<String> errorMessages;

    public LLPPData() {
        entryList = new ArrayList<>();
        derivationList = new ArrayList<>();
        errorMessages = new ArrayList<>();
    }

    /**
     * Add fine entry
     * @param stackSyntax
     * @param lexicalToken
     * @param productionLHS
     * @param productionRHS
     */
    public void addFineEntry(Stack<AbstractSyntaxToken> stackSyntax, AbstractToken lexicalToken, NonTerminalToken productionLHS, List<AbstractSyntaxToken> productionRHS) {

        // New derivation list
        List<AbstractSyntaxToken> derivationClone = null;

        // Replace derivation production
        if(productionLHS != null) {
            for (int i = 0; i < derivationList.size(); i++) {
                if (derivationList.get(i) instanceof NonTerminalToken && derivationList.get(i) == productionLHS) {
                    derivationList.remove(i);

                    // Add only non-terminals and terminals
                    for(int j=productionRHS.size()-1; j >= 0; --j) {
                        AbstractSyntaxToken syntaxToken = productionRHS.get(j);
                        if(syntaxToken instanceof NonTerminalToken || syntaxToken instanceof TerminalToken) {
                            derivationClone.add(i, syntaxToken);
                        }
                    }
                    break;
                }
            }

            // Copy derivation list
            derivationClone = new ArrayList<>(derivationList);
        }

        // Clone stack
        List<AbstractSyntaxToken> stackList = new ArrayList<>(stackSyntax);

        // Create entry
        LLPPDataFineEntry fineEntry = new LLPPDataFineEntry(entryList.size()+1, stackList, lexicalToken, productionLHS, productionRHS, derivationClone);
        l.debug(fineEntry);
        entryList.add(fineEntry);
    }

    /**
     * Add fine entry
     * @param stackSyntax
     * @param lexicalToken
     */
    public void addFineEntry(Stack<AbstractSyntaxToken> stackSyntax, AbstractToken lexicalToken) {
        addFineEntry(stackSyntax, lexicalToken, null, null);
    }

    /**
     * Add error entry
     * @param stackSyntax
     * @param lexicalToken
     * @param message
     */
    public void addErrorEntry(Stack<AbstractSyntaxToken> stackSyntax, AbstractToken lexicalToken, String message) {

        // Clone stack
        List<AbstractSyntaxToken> stackList = new ArrayList<>(stackSyntax);

        // Create entry
        LLPPDataErrorEntry errorEntry = new LLPPDataErrorEntry(entryList.size()+1, stackList, lexicalToken, message);
        l.debug(errorEntry);
        entryList.add(errorEntry);
        errorMessages.add(message);
    }

    /**
     * Get entry list
     * @return entry list
     */
    public List<LLPPDataEntry> getEntryList() {
        return entryList;
    }

    /**
     * Get the derivation list
     * @return derivation list
     */
    public List<AbstractSyntaxToken> getDerivationList() {
        return derivationList;
    }

    /**
     * Prettify header
     * @return header
     */
    public String[] prettifyHeader() {
        return new String[] {"Step", "Stack", "Input", "Rule", "Derivation"};
    }

    /**
     * Prettify data
     * @return data
     */
    public String[][] prettifyData() {
        String[][] data = new String[entryList.size()][5];

        for(int i=0; i < data.length; i++) {
            LLPPDataEntry dataEntry = entryList.get(i);
            if(dataEntry instanceof LLPPDataFineEntry) {
                LLPPDataFineEntry dataFineEntry = (LLPPDataFineEntry) dataEntry;
                data[i][0] = dataFineEntry.getStepNumber()+"";
                data[i][1] = dataFineEntry.getStackContent();
                data[i][2] = dataFineEntry.getInputContent();
                data[i][3] = dataFineEntry.getProductionContent();
                data[i][4] = dataFineEntry.getDerivationContent();
            } else {
                LLPPDataErrorEntry dataFineEntry = (LLPPDataErrorEntry) dataEntry;
                data[i][0] = dataFineEntry.getStepNumber()+"";
                data[i][1] = dataFineEntry.getStackContent();
                data[i][2] = dataFineEntry.getInputContent();
                data[i][3] = "";
                data[i][4] = dataFineEntry.getMessage();
            }
        }
        return data;
    }

    /**
     * Get error messages
     * @return error messages
     */
    public List<String> getErrorMessages() {
        return errorMessages;
    }

    @Override
    public String toString() {
        return ASCIITable.getInstance().getTable(prettifyHeader(), prettifyData());
    }
}
