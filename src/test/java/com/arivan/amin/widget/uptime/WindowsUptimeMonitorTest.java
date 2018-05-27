package com.arivan.amin.widget.uptime;

import org.junit.Test;

public class WindowsUptimeMonitorTest
{
    
    @Test
    public void systemUptime ()
    {
        System.out.println(WindowsUptimeMonitor.newInstance().systemUptime());
    }
}
