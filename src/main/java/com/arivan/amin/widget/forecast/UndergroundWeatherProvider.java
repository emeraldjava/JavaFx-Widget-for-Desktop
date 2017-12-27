package com.arivan.amin.widget.forecast;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.logging.Logger;

public class UndergroundWeatherProvider implements WeatherProvider
{
    private final Logger logger = Logger.getLogger(getClass().getName());
    
    private UndergroundWeatherProvider ()
    {
        super();
    }
    
    public static UndergroundWeatherProvider newInstance ()
    {
        return new UndergroundWeatherProvider();
    }
    
    @NotNull
    @Override
    public String getWeatherData ()
    {
        StringBuilder data = new StringBuilder(20);
        // try (InputStream stream = new URL("http://www.google.com").openStream();)
        // {
        //     weatherData = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        // }
        // catch (Exception e)
        // {
        //     weatherData = "";
        //     logger.warning(e.getMessage());
        // }
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
