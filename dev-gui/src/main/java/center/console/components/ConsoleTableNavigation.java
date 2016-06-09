package center.console.components;

import center.console.TabbedConsolePanel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

/**
 * Created by Amir on 3/26/2016.
 */
public class ConsoleTableNavigation extends JPanel {

    // Components
    private TabbedConsolePanel tabbedPane;
    private JPanel navigationPanel;
    private Stack<Integer> pageStack;
    private int lastPage;

    public ConsoleTableNavigation() {

        // Init components
        tabbedPane = new TabbedConsolePanel();
        navigationPanel = new JPanel();
        pageStack = new Stack<>();

        // Set layout
        setLayout(new BorderLayout());
        navigationPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Labels
        JLabel backwardLabel = new JLabel("<html><font color=#005DFF><u>&lt;&lt; Previous tab</u></font></html>");

        // Add label listener
        backwardLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!pageStack.isEmpty()) {
                    lastPage = pageStack.peek();
                    tabbedPane.setSelectedIndex(pageStack.pop());
                }
            }
        });

        // Add on tab change
        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(tabbedPane.getSelectedIndex() != lastPage) {
                    pageStack.push(lastPage);
                    lastPage = tabbedPane.getSelectedIndex();
                }
            }
        });

        // Add labels
        navigationPanel.add(backwardLabel);

        // Add components
        add(tabbedPane, BorderLayout.CENTER);
        add(navigationPanel, BorderLayout.NORTH);
    }

    public TabbedConsolePanel getTabbedPane() {
        return tabbedPane;
    }

    public void setSelectedIndex(int i) {
        tabbedPane.setSelectedIndex(i);
    }

    public void removeTables() {
        tabbedPane.removeAll();
        tabbedPane.getTabPanelsMap().clear();
        pageStack.clear();
        lastPage = 0;
    }
}
