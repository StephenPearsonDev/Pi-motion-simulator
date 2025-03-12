package dev.stephenpearson.ui;

import javax.swing.*;

import dev.stephenpearson.simulation.SimulationEngine;

public class Main extends JFrame {
	
    public Main() {
        setTitle("Pi Motion Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        
        
        SimulationEngine simulationEngine = new SimulationEngine();
        add(new VisualisationPanel(simulationEngine));
        
        setVisible(true);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main());
    }
}
