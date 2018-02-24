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
            String totalOutput =
                    getCommandOutput(List.of("wmic", "OS", "get", "TotalVisibleMemorySize"));
            String totalDelimiter = "TotalVisibleMemorySize";
            totalOutput = totalOutput
                    .substring(totalOutput.indexOf(totalDelimiter) + totalDelimiter.length())
                    .trim();
            double totalMemory = Double.parseDouble(totalOutput);
            String freeOutput =
                    getCommandOutput(List.of("wmic", "OS", "get", "FreePhysicalMemory"));
            String freeDelimiter = "FreePhysicalMemory";
            freeOutput = freeOutput
                    .substring(freeOutput.indexOf(freeDelimiter) + totalDelimiter.length()).trim();
            double freeMemory = Double.parseDouble(freeOutput);
            return freeMemory / totalMemory;
        }
        catch (Exception e)
        {
            logger.warning(e.getMessage());
        }
        return 0;
    }
}
