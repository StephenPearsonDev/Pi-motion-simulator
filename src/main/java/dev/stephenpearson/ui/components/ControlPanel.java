package dev.stephenpearson.ui.components;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import dev.stephenpearson.event.StateChangeEvent;
import dev.stephenpearson.event.StateChangeListener;
import dev.stephenpearson.model.SimulationState;
import dev.stephenpearson.simulation.SimulationEngine;
import dev.stephenpearson.ui.dialog.HelpScreen;
import dev.stephenpearson.ui.screen.ScreenPanel;


public class ControlPanel extends JPanel implements StateChangeListener {
    private final SimulationEngine simulator;
   
    private final SimulationState state;
    
    private final JButton startPauseBtn;
    
    public ControlPanel(SimulationEngine simulator, ScreenPanel visualizer, SimulationState state) {
        super(new WrapLayout(FlowLayout.LEFT, 10, 10));
        
        this.simulator = simulator;
  
        this.state = state;
        state.addStateChangeListener(this);
        
        startPauseBtn = new JButton("Start");
        startPauseBtn.addActionListener(e -> toggleRunning());
        add(startPauseBtn);
        
      
        JButton restartBtn = new JButton("Restart");
        restartBtn.addActionListener(e -> simulator.restart());
        add(restartBtn);
        
   
        JButton colorBtn = new JButton("New Segment Color");
        colorBtn.addActionListener(e -> simulator.createNewSegment());
        add(colorBtn);
        
       
        JButton speedUpBtn = new JButton("Speed +");
        speedUpBtn.addActionListener(e -> state.incrementSpeedLevel());
        add(speedUpBtn);
        
        JButton speedDownBtn = new JButton("Speed -");
        speedDownBtn.addActionListener(e -> state.decrementSpeedLevel());
        add(speedDownBtn);
        
  
        JButton resetZoomBtn = new JButton("Reset Zoom");
        resetZoomBtn.addActionListener(e -> {
            state.getCamera().reset();
        });
        add(resetZoomBtn);
        
    
        String[] fpsOptions = {"30 FPS", "60 FPS", "120 FPS"};
        JComboBox<String> fpsCombo = new JComboBox<>(fpsOptions);
        fpsCombo.setSelectedIndex(1);
        fpsCombo.addActionListener(e -> {
            int idx = fpsCombo.getSelectedIndex();
            int fps = idx == 0 ? 30 : (idx == 1 ? 60 : 120);
            state.setFps(fps);
            simulator.setupTimer();
        });
        add(fpsCombo);
        
   
        String[] lockOptions = {"Center", "Middle", "Outer"};
        JComboBox<String> lockCombo = new JComboBox<>(lockOptions);
        lockCombo.setSelectedIndex(0);
        lockCombo.addActionListener(e -> {
            state.getCamera().setCameraLockOption(lockCombo.getSelectedIndex() + 1);
        });
        add(lockCombo);
        
        JButton incLineBtn = new JButton("Increase Line Thickness");
        incLineBtn.addActionListener(e -> state.incrementLineThickness());
        add(incLineBtn);
        
        JButton decLineBtn = new JButton("Decrease Line Thickness");
        decLineBtn.addActionListener(e -> state.decrementLineThickness());
        add(decLineBtn);

        JButton dblBufBtn = new JButton("Toggle Double Buffering");
        dblBufBtn.addActionListener(e -> state.toggleDoubleBuffering());
        add(dblBufBtn);

        JButton helpBtn = new JButton("Help");
        helpBtn.addActionListener(e -> {
            SwingUtilities.getWindowAncestor(this).setVisible(true);
            HelpScreen.show(SwingUtilities.getWindowAncestor(this), state, simulator);
        });
        add(helpBtn);
    }
    
    private void toggleRunning() {
        if (state.isRunning()) {
            simulator.pause();
            startPauseBtn.setText("Unpause");
        } else {
            simulator.start();
            startPauseBtn.setText("Pause");
        }
    }
    
    @Override
    public void onStateChanged(StateChangeEvent event) {
        if (event.isPropertyChange("running")) {
            startPauseBtn.setText(state.isRunning() ? "Pause" : "Unpause");
        }
    }
}