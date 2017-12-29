package com.arivan.amin.widget.forecast;

import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
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
        String data;
        try (InputStream stream = new URL("http://api.openweathermap.org/data/2.5/forecast?id=98463&APPID=8baf149076bcaecb58c4b8ce7403afb4").openStream();)
        {
            data = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        }
        catch (Exception e)
        {
            data = "";
            logger.warning(e.getMessage());
        }
        return data;
    }
}
