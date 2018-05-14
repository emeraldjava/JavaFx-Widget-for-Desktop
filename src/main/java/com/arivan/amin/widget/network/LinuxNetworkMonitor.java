package com.arivan.amin.widget.network;

import com.arivan.amin.widget.wireless.LinuxWirelessMonitor;
import com.arivan.amin.widget.wireless.WirelessMonitor;

import java.util.List;
import java.util.logging.Logger;

public class LinuxNetworkMonitor implements NetworkMonitor
{
    private final Logger logger = Logger.getLogger(getClass().getName());
    private static final List<String> NETWORK_COMMAND = List.of("ip", "-s", "link");
    private final WirelessMonitor wirelessMonitor;
    private long previousDownload;
    private long previousUpload;
    private String data;
    
    private LinuxNetworkMonitor ()
    {
        wirelessMonitor = LinuxWirelessMonitor.newInstance();
        updateData();
    }
    
    public static LinuxNetworkMonitor newInstance ()
    {
        return new LinuxNetworkMonitor();
    }
    
    @Override
    public String downloadSpeed ()
    {
        try
        {
            String dataCopy = new String(data);
            dataCopy = removeExtraOutput(dataCopy);
            dataCopy = extractDownloadNumbers(dataCopy, "mcast", "TX");
            long bytes = getBytesAndSetPrevDownload(removeExtraSpaceAndSplit(dataCopy)[1]);
            return bytesIntoHumanReadable(bytes);
        }
        catch (Exception e)
        {
            logger.warning(e.getMessage());
            return "";
        }
    }
    
    private long getBytesAndSetPrevDownload (String s)
    {
        long download = Long.parseLong(s);
        long bytes = download - previousDownload;
        previousDownload = download;
        return bytes;
    }
    
    private String[] removeExtraSpaceAndSplit (String dataCopy)
    {
        dataCopy = EXTRA_SPACE.matcher(dataCopy).replaceAll(" ");
        return dataCopy.split(" ");
    }
    
    private String extractDownloadNumbers (String dataCopy, String mcast, String tx)
    {
        return dataCopy.substring(dataCopy.indexOf(mcast) + mcast.length(), dataCopy.indexOf(tx));
    }
    
    private String removeExtraOutput (String dataCopy)
    {
        dataCopy = dataCopy.replace("\n", " ");
        dataCopy = dataCopy.substring(dataCopy.indexOf("wlo"));
        return dataCopy;
    }
    
    @Override
    public String uploadSpeed ()
    {
        try
        {
            String dataCopy = new String(data);
            dataCopy = removeExtraOutput(dataCopy);
            String delimiter = "collsns";
            dataCopy = dataCopy.substring(dataCopy.indexOf(delimiter) + delimiter.length());
            long upload = Long.parseLong(removeExtraSpaceAndSplit(dataCopy)[1]);
            long bytes = getBytesAndSetPrevUpload(upload);
            return bytesIntoHumanReadable(bytes);
        }
        catch (Exception e)
        {
            logger.warning(e.getMessage());
            return "";
        }
    }
    
    private long getBytesAndSetPrevUpload (long upload)
    {
        long bytes = upload - previousUpload;
        previousUpload = upload;
        return bytes;
    }
    
    @Override
    public String ipAddress ()
    {
        try
        {
            if (wirelessMonitor.isConnected())
            {
                String output = getCommandOutput(List.of("ifconfig"));
                output = output.substring(output.indexOf("wlo"));
                output = extractDownloadNumbers(output, "inet", "netmask");
                return output;
            }
        }
        catch (Exception e)
        {
            logger.warning(e.getMessage());
        }
        return "";
    }

    @SuppressWarnings ({ "OverlyLongMethod", "IfStatementWithTooManyBranches" })
    public static String bytesIntoHumanReadable (long bytes)
    {
        long kilobyte = BYTES_THOUSAND_UNIT;
        long megabyte = kilobyte * BYTES_THOUSAND_UNIT;
        long gigabyte = megabyte * BYTES_THOUSAND_UNIT;
        long terabyte = gigabyte * BYTES_THOUSAND_UNIT;
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
            wirelessMonitor.updateData();
            data = getCommandOutput(NETWORK_COMMAND);
        }
        catch (Exception e)
        {
            logger.warning(e.getMessage());
            data = "";
        }
    }
    
    @Override
    public String toString ()
    {
        return "LinuxNetworkMonitor{" + "data='" + data + '\'' + ", previousDownload=" +
                previousDownload + ", previousUpload=" + previousUpload + ", wirelessMonitor=" +
                wirelessMonitor + '}';
    }
}
