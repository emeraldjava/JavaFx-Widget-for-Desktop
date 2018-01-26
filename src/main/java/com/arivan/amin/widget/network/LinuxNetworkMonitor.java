package com.arivan.amin.widget.network;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class LinuxNetworkMonitor implements NetworkMonitor
{
    private static final List<String> NETWORK_COMMAND = List.of("ip", "-s", "link");
    private static final Pattern EXTRA_SPACE = Pattern.compile(" {2,}");
    private final Logger logger = Logger.getLogger(getClass().getName());
    private String data;
    
    private LinuxNetworkMonitor ()
    {
        updateData();
    }
    
    public static LinuxNetworkMonitor newInstance ()
    {
        return new LinuxNetworkMonitor();
    }
    
    @Override
    public String downloadSpeed ()
    {
        data = data.substring(data.indexOf("wlo"));
        String mcast = "mcast";
        data = data.substring(data.indexOf(mcast) + mcast.length(), data.indexOf("TX"));
        data = EXTRA_SPACE.matcher(data).replaceAll(" ");
        // System.out.println(data);
        return "3 mbps";
    }
    
    @Override
    public String uploadSpeed ()
    {
        return "1 mbps";
    }
    
    @Override
    public void updateData ()
    {
        try
        {
            data = getCommandOutput(NETWORK_COMMAND);
        }
        catch (IOException e)
        {
            logger.warning(e.getMessage());
            data = "";
        }
    }
    
    @Override
    public String toString ()
    {
        return "LinuxNetworkMonitor{" + "data='" + data + '\'' + '}';
    }
}
