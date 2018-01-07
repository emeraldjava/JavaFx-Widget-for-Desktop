package com.arivan.amin.widget.applications.usage;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class LinuxUsageMonitorTest
{
    UsageMonitor monitor;
    
    @Before
    public void setUp () throws Exception
    {
        monitor = LinuxUsageMonitor.newInstance();
    }
    
    @Test
    public void getProcessesUsage ()
    {
        List<UsageItem> usage = monitor.getProcessesUsage();
    }
}
