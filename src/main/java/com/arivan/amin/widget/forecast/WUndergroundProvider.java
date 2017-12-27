package com.arivan.amin.widget.forecast;

import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class WUndergroundProvider implements WeatherProvider
{
    private final Logger logger = Logger.getLogger(getClass().getName());
    
    private WUndergroundProvider ()
    {
        super();
    }
    
    public static WUndergroundProvider newInstance ()
    {
        return new WUndergroundProvider();
    }
    
    @NotNull
    @Override
    public String getWeatherData ()
    {
        String data;
        try (InputStream stream = new URL("http://www.google.com").openStream();)
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
