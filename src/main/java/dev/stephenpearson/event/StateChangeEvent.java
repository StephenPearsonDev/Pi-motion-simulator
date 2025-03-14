package dev.stephenpearson.event;


import dev.stephenpearson.model.SimulationState;

public class StateChangeEvent {
    private final SimulationState source;
    private final String propertyName;
    private final Object oldValue;
    private final Object newValue;
    
    public StateChangeEvent(SimulationState source, String propertyName, 
                            Object oldValue, Object newValue) {
        this.source = source;
        this.propertyName = propertyName;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }
    

    public StateChangeEvent(SimulationState source) {
        this(source, "state", null, null);
    }
    
    public SimulationState getSource() {
        return source;
    }
    
    public String getPropertyName() {
        return propertyName;
    }
    
    public Object getOldValue() {
        return oldValue;
    }
    
    public Object getNewValue() {
        return newValue;
    }
    

    public boolean isPropertyChange(String propertyName) {
        return this.propertyName != null && this.propertyName.equals(propertyName);
    }
}