package com.arivan.amin.widget.network;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class WindowsNetworkMonitor implements NetworkMonitor
{
    private final Logger logger = Logger.getLogger(getClass().getName());
    private static final List<String> NETWORK_COMMAND = List.of("netstat", "-e");
    private long previousDownload;
    private long previousUpload;
    private String data;

    private WindowsNetworkMonitor()
    {
        updateData();
    }

    public static WindowsNetworkMonitor newInstance()
    {
        return new WindowsNetworkMonitor();
    }

    @Override
    public String downloadSpeed()
    {
        String download = LinuxNetworkMonitor.bytesIntoHumanReadable(getBytesAndSetPrevDownload(getBytes(1)));
        return String.valueOf(download);
    }

    private String getBytes(int index)
    {
        String dataCopy = new String(data);
        dataCopy = removeExtraOutput(dataCopy);
        String[] bytes = dataCopy.split(" ");
        return bytes[index];
    }

    private String removeExtraOutput(String output)
    {
        output = output.substring(output.indexOf("Bytes"));
        output = output.substring(0, output.indexOf("Unicast packets"));
        output = EXTRA_SPACE.matcher(output).replaceAll(" ").trim();
        return output;
    }

    @Override
    public String uploadSpeed()
    {
        String upload = LinuxNetworkMonitor.bytesIntoHumanReadable(getBytesAndSetPrevUpload(getBytes(2)));
        return String.valueOf(upload);
    }

    @Override
    public String ipAddress()
    {
        return "102";
    }

    private long getBytesAndSetPrevDownload(String s)
    {
        long download = Long.parseLong(s);
        long bytes = download - previousDownload;
        previousDownload = download;
        return bytes;
    }

    private long getBytesAndSetPrevUpload(String uploadStr)
    {
        long upload = Long.parseLong(uploadStr);
        long bytes = upload - previousUpload;
        previousUpload = upload;
        return bytes;
    }

    @Override
    public void updateData()
    {
        try
        {
            data = getCommandOutput(NETWORK_COMMAND);
        } catch (Exception e)
        {
            logger.warning(e.getMessage());
        }
    }
}
