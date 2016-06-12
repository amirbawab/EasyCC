package helper;

import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import machine.StateMachine;
import machine.json.State;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.*;

public class GuiHelper {

    /**
     * Visualize state machine on a JPanel
     * @param stateMachine
     * @return panel
     */
    public static JPanel stateMachineToPanel(StateMachine stateMachine) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        mxGraph graph = new mxGraph();
        Object parent = graph.getDefaultParent();

        // Configure graph
        graph.setAutoSizeCells(true);

        // Start drawing
        graph.getModel().beginUpdate();

        // Create nodes
        Queue<State> statesQueue = new LinkedList<>();

        // Add root
        Map<State, Object> stateMap = new HashMap<>();

        State initial = stateMachine.getInitialState();
        if(initial != null) {
            statesQueue.offer(initial);
            stateMap.put(statesQueue.peek(), graph.insertVertex(parent, null, "", 20, 20, 80,30));
        }

        try
        {
            while(!statesQueue.isEmpty()) {

                // Peek
                State currentState = statesQueue.poll();
                Object v1 = stateMap.get(currentState);
                mxCell v1Cell = (mxCell) v1;
                v1Cell.setValue("id: " + currentState.getId() + "\nname: " + currentState.getName());

                // If final
                if(currentState.getType() == State.Type.FINAL) {
                    graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, "green", new Object[]{v1});
                    graph.setCellStyles(mxConstants.STYLE_FONTCOLOR, "white", new Object[]{v1});
                    v1Cell.setValue(v1Cell.getValue() + "\ntoken: " + currentState.getToken() + "\nbacktrack: " + currentState.shouldBacktrack());
                }
                graph.updateCellSize(v1);

                for (int i=0; i < currentState.getOutEdges().size(); i++) {

                    // Get token
                    State token = currentState.getOutEdges().get(i).getToState();

                    // If not already visited
                    Object v2;
                    if(!stateMap.containsKey(token)) {

                        // Add child
                        v2 = graph.insertVertex(parent, null, "", 240, 150, 80, 30);

                        stateMap.put(token, v2);
                        statesQueue.offer(token);
                    } else {
                        v2 = stateMap.get(token);
                    }

                    Object[] edges = graph.getEdgesBetween(v1, v2);
                    if(edges.length == 0 || (((mxCell) edges[0]).getTarget() == v1 && ((mxCell) edges[0]).getSource() != v1)) {
                        graph.insertEdge(parent, null, currentState.getOutEdges().get(i).getValue(), v1, v2);
                    } else {
                        mxCell eCell = (mxCell) edges[0];
                        eCell.setValue(eCell.getValue() + ", " + currentState.getOutEdges().get(i).getValue());
                    }

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
        panel.add(graphComponent, BorderLayout.CENTER);
        return panel;
    }
}
