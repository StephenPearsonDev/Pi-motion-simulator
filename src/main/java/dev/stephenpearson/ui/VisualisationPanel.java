package dev.stephenpearson.ui;

import javax.swing.*;

import dev.stephenpearson.simulation.SimulationEngine;

import java.awt.*;


//Remember to stack buttons for resizing - need something to wrap them around
public class VisualisationPanel extends JPanel {
	
	private SimulationEngine simulationEngine;

    public VisualisationPanel(SimulationEngine simulationEngine) {
    	 this.simulationEngine = simulationEngine;
         setBackground(new Color(32, 32, 32));
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);
        
        double theta = simulationEngine.getTheta();
        String thetaText = String.format("Theta: %.3f", theta);
        
        g2.drawString(thetaText, 20, 20);
    }
}
