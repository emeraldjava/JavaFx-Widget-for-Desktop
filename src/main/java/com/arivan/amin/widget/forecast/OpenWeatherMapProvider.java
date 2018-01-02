package com.arivan.amin.widget.forecast;

import java.io.InputStream;
import java.net.URL;
import java.util.logging.Logger;

public class OpenWeatherMapProvider implements WeatherProvider
{
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
    public InputStream getWeatherDataStream ()
    {
        InputStream stream = null;
        try
        {
            stream =
                    new URL("http://api.openweathermap.org/data/2.5/forecast?id=98463&APPID=8baf149076bcaecb58c4b8ce7403afb4&mode=xml&units=metric")
                            .openStream();
        }
        catch (Exception e)
        {
            logger.warning(e.getMessage());
        }
        return stream;
    }
}
