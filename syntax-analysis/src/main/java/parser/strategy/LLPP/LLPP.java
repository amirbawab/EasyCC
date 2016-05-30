package parser.strategy.LLPP;

import grammar.Grammar;
import helper.LexicalHelper;
import helper.SyntaxHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import parser.strategy.LLPP.cell.LLPPAbstractTableCell;
import parser.strategy.LLPP.cell.LLPPErrorCell;
import parser.strategy.LLPP.cell.LLPPRuleCell;
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

    // Long scan process time
    private long syntaxAnalysisProcessTime;

    // Root of parser
    private NonTerminalToken treeRoot;

    public LLPP(Grammar grammar) {
        super(grammar);
        llppTable = new LLPPTable(grammar);

        // Check if the grammar is correct
        validate();

        // Check if the grammar can be optimized
        betterCompiler();

        // Print table
        l.info("Printing Predictive parser table:\n" + llppTable);
    }

    @Override
    public boolean parse(List<AbstractToken> lexicalTokens) {

        // Update time
        syntaxAnalysisProcessTime = System.currentTimeMillis();

        // Log
        l.info("Start parsing input ...");

        // Prepare stack
        Stack<AbstractSyntaxToken> stackSyntax = new Stack<>();

        // True if error detected
        boolean error = false;

        // Push EOS
        stackSyntax.push(SyntaxTokenFactory.createEndOfStackToken());

        // Create start token
        treeRoot = SyntaxTokenFactory.createNonTerminalToken(grammar.getStart());

        // Int phases
        int phases = 1;

        // Prepare lexical token
        AbstractToken lexicalToken = null;

        // Run multiple times
        for(int phase = 1; phase <= phases; phase++) {

            // Push S
            stackSyntax.push(treeRoot);

            // Reset input token index
            int lexicalTokensIndex = 0;

            // Reset input token
            lexicalToken = lexicalTokens.get(lexicalTokensIndex);

            // While top is not EOS
            while(!(stackSyntax.peek() instanceof EndOfStackToken)) {

                // Store top
                AbstractSyntaxToken syntaxToken = stackSyntax.peek();

                if(syntaxToken instanceof ActionToken) {

                    // Cast into action token
                    ActionToken token = (ActionToken) stackSyntax.pop();

                } else if(syntaxToken instanceof TerminalToken) {

                    // If match
                    if(syntaxToken.getValue().equals(lexicalToken.getToken())) {

                        // Set terminal value
                        ((TerminalToken) syntaxToken).setLexicalToken(lexicalToken);

                        // Pop terminal from stack
                        stackSyntax.pop();

                        // Update inputToken
                        lexicalToken = lexicalTokens.get(++lexicalTokensIndex);

                    } else {

                        // Prepare message
                        String errorMessage = SyntaxHelper.tokenDefaultMessage(lexicalToken);

                        // Print message
                        l.error(errorMessage);

                        // Grammar can be enhacned
                        l.warn("Compiler couldn't make the best decision because it expected a non-terminal and a terminal instead of two terminals");

                        // Pop a syntax token
                        // Note: This decision is arbitrary because the behavior is not provided
                        stackSyntax.pop();

                        // Error found
                        error = true;
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

                        // Pop syntax token
                        stackSyntax.pop();

                        // Inverse RHS multiple push. Don't push EPSILON
                        for(int i = ruleCopy.size()-1; i >= 0; --i) {
                            if(! (ruleCopy.get(i) instanceof EpsilonToken)) {
                                stackSyntax.push(ruleCopy.get(i));
                            }
                            nonTerminalToken.addChild(ruleCopy.get(i));
                        }

                    } else if(cell instanceof LLPPErrorCell) {

                        // Cast to error cell
                        LLPPErrorCell errorCell = (LLPPErrorCell) cell;

                        // Prepare message
                        String errorMessage = SyntaxHelper.tokenMessage(nonTerminalToken, lexicalToken);

                        // Print message
                        l.error(errorMessage);

                        // Check decision
                        switch (errorCell.getDecision()) {
                            case LLPPErrorCell.POP:

                                // Pop the stack
                                stackSyntax.pop();

                                break;
                            case LLPPErrorCell.SCAN:

                                // Scan next input token
                                lexicalToken = lexicalTokens.get(++lexicalTokensIndex);

                                break;
                            default:
                                l.error("Undefined behavior for the error cell: non-terminal: " + syntaxToken.getValue() + ", terminal: " + lexicalToken.getToken());
                                break;
                        }

                        // Error found
                        error = true;
                    }
                }
            }
        }

        // If lexical tokens are not completely consumed
        if(!(lexicalToken instanceof EndOfFileToken)){
            error = true;
        }

        // Update time
        syntaxAnalysisProcessTime = System.currentTimeMillis() - syntaxAnalysisProcessTime;

        // If there was an error
        if(error){
            return false;
        }

        // No errors
        return true;
    }

    /**
     * Check if the grammar is LL
     * A -> B | C
     * Cond 1: First(B) ^ First(C) = {}
     * Cond 2: If First(A) contains "", then First(A) & Follow(A) = {}
     */
    public void validate() {

        // Loop on non-terminal
        for(String nonTerminal : grammar.getProductions().keySet()) {

            // Cond 1
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

            // Cond 2
            if(uniqueFirstSetValues.contains(SyntaxHelper.EPSILON) && (uniqueFirstSetValues.retainAll(grammar.getFollowSetOfNonTerminal(nonTerminal)) & !uniqueFirstSetValues.isEmpty())) {
                String message = "The first and follow sets of the non-terminal: " + nonTerminal + " intersect at " + uniqueFirstSetValues;
                l.error(message);
                throw new LLPPException(message);
            }
        }
    }

    /**
     * Check if the grammar can be enhanced for better decisions by the compiler
     * Cond 1: Don't use terminals after the first token of a rule
     */
    public void betterCompiler() {

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
     * Get process time
     * @return process time in ms
     */
    public long getSyntaxAnalysisProcessTime() {
        return syntaxAnalysisProcessTime;
    }

    /**
     * Get parse tree root
     * @return parse tree root
     */
    public NonTerminalToken getTreeRoot() {
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
