package center;

import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import machine.json.Edge;
import machine.json.Lexical_Analysis;
import machine.json.State;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.HashMap;
import java.util.Map;

public class CenterPanel extends JTabbedPane {

    // Components
    private JPanel graphPanel, jsonPanel;
    private JTextPane jsonText;
    private Lexical_Analysis lexical_analysis;

    // Map states
//    Map<String, >

    public CenterPanel() {

        // Init components
        graphPanel = new JPanel();
        jsonPanel = new JPanel();
        jsonText = new JTextPane();

        // Set layout
        graphPanel.setLayout(new BorderLayout());
        jsonPanel.setLayout(new BorderLayout());

        // Set border
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add components
        jsonPanel.add(jsonText, BorderLayout.CENTER);

        addTab("Graph", graphPanel);
        addTab("JSON", jsonPanel);
    }

    private void generateJSON() {

    }

    /**
     * Draw graph on the graph tab
     */
    private void drawGraph() {
        graphPanel.setLayout(new BorderLayout());

        mxGraph graph = new mxGraph();
        Object parent = graph.getDefaultParent();

        // Configure graph
        graph.setAutoSizeCells(true);

        // Start drawing
        graph.getModel().beginUpdate();

        // Add root
        Map<State, Object> stateMap = new HashMap<>();

        try
        {

            for(State state : lexical_analysis.getStates()) {
                Object vertex = graph.insertVertex(parent, null, state.getName(), 20, 20, 80,30);
                stateMap.put(state, vertex);

                // If final
                if(state.getType().equals(State.Type.FINAL)) {
                    graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, "green", new Object[]{vertex});
                    graph.setCellStyles(mxConstants.STYLE_FONTCOLOR, "white", new Object[]{vertex});
                    mxCell cell = (mxCell) vertex;
                    cell.setValue(cell.getValue() + "\nToken: " + state.getToken() + "\nBacktrack: " + state.shouldBacktrack());
                    graph.updateCellSize(vertex);
                }
            }

            for(Edge state : lexical_analysis.getEdges()) {
                Object v1 = stateMap.get(state.getFromState());
                Object v2 = stateMap.get(state.getToState());

                Object[] edges = graph.getEdgesBetween(v1, v2);
                if(edges.length == 0 || (((mxCell) edges[0]).getTarget() == v1 && ((mxCell) edges[0]).getSource() != v1)) {
                    graph.insertEdge(parent, null, state.getValue(), v1, v2);
                } else {
                    mxCell cell = (mxCell) edges[0];
                    cell.setValue(cell.getValue() + ", " + state.getValue());
                }
            }

            mxCompactTreeLayout layout = new mxCompactTreeLayout(graph);
            layout.setEdgeRouting(false);
            layout.setNodeDistance(50);
            layout.setLevelDistance(200);
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
        graphPanel.removeAll();
        graphPanel.add(graphComponent);
        graphPanel.revalidate();
        graphPanel.repaint();
    }

    /**
     * Refresh panels
     */
    public void refresh() {
        drawGraph();
        generateJSON();
    }

    /**
     * Set lexical machine
     * @param lexical_analysis
     */
    public void setLexical_analysis(Lexical_Analysis lexical_analysis) {
        this.lexical_analysis = lexical_analysis;
    }
}
