package dev.stephenpearson.ui.components;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import dev.stephenpearson.event.StateChangeEvent;
import dev.stephenpearson.event.StateChangeListener;
import dev.stephenpearson.model.SimulationState;


public class InfoPanel extends JPanel implements StateChangeListener {
    private final SimulationState state;
    
    private final JLabel speedLabel = new JLabel();
    private final JLabel iterationLabel = new JLabel();
    private final JLabel zoomLabel = new JLabel();
    private final JLabel lockLabel = new JLabel();
    private final JLabel fpsLabel = new JLabel();
    private final JLabel runningLabel = new JLabel();
    private final JLabel thicknessLabel = new JLabel();
    private final JLabel doubleBufLabel = new JLabel();
    
    public InfoPanel(SimulationState state) {
        super(new WrapLayout(FlowLayout.LEFT, 5, 5));
        this.state = state;
        state.addStateChangeListener(this);
        

        add(speedLabel);
        add(iterationLabel);
        add(zoomLabel);
        add(lockLabel);
        add(fpsLabel);
        add(runningLabel);
        add(thicknessLabel);
        add(doubleBufLabel);
        
        updateLabels();
    }
    
    private void updateLabels() {
        speedLabel.setText(String.format("Speed Lvl: %d (%.3f)", 
                state.getSpeedLevel(), state.getSpeed()));
        iterationLabel.setText(String.format("Iteration: %d", 
                state.getIterationCount()));
        zoomLabel.setText(String.format("Zoom: %.1f", 
                state.getCamera().getZoom()));
        lockLabel.setText("Lock: " + state.getCamera().getLockName());
        fpsLabel.setText(String.format("FPS: %d", 
                state.getFps()));
        runningLabel.setText("Running: " + state.isRunning());
        thicknessLabel.setText(String.format("Thickness: %.1f", 
                state.getLineThickness()));
        doubleBufLabel.setText("DBuf: " + state.isDoubleBufferingEnabled());
    }
    
    @Override
    public void onStateChanged(StateChangeEvent event) {
        updateLabels();
    }
}