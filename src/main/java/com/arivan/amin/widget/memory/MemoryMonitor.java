package com.arivan.amin.widget.memory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.regex.Pattern;

public interface MemoryMonitor
{
    double PERCENT = 100.0;
    
    Pattern NON_DIGITS = Pattern.compile("[a-z :/A-Z]+");
    
    default String getCommandOutput (List<String> command) throws IOException
    {
        try (InputStream stream = new ProcessBuilder(command).start().getInputStream())
        {
            return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
    
    void updateData ();
    
    double getUsedMemory ();
}
