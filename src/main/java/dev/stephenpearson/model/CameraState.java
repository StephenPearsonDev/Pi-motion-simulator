package dev.stephenpearson.model;

import java.util.function.Consumer;

import dev.stephenpearson.event.StateChangeEvent;


public class CameraState {
    
	//TODO change this to a better way of handling the change events its not very clean or clear
    private Consumer<StateChangeEvent> stateChangeListener;
    
 
    private double zoom = 100;
    private double offsetX = 0;
    private double offsetY = 0;
    private static final int MAX_PAN = 300;
    

    private int cameraLockOption = 1;
    
    public CameraState() {
    }
    
    public void setStateChangeCallback(Consumer<StateChangeEvent> stateChangeListener) {
        this.stateChangeListener = stateChangeListener;
    }
    
    protected void notifyChange() {
        if (stateChangeListener != null) {
            stateChangeListener.accept(null);
        }
    }
    
    public void reset() {
        zoom = 100;
        offsetX = 0;
        offsetY = 0;
        cameraLockOption = 1;
        notifyChange();
    }
    
    public double getZoom() {
        return zoom;
    }
    
    public void setZoom(double zoom) {
        if (zoom < 1) zoom = 1;
        this.zoom = zoom;
        notifyChange();
    }
    
    public double getOffsetX() {
        return offsetX;
    }
    
    public void setOffsetX(double offsetX) {
        this.offsetX = clamp(offsetX, -MAX_PAN, MAX_PAN);
        notifyChange();
    }
    
    public double getOffsetY() {
        return offsetY;
    }
    
    public void setOffsetY(double offsetY) {
        this.offsetY = clamp(offsetY, -MAX_PAN, MAX_PAN);
        notifyChange();
    }
    
    public void pan(double dx, double dy) {
        setOffsetX(offsetX + dx);
        setOffsetY(offsetY + dy);
    }
    
    public int getCameraLockOption() {
        return cameraLockOption;
    }
    
    public void setCameraLockOption(int option) {
        if (option < 1) option = 1;
        if (option > 3) option = 3;
        
        cameraLockOption = option;
        notifyChange();
    }
    
    public String getLockName() {
        switch (cameraLockOption) {
            case 1: return "Center";
            case 2: return "Middle";
            case 3: return "Outer";
            default: return "Unknown";
        }
    }
    
    private double clamp(double val, double min, double max) {
        return Math.max(min, Math.min(max, val));
    }
}