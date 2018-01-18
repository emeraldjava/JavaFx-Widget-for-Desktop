package com.arivan.amin.widget.memory;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class LinuxMemoryMonitor implements MemoryMonitor
{
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final List<String> topCommand = List.of("top", "-d", "0.01", "-bn", "2");
    private String[] data;
    
    private LinuxMemoryMonitor ()
    {
    }
    
    @NotNull
    public static LinuxMemoryMonitor newInstance ()
    {
        return new LinuxMemoryMonitor();
    }
    
    @Override
    public void updateData ()
    {
        try
        {
            data = removeUnnecessaryData(getCommandOutput(topCommand)).split(",");
        }
        catch (IOException e)
        {
            logger.warning(e.getMessage());
        }
    }
    
    @Override
    public double getUsedMemory ()
    {
        return computeUsedMemoryPercentage();
    }
    
    private double computeUsedMemoryPercentage ()
    {
        return (PERCENT * Double.parseDouble(data[2])) / Double.parseDouble(data[0]) / PERCENT;
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
        return "LinuxCpuMonitor{" + "topCommand=" + topCommand + ", weatherData=" + Arrays.toString(data) +
                '}';
    }
}
