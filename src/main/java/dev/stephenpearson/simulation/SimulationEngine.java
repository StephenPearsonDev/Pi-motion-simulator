package dev.stephenpearson.simulation;

import javax.swing.Timer;

import dev.stephenpearson.model.SimulationState;


public class SimulationEngine {
    private final SimulationState state;
    private Timer mainTimer;
    
    private static final double SIM_UPDATE_MS = 1000.0 / 60.0;
    private long lastSimTimeNs = 0;
    
    public SimulationEngine(SimulationState state) {
        this.state = state;
    }
    
    public void setupTimer() {
        if (mainTimer != null && mainTimer.isRunning()) {
            mainTimer.stop();
        }
        
        int delayMs = (int)Math.round(1000.0 / state.getFps());
      
        lastSimTimeNs = System.nanoTime();
        
        mainTimer = new Timer(delayMs, e -> {
  
            if (state.isRunning() && !state.isShowHelp()) {
                long now = System.nanoTime();
                double elapsedMs = (now - lastSimTimeNs)/1_000_000.0;
                if (elapsedMs >= SIM_UPDATE_MS) {
                    lastSimTimeNs = now;
                    doSimulationStep();
                }
            }
            state.fireStateChanged();
        });
        mainTimer.start();
    }
    
    
    /**
     * TASK REQUIRMENTS: 
     * Calculate points using the parametric equation P(θ) = e^(iθ) + e^(iπθ)
     * 
     * 
     * 1. Use Euler's formula: e^(iθ) = cos(θ) + i·sin(θ)
     * 2. For the first term: x2,y2 = cos(θ),sin(θ)
     * 3. For the second term: xOff,yOff = cos(πθ),sin(πθ)
     * 4. The final point (x3,y3) is the sum of these complex numbers
     * 
     * Since π is irrational, resulting pattern never repeats.
     */
    
    public void doSimulationStep() {
        int subSteps = (int)Math.max(1, state.getSpeed() * 300);
        double stepSize = state.getSpeed() / subSteps;
        
        for (int i = 0; i < subSteps; i++) {
            state.incrementTheta(stepSize);
            
            double theta = state.getTheta();
            double x2 = Math.cos(theta);
            double y2 = Math.sin(theta);
            double xOff = Math.cos(Math.PI * theta);
            double yOff = Math.sin(Math.PI * theta);
            double x3 = x2 + xOff;
            double y3 = y2 + yOff;
            
            state.addNewPoint(x3, y3);
        }
    }
    
    public void start() {
        state.setRunning(true);
    }
    
    public void pause() {
        state.setRunning(false);
    }
    
    public void restart() {
        state.resetSimulation();
    }
    
    public void createNewSegment() {
        state.createNewSegment();
    }
    
    public Timer getMainTimer() {
        return mainTimer;
    }
}