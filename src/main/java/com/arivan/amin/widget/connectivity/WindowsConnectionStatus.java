package com.arivan.amin.widget.connectivity;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;
import java.util.logging.Logger;

public class WindowsConnectionStatus implements ConnectionStatus
{
    private final Logger logger = Logger.getLogger(getClass().getName());
    private static final List<String> PING_COMMAND = List.of("ping", "www.google.com", "-n", "1");
    private static final String HOSTNAME = "www.google.com";
    private static final int HTTP_PORT = 80;
    private static final int TIMEOUT_IN_MILLISECONDS = 500;
    private String commandData;
    
    private WindowsConnectionStatus ()
    {
    }
    
    public static WindowsConnectionStatus newInstance ()
    {
        return new WindowsConnectionStatus();
    }
    
    @Override
    public boolean isConnected ()
    {
        try (Socket socket = new Socket())
        {
            socket.connect(new InetSocketAddress(HOSTNAME, HTTP_PORT), TIMEOUT_IN_MILLISECONDS);
            return true;
        }
        catch (Exception ignored)
        {
            return false;
        }
    }
    
    @Override
    public double pingTime ()
    {
        try
        {
            String delimiter = "time=";
            commandData =
                    commandData.substring(commandData.indexOf(delimiter) + delimiter.length());
            commandData = commandData.substring(0, commandData.indexOf("ms")).trim();
            return Double.parseDouble(commandData);
        }
        catch (Exception e)
        {
            logger.warning(e.getMessage());
            return 0;
        }
    }
    
    @Override
    public void updateData ()
    {
        try
        {
            commandData = getCommandOutput(PING_COMMAND);
        }
        catch (IOException e)
        {
            logger.warning(e.getMessage());
        }
    }
    
    @Override
    public String toString ()
    {
        return "WindowsConnectionStatus{" + "commandData='" + commandData + '\'' + '}';
    }
}
