package com.arivan.amin.widget.uptime;

public class WindowsUptimeMonitor implements UptimeMonitor
{
    private WindowsUptimeMonitor ()
    {
    }
    
    public static WindowsUptimeMonitor newInstance ()
    {
        return new WindowsUptimeMonitor();
    }
    
    @Override
    public String systemUptime ()
    {
        return "";
    }
}
