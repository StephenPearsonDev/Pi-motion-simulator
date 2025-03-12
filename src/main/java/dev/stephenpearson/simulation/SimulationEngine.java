package dev.stephenpearson.simulation;


//Will have the three points but only 2 will move. Center point fixed, 2nd point rotates center, 3rd rotates 2nd.
public class SimulationEngine {
	
	
    private double theta = 0;

    public void doSimulationStep() {
        theta += 0.01;
    }
    
    public double getTheta() {
        return theta;
    }
}
