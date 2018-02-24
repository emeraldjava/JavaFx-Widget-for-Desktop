package com.arivan.amin.widget.network;

public class WindowsNetworkMonitor implements NetworkMonitor
{
    private WindowsNetworkMonitor ()
    {
    }
    
    public static WindowsNetworkMonitor newInstance ()
    {
        return new WindowsNetworkMonitor();
    }
    
    @Override
    public String downloadSpeed ()
    {
        return "";
    }
    
    @Override
    public String uploadSpeed ()
    {
        return "";
    }
    
    @Override
    public String ipAddress ()
    {
        return "";
    }
    
    @Override
    public void updateData ()
    {
    }
}
