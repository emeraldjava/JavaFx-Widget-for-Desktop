package com.arivan.amin.widget.forecast;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.logging.Logger;

public class UndergroundWeatherData implements WeatherData
{
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final WeatherProvider weatherProvider;
    private String weatherData;
    private final List<String> directionsList =
            List.of("north", "north east", "east", "south east", "south", "south west", "west",
                    "north west", "north");
    
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
    
    private int extractNumericData (String clonedData, String dataName, String delimiter)
    {
        String escapedDataName = '"' + dataName + "\":";
        String data = clonedData
                .substring(clonedData.indexOf(escapedDataName) + escapedDataName.length());
        return Integer.parseInt(data.substring(0, data.indexOf(delimiter)));
    }
    
    private static String extractLiteralData (String clonedData, String propertyName)
    {
        String escapedName = '"' + propertyName + "\":";
        String condition =
                clonedData.substring(clonedData.indexOf(escapedName) + escapedName.length());
        condition = condition.substring(condition.indexOf('"') + 1);
        condition = condition.substring(0, condition.indexOf('"'));
        return condition;
    }
    
    @Override
    public int getTemperature ()
    {
        String clonedData = new String(weatherData);
        int kelvinTemperature = extractNumericData(clonedData, "temp", ".");
        return UnitConverter.convertKelvinToCelsius(kelvinTemperature);
    }
    
    @Override
    public int getPressure ()
    {
        // TODO: 12/27/17 figure out pressure unit
        // TODO: 12/27/17 create unit test for weather data
        String clonedData = new String(weatherData);
        return extractNumericData(clonedData, "pressure", ".");
    }
    
    @Override
    public int getHumidity ()
    {
        String clonedData = new String(weatherData);
        return extractNumericData(clonedData, "humidity", ",");
    }
    
    @Override
    public String getWeather ()
    {
        String clonedData = new String(weatherData);
        clonedData = clonedData.substring(clonedData.indexOf("\"weather\""));
        return extractLiteralData(clonedData, "main");
    }
    
    @Override
    public String getWeatherDescription ()
    {
        String clonedData = new String(weatherData);
        clonedData = clonedData.substring(clonedData.indexOf("\"weather\""));
        return extractLiteralData(clonedData, "description");
    }
    
    @Override
    public int getCloudsRate ()
    {
        String clonedData = new String(weatherData);
        clonedData = clonedData.substring(clonedData.indexOf("\"clouds\""));
        return extractNumericData(clonedData, "all", "}");
    }
    
    @Override
    public int getWindsSpeed ()
    {
        // TODO: 12/27/17 figure out is wind speed unit in km or miles
        String clonedData = new String(weatherData);
        clonedData = clonedData.substring(clonedData.indexOf("\"wind\""));
        return extractNumericData(clonedData, "speed", ".");
    }
    
    @Override
    public String getWindsDirection ()
    {
        String clonedData = new String(weatherData);
        clonedData = clonedData.substring(clonedData.indexOf("\"wind\""));
        return translateWindDirectionDegree(extractNumericData(clonedData, "deg", "."));
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
    
    @Contract (pure = true)
    private String translateWindDirectionDegree (int degree)
    {
        String directionName = "";
        degree += 23;
        int direction = degree / 45;
        return directionsList.get(direction);
    }
    
    @Override
    public String toString ()
    {
        return "UndergroundWeatherData{" + "weatherProvider=" + weatherProvider +
                ", weatherData='" + weatherData + '\'' + ", directionsList=" + directionsList + '}';
    }
}
