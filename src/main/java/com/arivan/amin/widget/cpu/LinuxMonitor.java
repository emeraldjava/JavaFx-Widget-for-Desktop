package com.arivan.amin.widget.cpu;

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
        return (PERCENT - Double.parseDouble(data[3])) / PERCENT;
    }
    
    @Override
    public String removeUnnecessaryData (String output)
    {
        output = output.substring(output.lastIndexOf("Cpu"));
        output = output.substring(0, output.indexOf("avail Mem")).trim();
        output = NON_DIGITS.matcher(output).replaceAll("");
        return output;
    }
    
    @Override
    public String toString ()
    {
        return "LinuxMonitor{" + "topCommand=" + topCommand + ", data=" + Arrays.toString(data) +
                '}';
    }
}
