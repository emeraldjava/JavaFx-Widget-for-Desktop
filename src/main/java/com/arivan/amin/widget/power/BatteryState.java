package com.arivan.amin.widget.power;

import com.arivan.amin.widget.SystemCommandHandler;

public interface BatteryState extends SystemCommandHandler
{
    String batteryState ();
    
    String timeToFull ();
    
    String timeToEmpty ();
    
    String percentage ();
    
    void updateData ();
}
