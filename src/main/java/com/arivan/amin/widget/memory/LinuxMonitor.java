package com.arivan.amin.widget.memory;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class LinuxMonitor implements Monitor
{
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final List<String> topCommand = List.of("top", "-d", "0.01", "-bn", "2");
    private String[] data;
    
    private LinuxMonitor ()
    {
        super();
    }
    
    @NotNull
    public static LinuxMonitor newInstance ()
    {
        return new LinuxMonitor();
    }
    
    @Override
    public void updateCommandData ()
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
    public double getCommandData ()
    {
        updateCommandData();
        return (PERCENT * Double.parseDouble(data[2])) / Double.parseDouble(data[0]) / PERCENT;
    }
    
    @Override
    public String removeUnnecessaryData (String output)
    {
        String mem = output.substring(output.indexOf("KiB Mem"));
        mem = mem.substring(0, mem.indexOf("avail Mem"));
        mem = NON_DIGITS.matcher(mem).replaceAll("");
        return mem;
    }
    
    @Override
    public String toString ()
    {
        return "LinuxMonitor{" + "topCommand=" + topCommand + ", weatherData=" + Arrays.toString(data) +
                '}';
    }
}
