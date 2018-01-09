package com.arivan.amin.widget.power;

public interface BatteryState
{
    String batteryState ();
    
    String timeToFull ();
    String timeToEmpty ();
    
    String percentage ();
    
    String currentStateIcon ();
    
    void updateData ();
}
