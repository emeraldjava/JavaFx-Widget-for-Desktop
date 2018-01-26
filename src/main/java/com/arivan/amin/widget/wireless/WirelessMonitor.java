package com.arivan.amin.widget.wireless;

import com.arivan.amin.widget.SystemCommandHandler;

public interface WirelessMonitor extends SystemCommandHandler
{
    boolean isConnected ();
    
    String wirelessName ();
    
    int linkQuality ();
    
    double signalLevel ();
    
    void updateData ();
}
