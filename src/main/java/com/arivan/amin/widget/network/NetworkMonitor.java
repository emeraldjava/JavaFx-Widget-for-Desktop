package com.arivan.amin.widget.network;

import com.arivan.amin.widget.SystemCommandHandler;

import java.util.regex.Pattern;

public interface NetworkMonitor extends SystemCommandHandler
{
    Pattern EXTRA_SPACE = Pattern.compile(" {2,}");
    int BYTES_THOUSAND_UNIT = 1024;

    String downloadSpeed ();
    
    String uploadSpeed ();
    
    String ipAddress ();

    void updateData ();
}
