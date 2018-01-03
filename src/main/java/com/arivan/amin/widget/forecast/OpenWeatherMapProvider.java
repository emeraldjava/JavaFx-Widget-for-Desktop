package com.arivan.amin.widget.forecast;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.logging.Logger;

public class OpenWeatherMapProvider implements WeatherProvider
{
    private static final String WEATHER_FILE = "weather.xml";
    private static final String WEATHER_PROVIDER_URL =
            "http://api.openweathermap.org/data/2.5/forecast?id=98463&APPID=8baf149076bcaecb58c4b8ce7403afb4&mode=xml&units=metric";
    private final Logger logger = Logger.getLogger(getClass().getName());
    
    private OpenWeatherMapProvider ()
    {
        super();
    }
    
    public static OpenWeatherMapProvider newInstance ()
    {
        return new OpenWeatherMapProvider();
    }
    
    @Override
    public String getWeatherDataStream ()
    {
        String dataFileUri = "";
        Path path = Paths.get(WEATHER_FILE);
        try (InputStream stream = new URL(WEATHER_PROVIDER_URL).openStream())
        {
            String data = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
            Files.write(path, data.getBytes(StandardCharsets.UTF_8));
        }
        catch (MalformedURLException ignored)
        {
            // should never happen since the url is a constant defined above
        }
        catch (IOException e)
        {
            logger.warning(e.getMessage());
        }
        return path.toUri().toString();
    }
}
