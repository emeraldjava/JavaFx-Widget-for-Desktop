package com.arivan.amin.widget.wireless;

public interface WirelessMonitor
{
    // todo show signal strength as progress bar
    boolean isConnected ();
    
    String wirelessName ();
    
    int linkQuality ();
    
    int signalLevel ();
    
    void updateData ();
}
