package com.arivan.amin.widget.network;

import java.util.logging.Logger;

public class LinuxNetworkMonitor implements NetworkMonitor
{
    private final Logger logger = Logger.getLogger(getClass().getName());
    
    private LinuxNetworkMonitor ()
    {
    }
    
    public static LinuxNetworkMonitor newInstance ()
    {
        return new LinuxNetworkMonitor();
    }
    
    @Override
    public String downloadSpeed ()
    {
        return "3 mbps";
    }
    
    @Override
    public String uploadSpeed ()
    {
        return "1 mbps";
    }
    
    @Override
    public void updateData ()
    {
    }
}
