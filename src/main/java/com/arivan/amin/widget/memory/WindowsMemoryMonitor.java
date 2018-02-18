package com.arivan.amin.widget.memory;

import java.util.List;
import java.util.logging.Logger;

public class WindowsMemoryMonitor implements MemoryMonitor
{
    private final Logger logger = Logger.getLogger(getClass().getName());
    
    private WindowsMemoryMonitor ()
    {
    }
    
    public static WindowsMemoryMonitor newInstance ()
    {
        return new WindowsMemoryMonitor();
    }
    
    @Override
    public double getUsedMemory ()
    {
        try
        {
            double totalMemory = Long.parseLong(getCommandOutput(
                    List.of("wmic", "computersystem", "get", "TotalPhysicalMemory")));
            double freeMemory = Long.parseLong(
                    getCommandOutput(List.of("wmic", "OS", "get", "FreePhysicalMemory")));
            return (100 - ((100 * freeMemory) / totalMemory)) / 100;
        }
        catch (Exception e)
        {
            logger.warning(e.getMessage());
        }
        return 0;
    }
}
