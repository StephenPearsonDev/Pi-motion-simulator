package dev.stephenpearson.application;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import dev.stephenpearson.model.SimulationState;
import dev.stephenpearson.simulation.SimulationEngine;
import dev.stephenpearson.ui.components.ControlPanel;
import dev.stephenpearson.ui.components.InfoPanel;
import dev.stephenpearson.ui.screen.ScreenPanel;

public class Application {
    private final JFrame frame;
    private final SimulationState state;
    private final SimulationEngine simulator;
    private final ScreenPanel visualizer;
    private final ControlPanel controlPanel;
    private final InfoPanel infoPanel;

    public Application() {

        state = new SimulationState();
        simulator = new SimulationEngine(state);
        visualizer = new ScreenPanel(state);
        controlPanel = new ControlPanel(simulator, visualizer, state);
        infoPanel = new InfoPanel(state);


        frame = new JFrame("Pi Irrational Visualization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(400, 200));
        frame.setMaximumSize(new Dimension(1600, 1000));
        frame.setSize(900, 700);
        frame.setLocationRelativeTo(null);


        frame.setLayout(new BorderLayout());
        frame.add(controlPanel, BorderLayout.NORTH);
        frame.add(visualizer, BorderLayout.CENTER);
        frame.add(infoPanel, BorderLayout.SOUTH);
    }

    public void start() {
        frame.setVisible(true);
        simulator.setupTimer();
        visualizer.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Application app = new Application();
            app.start();
        });
    }

    public JFrame getFrame() {
        return frame;
    }
}