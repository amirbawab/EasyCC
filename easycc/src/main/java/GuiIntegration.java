import com.mxgraph.layout.*;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import core.config.SemanticHandler;
import core.structure.symbol.SymbolTableTree;
import core.structure.symbol.table.SymbolTable;
import data.GenericTable;
import data.LexicalAnalysisRow;
import data.SemanticErrorRow;
import data.SyntaxAnalysisRow;
import data.structure.ConsoleData;
import helper.GuiHelper;
import listener.DevGuiListener;
import machine.StateMachine;
import machine.json.State;
import org.apache.commons.lang3.StringUtils;
import parser.strategy.LLPP.LLPP;
import parser.strategy.LLPP.data.LLPPDataEntry;
import parser.strategy.LLPP.data.LLPPDataErrorEntry;
import parser.strategy.LLPP.data.LLPPDataFineEntry;
import token.*;
import utils.StringUtilsPlus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.*;
import java.util.List;

public class GuiIntegration implements DevGuiListener {

    private LexicalAnalyzer lexicalAnalyzer;
    private SyntaxAnalyzer syntaxAnalyzer;
    private SemanticAnalyzer semanticAnalyzer;
    private boolean lexicalCompiles, syntaxCompiles, semanticCompiles;

    public GuiIntegration(LexicalAnalyzer lexicalAnalyzer, SyntaxAnalyzer syntaxAnalyzer, SemanticAnalyzer semanticAnalyzer){
        this.lexicalAnalyzer = lexicalAnalyzer;
        this.syntaxAnalyzer = syntaxAnalyzer;
        this.semanticAnalyzer = semanticAnalyzer;
    }

    @Override
    public void lexicalAnalysis(String text) {
        lexicalAnalyzer.analyzeText(text);
        lexicalCompiles = lexicalAnalyzer.getErrorMessagesList().size() == 0;
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
                rows.add(new LexicalAnalysisRow(token.getToken(), token.getValue(), token.getRow(), token.getCol(), token.getPosition(), ((ErrorToken) token).getMessage()));
            }
        }

        return rows;
    }

    @Override
    public void parse() {
        if(lexicalCompiles) {
            syntaxCompiles = syntaxAnalyzer.parse(lexicalAnalyzer.getFirstToken());
        }
    }

    @Override
    public ConsoleData<SyntaxAnalysisRow> getParserOutput() {

        ConsoleData<SyntaxAnalysisRow> rows = new ConsoleData<>();
        if(lexicalCompiles && syntaxAnalyzer.getSyntaxParser().getParseStrategy() instanceof LLPP) {
            LLPP llpp = (LLPP) syntaxAnalyzer.getSyntaxParser().getParseStrategy();
            if(llpp.getLlppData() != null) {
                for (int row = 0; row < llpp.getLlppData().getEntryList().size(); row++) {

                    // Get current
                    LLPPDataEntry entry = llpp.getLlppData().getEntryList().get(row);

                    // If error
                    if (entry instanceof LLPPDataFineEntry) {
                        LLPPDataFineEntry dataFineEntry = (LLPPDataFineEntry) entry;
                        rows.add(new SyntaxAnalysisRow(dataFineEntry.getStepNumber(), dataFineEntry.getStackContent(), dataFineEntry.getInputContent(), dataFineEntry.getProductionContent(), dataFineEntry.getDerivationContent()));

                    } else if (entry instanceof LLPPDataErrorEntry) {
                        LLPPDataErrorEntry dataErrorEntry = (LLPPDataErrorEntry) entry;
                        rows.add(new SyntaxAnalysisRow(dataErrorEntry.getStepNumber(), dataErrorEntry.getStackContent(), dataErrorEntry.getInputContent(), "", dataErrorEntry.getMessage()));
                    }
                }
            }
        }
        return rows;
    }

    @Override
    public List<String> getSyntaxErrorMessages() {
        if(syntaxAnalyzer.getSyntaxParser().getParseStrategy() instanceof LLPP) {
            LLPP llpp = (LLPP) syntaxAnalyzer.getSyntaxParser().getParseStrategy();
            if(llpp.getLlppData() != null && lexicalCompiles) {
                return llpp.getLlppData().getErrorMessages();
            }
            return new ArrayList<>();
        }
        return null;
    }

    @Override
    public long getLexicalAnalysisTime() {
        return lexicalAnalyzer.getProcessTime();
    }

    @Override
    public long getParserTime() {
        return syntaxAnalyzer.getProcessTime();
    }

    @Override
    public boolean doesCompile() {
        return lexicalCompiles && syntaxCompiles;
    }

    @Override
    public List<GenericTable> getSymbolTables() {
        List<SymbolTable> symbolTables = SemanticHandler.getInstance().getSymbolTableTree().getSymbolTables();
        List<GenericTable> genericTables = new ArrayList<>();
        String[] header = SemanticHandler.getInstance().getSymbolTableTree().prettifyHeader();
        for(int i=0; i < symbolTables.size(); i++) {
            SymbolTable symbolTable = symbolTables.get(i);
            GenericTable genericTable = new GenericTable();
            genericTable.setName(i + 1 + " - " + symbolTable.getPath());
            genericTable.setHeader(StringUtilsPlus.convertStringArrayToObjectArray(header));
            genericTable.setData(StringUtilsPlus.convertStringTableToObjectTable(symbolTable.prettifyData()));
            genericTables.add(genericTable);
        }
        return genericTables;
    }

    @Override
    public ConsoleData<SemanticErrorRow> getSemanticErrors() {
        ConsoleData<SemanticErrorRow> rows = new ConsoleData<>();
        for(String message : SemanticHandler.getInstance().getErrorsList()) {
            rows.add(new SemanticErrorRow(message));
        }
        return rows;
    }

    @Override
    public JPanel getDerivationTree() {

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        mxGraph graph = new mxGraph();
        Object parent = graph.getDefaultParent();

        // Configure graph
        graph.setAutoSizeCells(true);

        // Start drawing
        graph.getModel().beginUpdate();

        // Create nodes
        Queue<AbstractSyntaxToken> tokensQueue = new LinkedList<>();
        Map<AbstractSyntaxToken, Object> vertexMap = new HashMap<>();

        // Add root
        tokensQueue.offer(syntaxAnalyzer.getSyntaxParser().getParseStrategy().getDerivationRoot());
        Object vRoot = graph.insertVertex(parent, null, tokensQueue.peek().getValue(), 20, 20, 80,30);
        vertexMap.put(tokensQueue.peek(), vRoot);
        try
        {
            while(!tokensQueue.isEmpty()) {

                // Peek
                AbstractSyntaxToken currentToken = tokensQueue.poll();

                // Create node
                Object v1 = vertexMap.get(currentToken);
                graph.updateCellSize(v1);

                if(currentToken instanceof NonTerminalToken) {
                    NonTerminalToken currentNonTerminal =(NonTerminalToken) currentToken;

                    for (int i=currentNonTerminal.getChildren().size()-1; i >= 0; i--) {

                        // Get token
                        AbstractSyntaxToken token = currentNonTerminal.getChildren().get(i);

                        // Get value
                        String value = token.getValue();
                        if(token instanceof TerminalToken) {
                            value += "\n" + ((TerminalToken) token).getLexicalToken().getValue();
                        }

                        // Add child
                        Object v2 = graph.insertVertex(parent, null, value, 240, 150, 80, 30);
                        vertexMap.put(token, v2);
                        tokensQueue.offer(token);

                        graph.insertEdge(parent, null, null, v1, v2);
                    }

                } else if(currentToken instanceof TerminalToken) {
                    graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, "green", new Object[]{v1});
                    graph.setCellStyles(mxConstants.STYLE_FONTCOLOR, "white", new Object[]{v1});

                } else if(currentToken instanceof EpsilonToken) {
                    graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, "brown", new Object[]{v1});
                    graph.setCellStyles(mxConstants.STYLE_FONTCOLOR, "white", new Object[]{v1});

                } else if(currentToken instanceof ActionToken) {
                    graph.setCellStyles(mxConstants.STYLE_FONTCOLOR, "red", new Object[]{v1});
                }
            }

            mxHierarchicalLayout layout = new mxHierarchicalLayout(graph, SwingConstants.NORTH);
            layout.execute(parent);
        }
        finally
        {
            graph.getModel().endUpdate();
        }

        final mxGraphComponent graphComponent = new mxGraphComponent(graph);

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
            data[i][1] = llpp.getLlppTable().getRulesList().get(i).getNonTerminal() + " => " + StringUtils.join(llpp.getLlppTable().getRulesList().get(i).getRule(), " ");
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
            data[index][1] = StringUtils.join(syntaxAnalyzer.getGrammar().getFirstSetOfNonTerminal(nonTerminal), ", ");
            data[index][2] = StringUtils.join(syntaxAnalyzer.getGrammar().getFollowSetMap().get(nonTerminal), ", ");
            index++;
        }
        genericTable.setData(data);
    }

    @Override
    public JPanel getStateMachineGraph() {
        return GuiHelper.stateMachineToPanel(lexicalAnalyzer.getStateMachine());
    }
}
