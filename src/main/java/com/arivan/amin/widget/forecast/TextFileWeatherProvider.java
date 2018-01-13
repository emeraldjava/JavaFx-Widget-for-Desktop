package com.arivan.amin.widget.forecast;

import org.jetbrains.annotations.NotNull;

import java.nio.file.*;
import java.util.logging.Logger;

public class TextFileWeatherProvider implements WeatherProvider
{
    private final Logger logger = Logger.getLogger(getClass().getName());
    
    private TextFileWeatherProvider ()
    {
    }
    
    @NotNull
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
