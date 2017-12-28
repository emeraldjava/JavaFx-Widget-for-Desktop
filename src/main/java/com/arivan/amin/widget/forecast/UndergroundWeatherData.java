package com.arivan.amin.widget.forecast;

import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public class UndergroundWeatherData implements WeatherData
{
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final WeatherProvider weatherProvider;
    private String weatherData;
    
    private UndergroundWeatherData ()
    {
        super();
        weatherProvider = UndergroundWeatherProvider.newInstance();
        updateWeatherData();
    }
    
    @NotNull
    public static UndergroundWeatherData newInstance ()
    {
        return new UndergroundWeatherData();
    }
    
    @Override
    public final void updateWeatherData ()
    {
        weatherData = weatherProvider.getWeatherData();
    }
    
    private int extractNumericData (String dataName, String delimiter)
    {
        String data = new String(weatherData);
        data = data.substring(data.indexOf(dataName) + dataName.length());
        return Integer.parseInt(data.substring(0, data.indexOf(delimiter)));
    }
    
    @Override
    public int getTemperature ()
    {
        int kelvinTemperature = extractNumericData("\"temp\":", ".");
        return UnitConverter.convertKelvinToCelsius(kelvinTemperature);
    }
    
    @Override
    public int getPressure ()
    {
        // TODO: 12/27/17 figure out pressure unit
        return extractNumericData("\"pressure\":", ".");
    }
    
    @Override
    public int getHumidity ()
    {
        return extractNumericData("\"humidity\":", ",");
    }
    
    @Override
    public String getWeather ()
    {
        String data = new String(weatherData);
        data = data.substring(data.indexOf("weather"));
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
    
    @Override
    public String toString ()
    {
        return "UndergroundWeatherData{" + "weatherProvider=" + weatherProvider +
                ", weatherData='" + weatherData + '\'' + '}';
    }
}
