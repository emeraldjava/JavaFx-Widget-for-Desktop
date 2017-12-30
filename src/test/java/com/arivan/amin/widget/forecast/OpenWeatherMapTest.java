package com.arivan.amin.widget.forecast;

import org.junit.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class OpenWeatherMapTest
{
    WeatherProvider provider;
    OpenWeatherMap weatherData;
    
    @Before
    public void setUp () throws Exception
    {
        provider = TextFileWeatherProvider.newInstance();
        weatherData = OpenWeatherMap.newInstance();
        weatherData.setWeatherProvider(provider);
    }
    
    @After
    public void tearDown () throws Exception
    {
    }
    
    @Test
    public void locationName ()
    {
        String locationName = weatherData.locationName();
        assertThat(locationName, is("As Sulaymaniyah"));
    }
    
    @Test
    public void sunrise ()
    {
        String sunrise = weatherData.sunrise();
        assertThat(sunrise, is("04:07"));
    }
    @Test
    public void sunset ()
    {
        String sunset = weatherData.sunset();
        assertThat(sunset, is("13:54"));
    }
    
    @Test
    public void weatherCondition ()
    {
        String condition = weatherData.weatherCondition();
        assertThat(condition, is("broken clouds"));
    }
    
    @Test
    public void precipitationType ()
    {
        throw new AssertionError();
    }
    
    public void precipitationValue ()
    {
        throw new AssertionError();
    }
    
    public void windDirection ()
    {
        throw new AssertionError();
    }
    
    public void windsSpeed ()
    {
        throw new AssertionError();
    }
    
    public void windName ()
    {
        throw new AssertionError();
    }
    
    public void temperatureUnit ()
    {
        throw new AssertionError();
    }
    
    public void temperatureValue ()
    {
        throw new AssertionError();
    }
    
    public void pressureUnit ()
    {
        throw new AssertionError();
    }
    
    public void pressureValue ()
    {
        throw new AssertionError();
    }
    
    public void humidityValue ()
    {
        throw new AssertionError();
    }
    
    public void cloudsName ()
    {
        throw new AssertionError();
    }
    
    public void cloudsRate ()
    {
        throw new AssertionError();
    }
    
    public void secondDayWeather ()
    {
    }
    
    public void secondDayMaxTemperature ()
    {
    }
    
    public void secondMinTemperature ()
    {
    }
    
    public void thirdDayWeather ()
    {
    }
    
    public void thirdDayMaxTemperature ()
    {
    }
    
    public void thirdMinTemperature ()
    {
    }
    
    public void fourthDayWeather ()
    {
    }
    
    public void fourthDayMaxTemperature ()
    {
    }
    
    public void fourthMinTemperature ()
    {
    }
}
