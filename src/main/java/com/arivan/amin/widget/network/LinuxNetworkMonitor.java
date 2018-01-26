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
    private long previousDownload;
    private long previousUpload;
    
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
        String dataCopy = new String(data);
        dataCopy = dataCopy.replace("\n", " ");
        dataCopy = dataCopy.substring(dataCopy.indexOf("wlo"));
        String delimiter = "mcast";
        dataCopy = dataCopy.substring(dataCopy.indexOf(delimiter) + delimiter.length(),
                dataCopy.indexOf("TX"));
        dataCopy = EXTRA_SPACE.matcher(dataCopy).replaceAll(":");
        String[] split = dataCopy.split(":");
        long download = Long.parseLong(split[1]);
        long bytes = download - previousDownload;
        previousDownload = download;
        return bytesIntoHumanReadable(bytes);
    }
    
    @Override
    public String uploadSpeed ()
    {
        String dataCopy = new String(data);
        dataCopy = dataCopy.replace("\n", " ");
        dataCopy = dataCopy.substring(dataCopy.indexOf("wlo"));
        String delimiter = "collsns";
        dataCopy = dataCopy.substring(dataCopy.indexOf(delimiter) + delimiter.length());
        dataCopy = EXTRA_SPACE.matcher(dataCopy).replaceAll(":");
        String[] split = dataCopy.split(":");
        long upload = Long.parseLong(split[1]);
        long bytes = upload - previousUpload;
        previousUpload = upload;
        return bytesIntoHumanReadable(bytes);
    }
    
    private String bytesIntoHumanReadable (long bytes)
    {
        long kilobyte = 1024;
        long megabyte = kilobyte * 1024;
        long gigabyte = megabyte * 1024;
        long terabyte = gigabyte * 1024;
        if ((bytes >= 0) && (bytes < kilobyte))
        {
            return "0 KB";
        }
        else if ((bytes >= kilobyte) && (bytes < megabyte))
        {
            return (bytes / kilobyte) + " KB";
        }
        else if ((bytes >= megabyte) && (bytes < gigabyte))
        {
            return (bytes / megabyte) + " MB";
        }
        else if ((bytes >= gigabyte) && (bytes < terabyte))
        {
            return (bytes / gigabyte) + " GB";
        }
        else if (bytes >= terabyte)
        {
            return (bytes / terabyte) + " TB";
        }
        else
        {
            return bytes + " Bytes";
        }
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
