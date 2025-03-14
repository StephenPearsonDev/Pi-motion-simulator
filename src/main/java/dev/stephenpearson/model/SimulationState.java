package dev.stephenpearson.model;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import dev.stephenpearson.event.StateChangeEvent;
import dev.stephenpearson.event.StateChangeListener;

public class SimulationState {

    private final List<StateChangeListener> listeners = new ArrayList<>();
    
    private double theta = 0;
    private int speedLevel = 1;
    private long iterationCount = 0;
    private double lastX3 = 0;
    private double lastY3 = 0;
    
    private final CameraState camera = new CameraState();
    
    private float lineThickness = 1f;
    
    private boolean doubleBufferingEnabled = true;
    private final List<ColoredSegment> segments = new ArrayList<>();
    private ColoredSegment currentSegment;
    
    private volatile boolean running = false;
    private volatile boolean showHelp = false;
    

    private int fps = 60;
    
    public SimulationState() {

        currentSegment = new ColoredSegment();
        segments.add(currentSegment);
        camera.setStateChangeCallback(event -> fireStateChanged());
    }
    

    public void addStateChangeListener(StateChangeListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }
    
    public void removeStateChangeListener(StateChangeListener listener) {
        listeners.remove(listener);
    }
    

    public void fireStateChanged() {
        StateChangeEvent event = new StateChangeEvent(this);
        for (StateChangeListener listener : listeners) {
            listener.onStateChanged(event);
        }
    }
    
    protected void firePropertyChanged(String propertyName, Object oldValue, Object newValue) {
        StateChangeEvent event = new StateChangeEvent(this, propertyName, oldValue, newValue);
        for (StateChangeListener listener : listeners) {
            listener.onStateChanged(event);
        }
    }
    
    public void resetSimulation() {
        theta = 0;
        speedLevel = 1;
        iterationCount = 0;
        lineThickness = 1f;
        camera.reset();
        
        segments.clear();
        currentSegment = new ColoredSegment();
        segments.add(currentSegment);
        
        running = true;
        fireStateChanged();
    }
    
    public void addNewPoint(double x3, double y3) {
        lastX3 = x3;
        lastY3 = y3;
        currentSegment.addPoint(new Point2D.Double(x3, y3));
    }
    
    public void createNewSegment() {
        currentSegment = new ColoredSegment();
        segments.add(currentSegment);
        fireStateChanged();
    }

    public double getTheta() {
        return theta;
    }
    
    public void setTheta(double theta) {
        this.theta = theta;
    }
    
    public void incrementTheta(double amount) {
        this.theta += amount;
        iterationCount++;
    }
    
    public int getSpeedLevel() {
        return speedLevel;
    }
    
    public void setSpeedLevel(int speedLevel) {
        if (speedLevel < 1) speedLevel = 1;
        if (speedLevel > 20) speedLevel = 20;
        
        int oldValue = this.speedLevel;
        this.speedLevel = speedLevel;
        firePropertyChanged("speedLevel", oldValue, speedLevel);
        fireStateChanged();
    }
    
    public void incrementSpeedLevel() {
        if (speedLevel < 20) {
            int oldValue = speedLevel;
            speedLevel++;
            firePropertyChanged("speedLevel", oldValue, speedLevel);
            fireStateChanged();
        }
    }
    
    public void decrementSpeedLevel() {
        if (speedLevel > 1) {
            int oldValue = speedLevel;
            speedLevel--;
            firePropertyChanged("speedLevel", oldValue, speedLevel);
            fireStateChanged();
        }
    }
    
    public double getSpeed() {
        return 0.005 * speedLevel;
    }
    
    public long getIterationCount() {
        return iterationCount;
    }
    
    public double getLastX3() {
        return lastX3;
    }
    
    public double getLastY3() {
        return lastY3;
    }
    
    public CameraState getCamera() {
        return camera;
    }
    
    public float getLineThickness() {
        return lineThickness;
    }
    
    public void setLineThickness(float lineThickness) {
        float oldValue = this.lineThickness;
        this.lineThickness = lineThickness;
        firePropertyChanged("lineThickness", oldValue, lineThickness);
        fireStateChanged();
    }
    
    public void incrementLineThickness() {
        if (lineThickness < 3f) {
            float oldValue = lineThickness;
            lineThickness += 1f;
            firePropertyChanged("lineThickness", oldValue, lineThickness);
            fireStateChanged();
        }
    }
    
    public void decrementLineThickness() {
        if (lineThickness > 1f) {
            float oldValue = lineThickness;
            lineThickness -= 1f;
            firePropertyChanged("lineThickness", oldValue, lineThickness);
            fireStateChanged();
        }
    }
    
    public boolean isDoubleBufferingEnabled() {
        return doubleBufferingEnabled;
    }
    
    public void setDoubleBufferingEnabled(boolean enabled) {
        boolean oldValue = this.doubleBufferingEnabled;
        this.doubleBufferingEnabled = enabled;
        firePropertyChanged("doubleBufferingEnabled", oldValue, enabled);
        fireStateChanged();
    }
    
    public void toggleDoubleBuffering() {
        boolean oldValue = doubleBufferingEnabled;
        doubleBufferingEnabled = !doubleBufferingEnabled;
        firePropertyChanged("doubleBufferingEnabled", oldValue, doubleBufferingEnabled);
        fireStateChanged();
    }
    
    public List<ColoredSegment> getSegments() {
        return segments;
    }
    
    public ColoredSegment getCurrentSegment() {
        return currentSegment;
    }
    
    public boolean isRunning() {
        return running;
    }
    
    public void setRunning(boolean running) {
        boolean oldValue = this.running;
        this.running = running;
        firePropertyChanged("running", oldValue, running);
        fireStateChanged();
    }
    
    public boolean isShowHelp() {
        return showHelp;
    }
    
    public void setShowHelp(boolean showHelp) {
        boolean oldValue = this.showHelp;
        this.showHelp = showHelp;
        firePropertyChanged("showHelp", oldValue, showHelp);
        fireStateChanged();
    }
    
    public int getFps() {
        return fps;
    }
    
    public void setFps(int fps) {
        int oldValue = this.fps;
        this.fps = fps;
        firePropertyChanged("fps", oldValue, fps);
        fireStateChanged();
    }
    
    public Point2D getLockedWorldPoint() {
        int lockOption = camera.getCameraLockOption();
        if (lockOption == 1) {
            return new Point2D.Double(0, 0);
        } else if (lockOption == 2) {
            return new Point2D.Double(Math.cos(theta), Math.sin(theta));
        } else {
            return new Point2D.Double(lastX3, lastY3);
        }
    }
}