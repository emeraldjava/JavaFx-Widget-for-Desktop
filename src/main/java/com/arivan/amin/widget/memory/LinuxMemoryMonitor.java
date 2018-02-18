package com.arivan.amin.widget.memory;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class LinuxMemoryMonitor implements MemoryMonitor
{
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final List<String> topCommand = List.of("top", "-d", "0.01", "-bn", "2");
    
    private LinuxMemoryMonitor ()
    {
    }
    
    public static LinuxMemoryMonitor newInstance ()
    {
        return new LinuxMemoryMonitor();
    }
    
    @Override
    public double getUsedMemory ()
    {
        return computeUsedMemoryPercentage();
    }
    
    private double computeUsedMemoryPercentage ()
    {
        try
        {
            String[] data = removeUnnecessaryData(getCommandOutput(topCommand)).split(",");
            return (PERCENT * Double.parseDouble(data[2])) / Double.parseDouble(data[0]) / PERCENT;
        }
        catch (IOException e)
        {
            logger.warning(e.getMessage());
            return 0;
        }
    }
    
    private String removeUnnecessaryData (String output)
    {
        String mem = output.substring(output.indexOf("KiB Mem"));
        mem = mem.substring(0, mem.indexOf("avail Mem"));
        mem = NON_DIGITS.matcher(mem).replaceAll("");
        return mem;
    }
    
    @Override
    public String toString ()
    {
        return "LinuxMemoryMonitor{" + "topCommand=" + topCommand + '}';
    }
}
