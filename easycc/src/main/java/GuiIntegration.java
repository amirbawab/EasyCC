import com.mxgraph.layout.*;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import data.GenericTable;
import data.LexicalAnalysisRow;
import data.structure.ConsoleData;
import helper.LexicalHelper;
import listener.DevGuiListener;
import machine.StateMachine;
import machine.json.State;
import org.apache.commons.lang3.StringUtils;
import parser.strategy.LLPP.LLPP;
import token.AbstractToken;
import token.ErrorToken;
import token.LexicalToken;
import utils.StringUtilsPlus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.*;

public class GuiIntegration implements DevGuiListener {

    private LexicalAnalyzer lexicalAnalyzer;
    private SyntaxAnalyzer syntaxAnalyzer;

    public GuiIntegration(LexicalAnalyzer lexicalAnalyzer, SyntaxAnalyzer syntaxAnalyzer){
        this.lexicalAnalyzer = lexicalAnalyzer;
        this.syntaxAnalyzer = syntaxAnalyzer;
    }

    @Override
    public void lexicalAnalysis(String text) {
        lexicalAnalyzer.analyzeText(text);
    }

    @Override
    public ConsoleData<LexicalAnalysisRow> getLexicalAnalyzerOutput() {

        ConsoleData<LexicalAnalysisRow> rows = new ConsoleData<>();
        for(int row = 0; row < lexicalAnalyzer.getTokens().size(); row++) {

            // Get current
            AbstractToken token = lexicalAnalyzer.getTokens().get(row);

            // If not error
            if(token instanceof LexicalToken)
                rows.add(new LexicalAnalysisRow(token.getToken(), token.getValue(), token.getRow(), token.getCol(), token.getPosition()));
        }

        return rows;
    }

    @Override
    public ConsoleData<LexicalAnalysisRow> getLexicalAnalyzerError() {

        ConsoleData<LexicalAnalysisRow> rows = new ConsoleData<>();
        for(int row = 0; row < lexicalAnalyzer.getTokens().size(); row++) {

            // Get current
            AbstractToken token = lexicalAnalyzer.getTokens().get(row);

            // If error
            if(token instanceof ErrorToken) {
                rows.add(new LexicalAnalysisRow(token.getToken(), token.getValue(), token.getRow(), token.getCol(), token.getPosition(), LexicalHelper.tokenMessage(token)));
            }
        }

        return rows;
    }

    @Override
    public void parse() {

    }

    @Override
    public Object[][] getParserOutput() {
        return new Object[0][];
    }

    @Override
    public Object[][] getParserError() {
        return new Object[0][];
    }

    @Override
    public long getLexicalAnalysisTime() {
        return lexicalAnalyzer.getProcessTime();
    }

    @Override
    public long getParserTime() {
        return 0;
    }

    @Override
    public boolean doesCompile() {
        return false;
    }

    @Override
    public Object[][][] getSymbolTables() {
        return new Object[0][][];
    }

    @Override
    public String getSymbolTableName(int id) {
        return null;
    }

    @Override
    public Object[][] getSemanticErrors() {
        return new Object[0][];
    }

    @Override
    public JPanel getParserTree() {
        return null;
    }

    @Override
    public String getGeneratedCode() {
        return null;
    }

    @Override
    public void setStateTable(GenericTable genericTable) {
        genericTable.setHeader(StringUtilsPlus.convertStringArrayToObjectArray(lexicalAnalyzer.getStateTransitionTable().prettifyStateTransitionTableHeader()));
        genericTable.setData(StringUtilsPlus.convertStringTableToObjectTable(lexicalAnalyzer.getStateTransitionTable().prettifyStateTransitionTableData()));
    }

    @Override
    public void setLLPPTable(GenericTable genericTable) {
        LLPP llpp = (LLPP) syntaxAnalyzer.getSyntaxParser().getParseStrategy();
        genericTable.setHeader(StringUtilsPlus.convertStringArrayToObjectArray(llpp.getLlppTable().prettifyPPTableHeader()));
        genericTable.setData(StringUtilsPlus.convertStringTableToObjectTable(llpp.getLlppTable().prettifyPPTableData()));
    }

    @Override
    public void setLLPPTableRules(GenericTable genericTable) {
        LLPP llpp = (LLPP) syntaxAnalyzer.getSyntaxParser().getParseStrategy();
        Object data[][] = new Object[llpp.getLlppTable().getRulesList().size()][2];
        for(int i=0; i < data.length; i++) {
            data[i][0] = llpp.getLlppTable().getRulesList().get(i).getId();
            data[i][1] = llpp.getLlppTable().getRulesList().get(i).getNonTerminal() + " => " + StringUtils.join(llpp.getLlppTable().getRulesList().get(i).getProduction(), " ");
        }
        genericTable.setData(data);
        genericTable.setHeader(new Object[]{"Rule#","Production"});
    }

    @Override
    public void setLLPPTableErrors(GenericTable genericTable) {
        LLPP llpp = (LLPP) syntaxAnalyzer.getSyntaxParser().getParseStrategy();
        Object data[][] = new Object[llpp.getLlppTable().getMessagesSet().size()][2];

        int i=0;
        for(String message : llpp.getLlppTable().getMessagesSet()) {
            data[i][0] = i;
            data[i][1] = message;
            i++;
        }
        genericTable.setData(data);
        genericTable.setHeader(new Object[]{"Error#","Message"});
    }

    @Override
    public void setFirstAndFollowSets(GenericTable genericTable) {
        genericTable.setHeader(new Object[] {"Non terminal", "First set", "Follow set"});
        Object[][] data = new Object[syntaxAnalyzer.getGrammar().getNonTerminals().size()][3];
        int index=0;
        for(String nonTerminal : syntaxAnalyzer.getGrammar().getNonTerminals()) {
            data[index][0] = nonTerminal;
            data[index][1] = StringUtils.join(syntaxAnalyzer.getGrammar().getFirstSetMap().get(nonTerminal), ", ");
            data[index][2] = StringUtils.join(syntaxAnalyzer.getGrammar().getFollowSetMap().get(nonTerminal), ", ");
            index++;
        }
        genericTable.setData(data);
    }

    @Override
    public JPanel getStateMachineGraph() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        mxGraph graph = new mxGraph();
        Object parent = graph.getDefaultParent();

        // Configure graph
        graph.setAutoSizeCells(true);

        // Start drawing
        graph.getModel().beginUpdate();

        StateMachine stateMachine = lexicalAnalyzer.getStateMachine();

        // Create nodes
        Queue<State> statesQueue = new LinkedList<>();

        // Add root
        Map<State, Object> stateMap = new HashMap<>();
        statesQueue.offer(stateMachine.getInitialState());
        stateMap.put(statesQueue.peek(), graph.insertVertex(parent, null, statesQueue.peek().getId(), 20, 20, 80,30));

        try
        {
            while(!statesQueue.isEmpty()) {

                // Peek
                State currentState = statesQueue.poll();
                Object v1 = stateMap.get(currentState);

                for (int i=currentState.getOutEdges().size()-1; i >= 0; i--) {

                    // Get token
                    State token = currentState.getOutEdges().get(i).getToState();

                    // If not already visited
                    Object v2;
                    if(!stateMap.containsKey(token)) {

                        // Add child
                        v2 = graph.insertVertex(parent, null, token.getId(), 240, 150, 80, 30);
                        stateMap.put(token, v2);
                        statesQueue.offer(token);
                    } else {
                        v2 = stateMap.get(token);
                    }

                    Object[] edges = graph.getEdgesBetween(v1, v2);
                    if(edges.length == 0) {
                        graph.insertEdge(parent, null, currentState.getOutEdges().get(i).getValue(), v1, v2);
                    } else {
                        mxCell cell = (mxCell) edges[0];
                        cell.setValue(cell.getValue() + ", " + currentState.getOutEdges().get(i).getValue());
                    }

                }
            }

            mxCompactTreeLayout layout = new mxCompactTreeLayout(graph);
            layout.setEdgeRouting(false);
            layout.setNodeDistance(100);
            layout.execute(parent);
        } finally
        {
            graph.getModel().endUpdate();
        }

        final mxGraphComponent graphComponent = new mxGraphComponent(graph);
        graphComponent.setEnabled(true);

        // Add wheel listener
        graphComponent.addMouseWheelListener(new MouseWheelListener() {
            final int MAX_ZOOM = 10;
            final int MIN_ZOOM = -10;
            int zoomValue = 0;

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if((e.getModifiers() & InputEvent.CTRL_MASK) == InputEvent.CTRL_MASK) {
                    if (e.getWheelRotation() < 0 && zoomValue < MAX_ZOOM) {
                        graphComponent.zoomIn();
                        zoomValue++;

                    } else if(e.getWheelRotation() > 0 && zoomValue > MIN_ZOOM) {
                        graphComponent.zoomOut();
                        zoomValue--;
                    }
                }
            }
        });

        // Add component
        panel.add(graphComponent, BorderLayout.CENTER);
        return panel;
    }
}
