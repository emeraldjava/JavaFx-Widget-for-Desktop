package com.arivan.amin.widget.forecast;

import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public class WUndergroundProcessor implements WeatherProcessor
{
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final WeatherProvider weatherProvider;
    private String data;
    
    private WUndergroundProcessor ()
    {
        super();
        weatherProvider = WUndergroundProvider.newInstance();
        updateWeatherData();
    }
    
    @NotNull
    public static WUndergroundProcessor newInstance ()
    {
        return new WUndergroundProcessor();
    }
    
    @Override
    public final void updateWeatherData ()
    {
        data = weatherProvider.getWeatherData();
    }
    
    @Override
    public int getTemperature ()
    {
        return 0;
    }
    
    @Override
    public int getPressure ()
    {
        return 0;
    }
    
    @Override
    public int getHumidity ()
    {
        return 0;
    }
    
    @Override
    public String getWeather ()
    {
        return null;
    }
    
    @Override
    public String getWeatherDescription ()
    {
        return null;
    }
    
    @Override
    public int getCloudsRate ()
    {
        return 0;
    }
    
    @Override
    public int getWindsSpeed ()
    {
        return 0;
    }
    
    @Override
    public int getWindsDirectionDegree ()
    {
        return 0;
    }
    
    @Override
    public String getSecondDayWeather ()
    {
        return null;
    }
    
    @Override
    public int getSecondDayMaxTemperature ()
    {
        return 0;
    }
    
    @Override
    public int getSecondMinTemperature ()
    {
        return 0;
    }
    
    @Override
    public String getThirdDayWeather ()
    {
        return null;
    }
    
    @Override
    public int getThirdDayMaxTemperature ()
    {
        return 0;
    }
    
    @Override
    public int getThirdMinTemperature ()
    {
        return 0;
    }
    
    @Override
    public String getFourthDayWeather ()
    {
        return null;
    }
    
    @Override
    public int getFourthDayMaxTemperature ()
    {
        return 0;
    }
    
    @Override
    public int getFourthMinTemperature ()
    {
        return 0;
    }
}
