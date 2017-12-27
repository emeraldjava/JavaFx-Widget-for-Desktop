package com.arivan.amin.widget.forecast;

public interface WeatherData
{
    void updateWeatherData ();
    
    int getTemperature ();
    
    int getPressure ();
    
    int getHumidity ();
    
    String getWeather ();
    
    String getWeatherDescription ();
    
    int getCloudsRate ();
    
    int getWindsSpeed ();
    
    int getWindsDirectionDegree ();
    
    String getSecondDayWeather ();
    
    int getSecondDayMaxTemperature ();
    
    int getSecondMinTemperature ();
    
    String getThirdDayWeather ();
    
    int getThirdDayMaxTemperature ();
    
    int getThirdMinTemperature ();
    
    String getFourthDayWeather ();
    
    int getFourthDayMaxTemperature ();
    
    int getFourthMinTemperature ();
}
