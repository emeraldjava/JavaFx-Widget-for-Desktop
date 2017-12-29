package com.arivan.amin.widget.forecast;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
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
    
    @NotNull
    @Override
    public String getWeatherData ()
    {
        StringBuilder data = new StringBuilder(20);
        Path path = Paths.get("/home/arivan/IdeaProjects/Widget/src/main/resources/WeatherSampleDataOriginal.txt");
        try
        {
            List<String> lines = Files.readAllLines(path);
            lines.forEach(data::append);
        }
        catch (IOException e)
        {
            logger.warning(e.getMessage());
        }
        return data.toString();
    }
}
