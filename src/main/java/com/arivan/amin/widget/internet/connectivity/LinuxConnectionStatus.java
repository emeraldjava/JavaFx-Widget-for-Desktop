package com.arivan.amin.widget.internet.connectivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class LinuxConnectionStatus implements ConnectionStatus
{
    private static final String[] PING_COMMAND = { "ping", "www.google.com", "-c", "1" };
    private static final String HOSTNAME = "www.google.com";
    private static final int HTTP_PORT = 80;
    private final Logger logger = Logger.getLogger(getClass().getName());
    private String commandData;
    
    private LinuxConnectionStatus ()
    {
    }
    
    public static LinuxConnectionStatus newInstance ()
    {
        return new LinuxConnectionStatus();
    }
    
    private String getCommandOutput (String... command)
    {
        try (InputStream stream = new ProcessBuilder(command).start().getInputStream())
        {
            return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        }
        catch (IOException e)
        {
            logger.warning(e.getMessage());
            return "";
        }
    }
    
    @Override
    public boolean isConnected ()
    {
        try (Socket socket = new Socket())
        {
            InetSocketAddress socketAddress = new InetSocketAddress(HOSTNAME, HTTP_PORT);
            socket.connect(socketAddress, 3000);
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
        catch (Exception ignored)
        {
            return 0;
        }
    }
    
    @Override
    public void updateData ()
    {
        commandData = getCommandOutput(PING_COMMAND);
    }
}
