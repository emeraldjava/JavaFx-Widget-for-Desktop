package com.arivan.amin.widget.cpu;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.regex.Pattern;

public interface Monitor
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
    
    void updateCommandData ();
    
    double getCommandData ();
    
    String removeUnnecessaryData (String output);
}
