package com.arivan.amin.widget.forecast;

import org.junit.*;

import static org.hamcrest.CoreMatchers.is;

public class UndergroundWeatherDataTest
{
    WeatherProvider provider;
    UndergroundWeatherData weatherData;
    
    @Before
    public void setUp () throws Exception
    {
        provider = TextFileWeatherProvider.newInstance();
        weatherData = UndergroundWeatherData.newInstance();
        weatherData.setWeatherProvider(provider);
    }
    
    @After
    public void tearDown () throws Exception
    {
    }
    
    @Test
    public void getTemperature ()
    {
        int temp = weatherData.getTemperature();
        Assert.assertThat("is temperature converted correctly", temp, is(17));
    }
    
    @Test
    public void getPressure ()
    {
        int pressure = weatherData.getPressure();
        Assert.assertThat("is pressure fetched correctly", pressure, is(922));
    }
    
    @Test
    public void getHumidity ()
    {
        int humidity = weatherData.getHumidity();
        Assert.assertThat("is humidity fetched correctly", humidity, is(62));
    }
    
    @Test
    public void getWeather ()
    {
        String weather = weatherData.getWeather();
        Assert.assertThat("is weather condition correct", weather, is("Clouds"));
    }
    
    @Test
    public void getWeatherDescription ()
    {
        String description = weatherData.getWeatherDescription();
        Assert.assertThat("is weather description correct", description, is("overcast clouds"));
    }
    
    @Test
    public void getCloudsRate ()
    {
        int cloudsRate = weatherData.getCloudsRate();
        Assert.assertThat("is clouds rate correct", cloudsRate, is(88));
    }
    
    @Test
    public void getWindsSpeed ()
    {
        int speed = weatherData.getWindsSpeed();
        Assert.assertThat("is wind speed correct", speed, is(2));
    }
    
    @Test
    public void getWindsDirection ()
    {
        String direction = weatherData.getWindsDirection();
        Assert.assertThat("is wind direction translated correctly", direction, is("south west"));
    }
    
    @Test
    public void getSecondDayWeather ()
    {
    }
    
    @Test
    public void getSecondDayMaxTemperature ()
    {
    }
    
    @Test
    public void getSecondMinTemperature ()
    {
    }
    
    @Test
    public void getThirdDayWeather ()
    {
    }
    
    @Test
    public void getThirdDayMaxTemperature ()
    {
    }
    
    @Test
    public void getThirdMinTemperature ()
    {
    }
    
    @Test
    public void getFourthDayWeather ()
    {
    }
    
    @Test
    public void getFourthDayMaxTemperature ()
    {
    }
    
    @Test
    public void getFourthMinTemperature ()
    {
    }
}
