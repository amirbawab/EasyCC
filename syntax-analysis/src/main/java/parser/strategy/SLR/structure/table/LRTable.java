package parser.strategy.SLR.structure.table;

import com.bethecoder.ascii_table.ASCIITable;
import core.config.SyntaxConfig;
import helper.SyntaxHelper;
import org.apache.commons.lang3.StringUtils;
import parser.strategy.SLR.exceptions.LRException;
import parser.strategy.SLR.structure.machine.LRStateMachine;
import parser.strategy.SLR.structure.machine.item.LRItem;
import parser.strategy.SLR.structure.machine.node.LRItemNode;
import parser.strategy.SLR.structure.machine.transition.LRTransition;
import parser.strategy.SLR.structure.table.cell.*;
import token.*;

import java.util.*;

/**
 * This class construct the table given a LR State machine
 */

public class LRTable {

    private LRStateMachine stateMachine;
    private LRAbstractTableCell action[][];
    private int goTo[][];
    private Map<String, Integer> terminalIndex;
    private Map<String, Integer> nonTerminalIndex;
    private List<LRReduceCell> reduceCellList;
    private Map<String, Integer> messageCellMap;
    public static final int GO_TO_EMPTY = -1;

    public LRTable(LRStateMachine stateMachine) {
        this.stateMachine = stateMachine;
        terminalIndex = new HashMap<>();
        nonTerminalIndex = new HashMap<>();
        reduceCellList = new ArrayList<>();
        messageCellMap = new HashMap<>();

        // Assign indices for terminals
        for(String terminal : stateMachine.getGrammar().getTerminals()) {
            terminalIndex.put(terminal, terminalIndex.size());
        }

        // Assign indices for non-terminals
        for(String nonTerminal : stateMachine.getGrammar().getNonTerminals()) {
            nonTerminalIndex.put(nonTerminal, nonTerminalIndex.size());
        }

        action = new LRAbstractTableCell[stateMachine.getNodes().size()][terminalIndex.size()];
        goTo = new int[stateMachine.getNodes().size()][nonTerminalIndex.size()];

        // Fill go to table with empty
        for (int[] aGoTo : goTo) {
            Arrays.fill(aGoTo, GO_TO_EMPTY);
        }

        // Populate action and goto tables
        buildTable();
    }

    /**
     * Populate action and goto tables
     * @throws LRException
     */
    private void buildTable() {

        // Loop on all nodes to populate the rows
        for(LRItemNode node : stateMachine.getNodes()) {

            // Loop on all items of a node
            for(LRItem item : node.getItemList()) {
                AbstractSyntaxToken tokenAfterDot = item.getTokenAfterDot();

                // If should reduce
                if(tokenAfterDot == null) {

                    // Create a reduce cell
                    LRReduceCell reduceCell;
                    if(item.getLHS().equals(stateMachine.getGrammar().getStart())) {
                        reduceCell = new LRAcceptCell();
                    } else {
                        reduceCell = new LRReduceCell();
                    }
                    reduceCell.setItem(item);
                    reduceCell.setRuleId(reduceCellList.size());
                    reduceCellList.add(reduceCell);

                    // Loop on follow set
                    for(String terminal : stateMachine.getGrammar().getFollowSetOfNonTerminal(item.getLHS())) {

                        if(action[node.getId()][terminalIndex.get(terminal)] != null) {
                            throw new LRException("The provided grammar cannot be parsed with the selected parsing method. Try another one.");
                        }
                        action[node.getId()][terminalIndex.get(terminal)] = reduceCell;
                    }
                } else {

                    LRTransition transition = node.getTransition(tokenAfterDot);
                    if(transition.getValue() instanceof NonTerminalToken) {
                        goTo[node.getId()][nonTerminalIndex.get(transition.getValue().getValue())] = transition.getToItemNode().getId();

                    } else if(transition.getValue() instanceof TerminalToken) {
                        if(action[node.getId()][terminalIndex.get(transition.getValue().getValue())] != null) {
                            throw new LRException("The provided grammar cannot be parsed with the selected parsing method. Try another one.");
                        }

                        LRShiftCell shiftCell = new LRShiftCell();
                        shiftCell.setNode(transition.getToItemNode());
                        shiftCell.setNodeId(transition.getToItemNode().getId());
                        action[node.getId()][terminalIndex.get(transition.getValue().getValue())] = shiftCell;
                    }
                }
            }
        }

        // Create entry for the default message
        messageCellMap.put(SyntaxConfig.getInstance().getSyntaxMessageConfig().getDefaultMessage(), messageCellMap.size());

        // Add error cells
        for(int nodeId=0; nodeId < action.length; nodeId++){
            for(String terminal : stateMachine.getGrammar().getTerminals()) {
                if(action[nodeId][terminalIndex.get(terminal)] == null) {

                    LRErrorCell errorCell = null;

                    // Terminal should pop the stack
                    if (terminal.equals(SyntaxHelper.END_OF_STACK)) {
                        errorCell = new LRErrorCell(LRErrorCell.Type.POP, null);
                    } else {
                        boolean goToFound = false;
                        for(String nonTerminal : stateMachine.getGrammar().getNonTerminals()) {
                            if(goTo[nodeId][nonTerminalIndex.get(nonTerminal)] != GO_TO_EMPTY) {
                                goToFound = true;
                                LRAbstractTableCell actionCell = action[goTo[nodeId][nonTerminalIndex.get(nonTerminal)]][terminalIndex.get(terminal)];

                                // Shift and Reduce (Reduce includes Accept)
                                if(actionCell instanceof LRShiftCell || actionCell instanceof LRReduceCell) {
                                    errorCell = new LRErrorCell(LRErrorCell.Type.PUSH, null);
                                    errorCell.setItemNode(stateMachine.getNodes().get(goTo[nodeId][nonTerminalIndex.get(nonTerminal)]));
                                    errorCell.setNonTerminal(nonTerminal);
                                    break;
                                }
                            }
                        }

                        // If cell was not assigned
                        if(errorCell == null) {

                            // If go to found but cell not assigned
                            if (goToFound) {
                                errorCell = new LRErrorCell(LRErrorCell.Type.SCAN, null);
                            } else {
                                errorCell = new LRErrorCell(LRErrorCell.Type.POP, null);
                            }
                        }
                    }

//                    String message = SyntaxConfig.getInstance().getMessage(nonTerminal, terminal);
//                    messageCellMap.putIfAbsent(message, messageCellMap.size());

                    // Add error cell
                    action[nodeId][terminalIndex.get(terminal)] = errorCell;
                }
            }
        }
    }

    /**
     * Get action cell by node and terminal
     * @param node
     * @param terminal
     * @return cell
     */
    public LRAbstractTableCell getActionCell(LRItemNode node, AbstractToken terminal) {
        if(terminal instanceof EndOfFileToken) {
            return action[node.getId()][terminalIndex.get(SyntaxHelper.END_OF_STACK)];
        }
        return action[node.getId()][terminalIndex.get(terminal.getToken())];
    }

    /**
     * Get goto cell by node and terminal
     * @param nodeId
     * @param nonTerminal
     * @return cell
     */
    public int getGoToCell(int nodeId, String nonTerminal) {
        return goTo[nodeId][nonTerminalIndex.get(nonTerminal)];
    }

    /**
     * Prettify action header
     * @return action header as string
     */
    public String[] prettifyActionHeader() {
        String[] header = new String[stateMachine.getGrammar().getTerminals().size()+1];
        header[0] = "Node";
        for(String terminal : stateMachine.getGrammar().getTerminals()) {
            int terminalId = terminalIndex.get(terminal);
            header[terminalId+1] = terminal;
        }
        return header;
    }

    /**
     * Prettify action table
     * @return action table as string table
     */
    public String[][] prettifyActionData() {
        String[][] data = new String[action.length][];
        for(int row = 0; row < data.length; row++) {
            data[row] = new String[action[row].length+1];
            data[row][0] = Integer.toString(row);
            for(int col = 0; col < action[row].length; col++) {
                if(action[row][col] instanceof LRReduceCell) {
                    data[row][col + 1] = "R" + ((LRReduceCell)action[row][col]).getRuleId();

                    if(action[row][col] instanceof LRAcceptCell) {
                        data[row][col + 1] += "(acc)";
                    }

                } else if(action[row][col] instanceof LRShiftCell) {
                    data[row][col + 1] = "S" + ((LRShiftCell)action[row][col]).getNodeId();

                } else if(action[row][col] instanceof LRErrorCell) {
                    LRErrorCell errorCell = (LRErrorCell) action[row][col];

                    if(errorCell.getDecision() == LRErrorCell.Type.POP) {
                        data[row][col + 1] = "Pop";

                    } else if(errorCell.getDecision() == LRErrorCell.Type.PUSH) {
                        data[row][col + 1] = "Push";

                    } else if(errorCell.getDecision() == LRErrorCell.Type.SCAN) {
                        data[row][col + 1] = "Scan";
                    }
                }
            }
        }
        return data;
    }

    /**
     * Prettify go to header
     * @return go to header as string
     */
    public String[] prettifyGoToHeader() {
        String[] header = new String[stateMachine.getGrammar().getNonTerminals().size()+1];
        header[0] = "Node";
        for(String nonTerminal : stateMachine.getGrammar().getNonTerminals()) {
            int nonTerminalId = nonTerminalIndex.get(nonTerminal);
            header[nonTerminalId+1] = nonTerminal;
        }
        return header;
    }

    /**
     * Prettify go to table
     * @return go to table as string table
     */
    public String[][] prettifyGoToData() {
        String[][] data = new String[goTo.length][];
        for(int row = 0; row < data.length; row++) {
            data[row] = new String[goTo[row].length+1];
            data[row][0] = Integer.toString(row);
            for(int col = 0; col < goTo[row].length; col++) {
                if(goTo[row][col] == GO_TO_EMPTY) {
                    data[row][col + 1] = "";
                } else {
                    data[row][col + 1] = Integer.toString(goTo[row][col]);
                }
            }
        }
        return data;
    }

    /**
     * Ascii representation of the action and go to tables
     * @return Ascii tables string
     */
    public String toString() {
        String output = "ACTION TABLE:\n";
        output += ASCIITable.getInstance().getTable(prettifyActionHeader(), prettifyActionData());

        output += "GOTO TABLE:\n";
        output += ASCIITable.getInstance().getTable(prettifyGoToHeader(), prettifyGoToData());

        output += "REDUCE RULES:\n";
        for(LRReduceCell reduceCell : reduceCellList) {
            output += reduceCell.getRuleId() + ": " + reduceCell.getItem().getLHS() + " => " + StringUtils.join(reduceCell.getItem().getRule(), " ") + "\n";
        }
        return output;
    }
}
