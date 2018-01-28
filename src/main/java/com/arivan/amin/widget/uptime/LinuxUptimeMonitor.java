package com.arivan.amin.widget.uptime;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class LinuxUptimeMonitor implements UptimeMonitor
{
    private final Logger logger = Logger.getLogger(getClass().getName());
    
    private LinuxUptimeMonitor ()
    {
    }
    
    public static LinuxUptimeMonitor newInstance ()
    {
        return new LinuxUptimeMonitor();
    }
    
    @Override
    public String systemUptime ()
    {
        try
        {
            String uptime = getCommandOutput(List.of("uptime"));
            String delimiter = "up";
            uptime = uptime.substring(uptime.indexOf(delimiter) + delimiter.length(),
                    uptime.indexOf(','));
            return uptime;
        }
        catch (IOException e)
        {
            logger.warning(e.getMessage());
            return "";
        }
    }
}
