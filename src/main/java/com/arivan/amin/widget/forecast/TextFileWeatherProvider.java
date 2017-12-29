package com.arivan.amin.widget.forecast;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.logging.Logger;

public class TextFileWeatherProvider implements WeatherProvider
{
    private final Logger logger = Logger.getLogger(getClass().getName());
    
    private TextFileWeatherProvider ()
    {
        super();
    }
    
    public static TextFileWeatherProvider newInstance ()
    {
        return new TextFileWeatherProvider();
    }
    
    @Override
    public InputStream getWeatherDataStream ()
    {
        InputStream stream = null;
        StringBuilder data = new StringBuilder(20);
        Path path = Paths.get("src", "main", "resources", "WeatherData.xml");
        try
        {
            stream = Files.newInputStream(path);
        }
        catch (IOException e)
        {
            logger.warning(e.getMessage());
        }
        return stream;
    }
}
