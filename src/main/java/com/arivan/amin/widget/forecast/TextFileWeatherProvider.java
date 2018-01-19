package com.arivan.amin.widget.forecast;

import java.nio.file.*;
import java.util.logging.Logger;

public class TextFileWeatherProvider implements WeatherProvider
{
    private final Logger logger = Logger.getLogger(getClass().getName());
    
    private TextFileWeatherProvider ()
    {
    }
    
    public static TextFileWeatherProvider newInstance ()
    {
        return new TextFileWeatherProvider();
    }
    
    @Override
    public String getWeatherDataUri ()
    {
        Path path = Paths.get("WeatherData.xml");
        return path.toUri().toString();
    }
}
