package parser.strategy.LLPP;

import grammar.Grammar;
import helper.LexicalHelper;
import helper.SyntaxHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import parser.strategy.LLPP.data.LLPPData;
import parser.strategy.LLPP.exceptions.LLPPException;
import parser.strategy.LLPP.structure.table.LLPPTable;
import parser.strategy.LLPP.structure.table.cell.LLPPAbstractTableCell;
import parser.strategy.LLPP.structure.table.cell.LLPPErrorCell;
import parser.strategy.LLPP.structure.table.cell.LLPPRuleCell;
import parser.strategy.ParseStrategy;
import token.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 * Lef to Right - Leftmost Predictive parser
 */

public class LLPP extends ParseStrategy {

    // Logger
    private Logger l = LogManager.getFormatterLogger(getClass());

    // Components
    private LLPPTable llppTable;

    // Root of parser
    private NonTerminalToken treeRoot;

    // Data storage
    private LLPPData llppData;

    public LLPP(Grammar grammar) {
        super(grammar);

        // Check if the grammar is correct
        validate();

        // Check if the grammar can be optimized
        betterCompiler();

        // Init components
        llppTable = new LLPPTable(grammar);

        // Print table
        l.info("Printing Predictive parser table:\n" + llppTable);
    }

    @Override
    public boolean parse(AbstractToken firstLexicalTokens) {

        // Log
        l.info("Start parsing input ...");

        // Prepare data
        Stack<AbstractSyntaxToken> stackSyntax = new Stack<>();

        // True if error detected
        boolean error = false;

        // True if the parser is in panic mode
        boolean stable = true;

        // Push EOS
        stackSyntax.push(SyntaxTokenFactory.createEndOfStackToken());

        // Create start token
        treeRoot = SyntaxTokenFactory.createNonTerminalToken(grammar.getStart());

        // Int phases
        int phases = 1;

        // If listener is set then update the number of parse phases
        if(parseStrategyListener != null) {

            // Required to reset data
            parseStrategyListener.init();
            phases = parseStrategyListener.getParsePhase();

            // If no phase or phase 0 has been specified
            if(phases == 0) {
                l.error("Exiting parser because no actions has been specified for phase 1");
                return false;
            }
        }

        l.info("Total parses: " + phases);

        // Prepare lexical token
        AbstractToken lexicalToken = null;

        // Run multiple times
        for(int phase = 1; phase <= phases; phase++) {

            // Push S
            stackSyntax.push(treeRoot);

            if(phase == 1) {
                // Prepare storage
                llppData = new LLPPData();

                // Add start production LHS
                llppData.getDerivationList().add(stackSyntax.peek());

                // New data entry
                llppData.addFineEntry(stackSyntax, lexicalToken);
            }

            // Reset input token
            lexicalToken = firstLexicalTokens;

            // While top is not EOS
            while(!(stackSyntax.peek() instanceof EndOfStackToken)) {

                // Store top
                AbstractSyntaxToken syntaxToken = stackSyntax.peek();

                if(syntaxToken instanceof LLActionToken) {

                    // Call action
                    if(parseStrategyListener != null) {
                        LLActionToken actionToken = (LLActionToken) syntaxToken;
                        actionToken.setStable(stable);
                        actionToken.setLexicalToken(lexicalToken.getPrevious());
                        parseStrategyListener.actionCall(actionToken, phase);
                    }

                    // Pop action token
                    stackSyntax.pop();

                } else if(syntaxToken instanceof TerminalToken) {

                    // If match
                    if(syntaxToken.getValue().equals(lexicalToken.getToken())) {

                        if(phase == 1) {
                            // New data entry
                            llppData.addFineEntry(stackSyntax, lexicalToken);
                        }

                        // Set terminal value
                        ((TerminalToken) syntaxToken).setLexicalToken(lexicalToken);

                        // Pop terminal from data
                        stackSyntax.pop();

                        // Update inputToken
                        lexicalToken = lexicalToken.getNext();

                        // Update stability
                        stable = true;

                    } else {

                        if(phase == 1) {
                            // New data entry
                            llppData.addErrorEntry(stackSyntax, lexicalToken, SyntaxHelper.tokenDefaultMessageLL(lexicalToken));

                            // Grammar can be enhacned
                            l.warn("Compiler couldn't make the best decision because it expected a non-terminal and a terminal instead of two terminals");
                        }

                        // Pop a syntax token
                        // Note: This decision is arbitrary because the behavior is not provided
                        stackSyntax.pop();

                        // Error found
                        error = true;

                        // Update stability
                        stable = false;
                    }

                } else if(syntaxToken instanceof NonTerminalToken) {

                    // Get cell
                    LLPPAbstractTableCell cell = llppTable.getCell(syntaxToken, lexicalToken);

                    // Cast to non-terminal
                    NonTerminalToken nonTerminalToken = (NonTerminalToken) syntaxToken;

                    // If a rule cell
                    if(cell instanceof LLPPRuleCell) {

                        // Store production
                        List<AbstractSyntaxToken> ruleCopy = ((LLPPRuleCell) cell).getRuleCopy();

                        if(phase == 1) {
                            // New data entry
                            llppData.addFineEntry(stackSyntax, lexicalToken, nonTerminalToken, ruleCopy);
                        }

                        // Pop syntax token
                        stackSyntax.pop();

                        // Inverse RHS multiple push. Don't push EPSILON
                        for(int i = ruleCopy.size()-1; i >= 0; --i) {
                            if(! (ruleCopy.get(i) instanceof EpsilonToken)) {
                                stackSyntax.push(ruleCopy.get(i));
                            }
                            nonTerminalToken.getChildren().add(0, ruleCopy.get(i));
                        }

                        // Update stability
                        stable = true;

                    } else if(cell instanceof LLPPErrorCell) {

                        // Cast to error cell
                        LLPPErrorCell errorCell = (LLPPErrorCell) cell;

                        if(phase == 1) {
                            // New data entry
                            llppData.addErrorEntry(stackSyntax, lexicalToken, LexicalHelper.messageReplace(errorCell.getMessage(), lexicalToken));
                        }

                        // Check decision
                        switch (errorCell.getDecision()) {
                            case LLPPErrorCell.POP:

                                // Pop the data
                                stackSyntax.pop();

                                break;
                            case LLPPErrorCell.SCAN:

                                // Scan next input token
                                lexicalToken = lexicalToken.getNext();

                                break;
                            default:
                                l.error("Undefined behavior for the error cell: non-terminal: " + syntaxToken.getValue() + ", terminal: " + lexicalToken.getToken());
                                break;
                        }

                        // Error found
                        error = true;

                        // Update stability
                        stable = false;
                    }
                }
            }
        }

        // If lexical tokens are not completely consumed
        if(!(lexicalToken instanceof EndOfFileToken)){

            // New data entry
            llppData.addErrorEntry(stackSyntax, lexicalToken, SyntaxHelper.tokenDefaultMessageLL(lexicalToken));

            // Error found
            error = true;
        }

        // If there was an error
        if(error){
            return false;
        }

        // New data entry
        llppData.addFineEntry(stackSyntax, lexicalToken);

        // No errors
        return true;
    }

    /**
     * Check if the grammar is LL
     * A -> B | C
     * Cond 1: No left recursion
     * Cond 2: First(B) ^ First(C) = {}
     * Cond 3: If First(A) contains "", then First(A) & Follow(A) = {}
     */
    private void validate() {

        // Cond 1
        for(String LHS : grammar.getProductions().keySet()) {
            AbstractSyntaxToken leftRecusiveToken = getLeftRecursion(SyntaxTokenFactory.createNonTerminalToken(LHS), new HashSet<>());
            if (leftRecusiveToken != null) {
                String message = "Left hand side: " + leftRecusiveToken.getValue() + " has a left recursion";
                l.error(message);
                throw new LLPPException(message);
            }
        }

        // Loop on non-terminal
        for(String nonTerminal : grammar.getProductions().keySet()) {

            // Cond 2
            Set<String> uniqueFirstSetValues = new HashSet<>();
            for(List<AbstractSyntaxToken> rule : grammar.getProductions().get(nonTerminal)) {
                for(String token : grammar.getRuleFirstSetMap().get(rule)) {
                    if(uniqueFirstSetValues.contains(token)) {
                        String message = "The first set of the rules of the non-terminal: " + nonTerminal + " intersect at " + token;
                        l.error(message);
                        throw new LLPPException(message);
                    } else {
                        uniqueFirstSetValues.add(token);
                    }
                }
            }

            // Cond 3
            if(uniqueFirstSetValues.contains(SyntaxHelper.EPSILON) && (uniqueFirstSetValues.retainAll(grammar.getFollowSetOfNonTerminal(nonTerminal)) & !uniqueFirstSetValues.isEmpty())) {
                String message = "The first and follow sets of the non-terminal: " + nonTerminal + " intersect at " + uniqueFirstSetValues;
                l.error(message);
                throw new LLPPException(message);
            }
        }
    }

    /**
     * Checks if the grammar has left recursion
     * @param token
     * @param visitedNonTerminals
     * @return token if left recursion detected. Otherwise return null
     */
    private AbstractSyntaxToken getLeftRecursion(AbstractSyntaxToken token, Set<String> visitedNonTerminals) {

        // If visited more than once
        if(visitedNonTerminals.contains(token.getValue())) {
            return token;
        }

        // Mark non terminal as visited
        visitedNonTerminals.add(token.getValue());

        for(List<AbstractSyntaxToken> production : grammar.getProductions().get(token.getValue())) {
            for(AbstractSyntaxToken syntaxToken : production) {
                if(syntaxToken instanceof NonTerminalToken) {

                    // Check recursively for left-recursion
                    AbstractSyntaxToken result = getLeftRecursion(syntaxToken, visitedNonTerminals);
                    if(result != null) {
                        return result;
                    }

                    // Stop checking when the first set of non-terminal does not contain epsilon
                    if(!grammar.getFirstSetOf(syntaxToken).contains(SyntaxHelper.EPSILON)) {
                        break;
                    }
                } else if(syntaxToken instanceof TerminalToken) {
                    // Stop checking if token is a terminal
                    break;
                }
            }
        }

        // No left recursion found
        return null;
    }

    /**
     * Check if the grammar can be enhanced for better decisions by the compiler
     * Cond 1: Don't use terminals after the first token of a rule
     */
    private void betterCompiler() {

        // Loop on non-terminal
        for (String nonTerminal : grammar.getProductions().keySet()) {

            // Loop on rules
            for (List<AbstractSyntaxToken> rule : grammar.getProductions().get(nonTerminal)) {

                // Loop on syntax tokens
                boolean foundToken = false;
                for(AbstractSyntaxToken syntaxToken : rule) {
                    if (syntaxToken instanceof TerminalToken && foundToken) {
                        l.warn("The compiler will make better decisions if you replace the terminal " + syntaxToken.getOriginalValue() + " by a non-terminal in the following rule:\n" + nonTerminal + " => " + StringUtils.join(rule, " "));
                        break;
                    }

                    if(syntaxToken instanceof NonTerminalToken || syntaxToken instanceof TerminalToken) {
                        foundToken = true;
                    }
                }
            }
        }
    }

    /**
     * Get LL PP Data
     * Includes parsing steps and error messages
     * @return data
     */
    public LLPPData getLlppData() {
        return llppData;
    }

    @Override
    public NonTerminalToken getDerivationRoot() {
        return treeRoot;
    }

    /**
     * Get PP Table
     * @return PP table
     */
    public LLPPTable getLlppTable() {
        return llppTable;
    }
}
