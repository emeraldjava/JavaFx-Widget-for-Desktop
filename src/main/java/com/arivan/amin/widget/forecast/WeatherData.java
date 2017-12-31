package com.arivan.amin.widget.forecast;

public interface WeatherData
{
    String locationName ();
    
    String sunrise ();
    
    String sunset ();
    
    String weatherCondition ();
    
    String weatherIcon ();
    
    String precipitationType ();
    
    String precipitationValue ();
    
    String windDirection ();
    
    int windsSpeed ();
    
    String windName ();
    
    String temperatureUnit ();
    
    int temperatureValue ();
    
    String pressureUnit ();
    
    String pressureValue ();
    
    String humidityValue ();
    
    String cloudsName ();
    
    String cloudsRate ();
    
    String secondDayWeather ();
    
    int secondDayMaxTemperature ();
    
    int secondMinTemperature ();
    
    String thirdDayWeather ();
    
    int thirdDayMaxTemperature ();
    
    int thirdMinTemperature ();
    
    String fourthDayWeather ();
    
    int fourthDayMaxTemperature ();
    
    int fourthMinTemperature ();
    
    void updateWeatherData ();
}
