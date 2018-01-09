package com.arivan.amin.widget.power;

public interface BatteryState
{
    boolean isBatteryRechargeable ();
    
    String batteryState ();
    
    String timeToFull ();
    
    String percentage ();
    
    String currentStateIcon ();
}
