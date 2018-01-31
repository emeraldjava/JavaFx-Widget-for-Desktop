package com.arivan.amin.widget.forecast;

import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.logging.Logger;

public class OpenWeatherMapProvider implements WeatherProvider
{
    private final Logger logger = Logger.getLogger(getClass().getName());
    private static final String WEATHER_FILE = "weather.xml";
    private static final String WEATHER_PROVIDER_URL =
            "http://api.openweathermap.org/data/2.5/forecast?" +
                    "APPID=8baf149076bcaecb58c4b8ce7403afb4&mode=xml&units=metric";
    private final GeoLocationProvider locationProvider;
    
    private OpenWeatherMapProvider ()
    {
        locationProvider = GeoLite2LocationProvider.newInstance();
    }
    
    public static OpenWeatherMapProvider newInstance ()
    {
        return new OpenWeatherMapProvider();
    }
    
    private String getWeatherProviderUrlAndLocation ()
    {
        return WEATHER_PROVIDER_URL + "&lat=" + locationProvider.latitude() + "&lon=" +
                locationProvider.longitude();
    }
    
    @Override
    public String getWeatherDataUri ()
    {
        String dataFileUri = "";
        Path path = Paths.get(WEATHER_FILE);
        try (InputStream stream = new URL(getWeatherProviderUrlAndLocation()).openStream())
        {
            String data = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
            Files.write(path, data.getBytes(StandardCharsets.UTF_8));
        }
        catch (Exception e)
        {
            logger.warning(e.getMessage());
        }
        return path.toUri().toString();
    }
}
