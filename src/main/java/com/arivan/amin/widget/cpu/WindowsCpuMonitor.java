package com.arivan.amin.widget.cpu;

import java.util.List;
import java.util.logging.Logger;

public class WindowsCpuMonitor implements CpuMonitor
{
    private final Logger logger = Logger.getLogger(getClass().getName());
    
    private WindowsCpuMonitor ()
    {
    }
    
    public static WindowsCpuMonitor newInstance ()
    {
        return new WindowsCpuMonitor();
    }
    
    @Override
    public double getCpuUsage ()
    {
        try
        {
            String output = getCommandOutput(List.of("wmic", "cpu", "get", "loadpercentage"));
            String loadPercentage = "LoadPercentage";
            output = output.substring(output.indexOf(loadPercentage) + loadPercentage.length())
                    .trim();
            return Double.parseDouble(output) / 100;
        }
        catch (Exception e)
        {
            logger.warning(e.getMessage());
            return 0;
        }
    }
}
