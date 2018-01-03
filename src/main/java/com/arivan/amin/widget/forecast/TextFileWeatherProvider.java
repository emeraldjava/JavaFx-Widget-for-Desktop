package com.arivan.amin.widget.forecast;

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
    public String getWeatherDataStream ()
    {
        Path path = Paths.get("src", "main", "resources", "WeatherData.xml");
        return path.toUri().toString();
    }
}
