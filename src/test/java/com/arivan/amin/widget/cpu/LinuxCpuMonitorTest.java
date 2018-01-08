package com.arivan.amin.widget.cpu;

import org.junit.Before;
import org.junit.Test;

public class LinuxCpuMonitorTest
{
    CpuMonitor monitor;
    
    @Before
    public void setUp () throws Exception
    {
        monitor = LinuxCpuMonitor.newInstance();
    }
    
    @Test
    public void getCpuUsage ()
    {
        System.out.println(monitor.getCpuUsage());
    }
}
