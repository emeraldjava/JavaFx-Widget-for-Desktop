package com.arivan.amin.widget.network;

import com.arivan.amin.widget.SystemCommandHandler;

public interface NetworkMonitor extends SystemCommandHandler
{
    String downloadSpeed ();
    
    String uploadSpeed ();
    
    String ipAddress ();
    
    void updateData ();
}
