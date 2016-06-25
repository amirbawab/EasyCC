package parser.strategy.SLR;

import grammar.Grammar;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import parser.strategy.ParseStrategy;
import parser.strategy.SLR.exceptions.SLRException;
import parser.strategy.SLR.structure.machine.SLRStateMachine;
import parser.strategy.SLR.structure.machine.node.LRItemNode;
import parser.strategy.SLR.structure.parse.stack.LRAbstrackStackEntry;
import parser.strategy.SLR.structure.parse.stack.LRLexicalEntry;
import parser.strategy.SLR.structure.parse.stack.LRSyntaxEntry;
import parser.strategy.SLR.structure.table.LRTable;
import parser.strategy.SLR.structure.table.cell.LRAbstractTableCell;
import parser.strategy.SLR.structure.table.cell.LRErrorCell;
import parser.strategy.SLR.structure.table.cell.LRReduceCell;
import parser.strategy.SLR.structure.table.cell.LRShiftCell;
import token.*;

import java.util.List;
import java.util.Stack;

public class SLR extends ParseStrategy {

    // Logger
    private Logger l = LogManager.getFormatterLogger(getClass());

    // Components
    private SLRStateMachine stateMachine;
    private LRTable table;

    // Root of parser
    private NonTerminalToken treeRoot;

    public SLR(Grammar grammar) {
        super(grammar);

        // Check if the grammar is correct
        validate();

        // Initial components
        stateMachine = new SLRStateMachine(grammar);
        table = new LRTable(stateMachine);

        // Print table
        l.info("Printing SLR parser table:\n" + table);
    }

    /**
     * Get LR state machine
     * @return LR state machine
     */
    public SLRStateMachine getStateMachine() {
        return stateMachine;
    }

    @Override
    public boolean parse(AbstractToken firstLexicalTokens) {

        // Log
        l.info("Start parsing input ...");

        // Prepare stack
        Stack<LRAbstrackStackEntry> parserStack = new Stack<>();

        // True if error detected
        boolean error = false;

        // True if the parser is in panic mode
        boolean stable = true;

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

            // Push initial entry
            LRSyntaxEntry initialEntry = new LRSyntaxEntry();
            initialEntry.setNode(stateMachine.getNodes().get(0));
            initialEntry.setSyntaxToken(SyntaxTokenFactory.createEndOfStackToken());
            parserStack.push(initialEntry);

            // Reset input token
            lexicalToken = firstLexicalTokens;

            while (!parserStack.isEmpty()) {

                // Get the top entry
                LRItemNode topEntry = parserStack.peek().getNode();

                // Get action cell
                LRAbstractTableCell actionCell = table.getActionCell(topEntry.getId(), lexicalToken.getToken());

                if(actionCell instanceof LRShiftCell) {
                    LRShiftCell shiftCell = (LRShiftCell) actionCell;
                    LRLexicalEntry lexicalEntry = new LRLexicalEntry();
                    lexicalEntry.setLexicalToken(lexicalToken);
                    lexicalEntry.setNode(shiftCell.getNode());
                    parserStack.push(lexicalEntry);
                    lexicalToken = lexicalToken.getNext();

                } else if(actionCell instanceof LRReduceCell) {
                    LRReduceCell reduceCell = (LRReduceCell) actionCell;

                    // Create parent token
                    NonTerminalToken parentToken = SyntaxTokenFactory.createNonTerminalToken(reduceCell.getItem().getLHS());

                    // Get rule
                    List<AbstractSyntaxToken> rule = reduceCell.getItem().getRuleCopy();
                    for(int i=0; i < rule.size(); i++) {
                        if(rule.get(i) instanceof NonTerminalToken) {
                            parserStack.pop();
                            parentToken.addChild(((LRSyntaxEntry) parserStack.pop()).getSyntaxToken());

                        } else if(rule.get(i) instanceof TerminalToken) {
                            parserStack.pop();
                            ((TerminalToken) rule.get(i)).setLexicalToken(((LRLexicalEntry) parserStack.pop()).getLexicalToken());
                            parentToken.addChild(rule.get(i));

                        } else if(rule.get(i) instanceof EpsilonToken) {
                            parentToken.addChild(rule.get(i));
                        }
                    }

                    // Get the top entry
                    topEntry = parserStack.peek().getNode();

                    // Check LHS is the initial production
                    if(parentToken.getValue().equals(grammar.getStart())) {
                        parserStack.pop();
                        treeRoot = parentToken;

                    } else {
                        // Push LHS
                        LRSyntaxEntry syntaxEntry = new LRSyntaxEntry();
                        syntaxEntry.setSyntaxToken(parentToken);
                        parserStack.push(syntaxEntry);

                        // Push go to
                        int goToNode = table.getGoToCell(topEntry.getId(), parentToken.getValue());
                        if (goToNode == LRTable.GO_TO_EMPTY) {
                            String message = "GOTO[" + topEntry.getId() + "][" + parentToken.getValue() + "] result was not found. Please report this problem.";
                            l.fatal(message);
                            throw new RuntimeException(message);
                        }
                        syntaxEntry.setNode(stateMachine.getNodes().get(goToNode));
                        parserStack.push(syntaxEntry);
                    }
                } else if(actionCell instanceof LRErrorCell) {

                    LRErrorCell errorCell = (LRErrorCell) actionCell;

                    switch (errorCell.getDecision()) {
                        case POP:
                            parserStack.pop();
                            break;
                        case SCAN:
                            lexicalToken = lexicalToken.getNext();
                            break;
                        case PUSH:
                            LRSyntaxEntry syntaxEntry = new LRSyntaxEntry();
                            syntaxEntry.setSyntaxToken(SyntaxTokenFactory.createNonTerminalToken(errorCell.getNonTerminal()));
                            syntaxEntry.setNode(errorCell.getItemNode());
                            parserStack.push(syntaxEntry);
                    }
                    error = true;
                }
            }
        }

        return error;
    }

    /**
     * Check if the grammar is LR
     * Cond 1: Start non-terminal should have only one production of the form A -> B
     * Cond 2: No other production should call the start non-terminal
     * TODO Check if action tokens should be allowed in initial production [Cond 1]
     */
    public void validate() {

        // Cond 1
        List<List<AbstractSyntaxToken>> startProductions = grammar.getProductions().get(grammar.getStart());
        if(startProductions.size() != 1 || startProductions.get(0).size() != 1 || !(startProductions.get(0).get(0) instanceof NonTerminalToken)) {
            String message = "The initial production should be of the form A -> B";
            l.error(message);
            throw new SLRException(message);
        }

        // Cond 2
        for(List<List<AbstractSyntaxToken>> productionList : grammar.getProductions().values()) {
            for(List<AbstractSyntaxToken> production : productionList) {
                for(AbstractSyntaxToken syntaxToken : production) {
                    if(syntaxToken instanceof NonTerminalToken && syntaxToken.getValue().equals(grammar.getStart())) {
                        String message = "No production should include the starting non-terminal: " + grammar.getStart();
                        l.error(message);
                        throw new SLRException(message);
                    }
                }
            }
        }
    }

    @Override
    public NonTerminalToken getDerivationRoot() {
        return null;
    }
}
