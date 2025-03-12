package dev.stephenpearson.simulation;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import dev.stephenpearson.model.ColouredSegment;
import dev.stephenpearson.utils.ColorUtils;

//Will have the three points but only 2 will move. Center point fixed, 2nd point rotates center, 3rd rotates 2nd.
public class SimulationEngine {
	
	private double theta = 0;
    private int speedLevel = 1;
    private long iterationCount = 0;
    private double lastX3 = 0;
    private double lastY3 = 0;

    private final List<ColouredSegment> segments = new ArrayList<>();
    private ColouredSegment currentSegment;

    public SimulationEngine() {
        currentSegment = new ColouredSegment(ColorUtils.randomFluorescentColor());
        segments.add(currentSegment);
    }

    public void doSimulationStep() {
   
        theta += 0.01;
        iterationCount++;
        
        double x2 = Math.cos(theta);
        double y2 = Math.sin(theta);
        double xOff = Math.cos(Math.PI * theta);
        double yOff = Math.sin(Math.PI * theta);
        double x3 = x2 + xOff;
        double y3 = y2 + yOff;
        
        currentSegment.points.add(new Point2D.Double(x3, y3));
        lastX3 = x3;
        lastY3 = y3;
    }
    
    public double getTheta() {
        return theta;
    }
}
