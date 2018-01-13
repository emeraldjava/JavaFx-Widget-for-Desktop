package com.arivan.amin.widget.cpu;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

@FunctionalInterface
public interface CpuMonitor
{
    double PERCENT = 100.0;
    
    default String getCommandOutput (List<String> command) throws IOException
    {
        try (InputStream stream = new ProcessBuilder(command).start().getInputStream())
        {
            return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
    
    double getCpuUsage ();
}
