package com.arivan.amin.widget.wireless;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public interface WirelessMonitor
{
    // todo show signal strength as progress bar
    default String getCommandOutput (List<String> command) throws IOException
    {
        try (InputStream stream = new ProcessBuilder(command).start().getInputStream())
        {
            return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
    
    boolean isConnected ();
    
    String wirelessName ();
    
    int linkQuality ();
    
    double signalLevel ();
    
    void updateData ();
}
