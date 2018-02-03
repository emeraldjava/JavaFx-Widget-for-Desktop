package com.arivan.amin.widget.forecast;

@SuppressWarnings ("ClassWithTooManyMethods")
public interface WeatherDataProvider
{
    String getWeatherDataUri ();
    
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
    
    String secondDayWeatherIcon ();
    
    int secondDayMaxTemperature ();
    
    int secondDayMinTemperature ();
    
    String thirdDayWeatherIcon ();
    
    int thirdDayMaxTemperature ();
    
    int thirdDayMinTemperature ();
    
    String fourthDayWeatherIcon ();
    
    int fourthDayMaxTemperature ();
    
    int fourthDayMinTemperature ();
    
    String fifthDayWeatherIcon ();
    
    int fifthDayMaxTemperature ();
    
    int fifthDayMinTemperature ();
    
    void updateWeatherData ();
}
