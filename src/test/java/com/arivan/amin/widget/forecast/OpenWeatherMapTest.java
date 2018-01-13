package com.arivan.amin.widget.forecast;

import org.junit.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class OpenWeatherMapTest
{
    private OpenWeatherMap weatherData;
    
    @Before
    public void setUp () throws Exception
    {
        weatherData = OpenWeatherMap.newInstance(TextFileWeatherProvider.newInstance());
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
        assertThat(sunrise, is("07:07"));
    }
    
    @Test
    public void sunset ()
    {
        String sunset = weatherData.sunset();
        assertThat(sunset, is("16:54"));
    }
    
    @Test
    public void weatherCondition ()
    {
        String condition = weatherData.weatherCondition();
        assertThat(condition, is("broken clouds"));
    }
    
    @Test
    public void weatherIcon ()
    {
        String icon = weatherData.weatherIcon();
        assertThat(icon, is("04n.png"));
    }
    
    @Test
    public void precipitationType ()
    {
        String type = weatherData.precipitationType();
        assertThat(type, is("rain"));
    }
    
    @Test
    public void precipitationValue ()
    {
        String value = weatherData.precipitationValue();
        assertThat(value, is("5"));
    }
    
    @Test
    public void windDirection ()
    {
        String direction = weatherData.windDirection();
        assertThat(direction, is("North-northeast"));
    }
    
    @Test
    public void windsSpeed ()
    {
        int speed = weatherData.windsSpeed();
        assertThat(speed, is(1));
    }
    
    @Test
    public void windName ()
    {
        String windName = weatherData.windName();
        assertThat(windName, is("Calm"));
    }
    
    @Test
    public void temperatureUnit ()
    {
        String unit = weatherData.temperatureUnit();
        assertThat(unit, is("C"));
    }
    
    @Test
    public void temperatureValue ()
    {
        int value = weatherData.temperatureValue();
        assertThat(value, is(7));
    }
    
    @Test
    public void pressureUnit ()
    {
        String unit = weatherData.pressureUnit();
        assertThat(unit, is("hPa"));
    }
    
    @Test
    public void pressureValue ()
    {
        String value = weatherData.pressureValue();
        assertThat(value, is("930"));
    }
    
    @Test
    public void humidityValue ()
    {
        String value = weatherData.humidityValue();
        assertThat(value, is("78"));
    }
    
    @Test
    public void cloudsName ()
    {
        String cloudsName = weatherData.cloudsName();
        assertThat(cloudsName, is("broken clouds"));
    }
    
    @Test
    public void cloudsRate ()
    {
        String rate = weatherData.cloudsRate();
        assertThat(rate, is("56"));
    }
    // @Test
    // public void getTimeNodeAttribute ()
    // {
    //     String from = weatherData.getTimeNodeAttribute(0, "from");
    //     assertThat(from, is("2017-12-29T15:00"));
    //     String to = weatherData.getTimeNodeAttribute(0, "to");
    //     assertThat(to, is("2017-12-29T18:00"));
    // }
    
    @Test
    public void secondDayWeather ()
    {
        String condition = weatherData.secondDayWeatherIcon();
        assertThat(condition, is("04d.png"));
    }
    
    @Test
    public void secondDayMaxTemperature ()
    {
        int maxTemperature = weatherData.secondDayMaxTemperature();
        assertThat(maxTemperature, is(16));
    }
    
    @Test
    public void secondMinTemperature ()
    {
        int minTemperature = weatherData.secondDayMinTemperature();
        assertThat(minTemperature, is(1));
    }
    
    @Test
    public void thirdDayWeather ()
    {
        String weatherIcon = weatherData.thirdDayWeatherIcon();
        assertThat(weatherIcon, is("01d.png"));
    }
    
    @Test
    public void thirdDayMaxTemperature ()
    {
        int temperature = weatherData.thirdDayMaxTemperature();
        assertThat(temperature, is(17));
    }
    
    @Test
    public void thirdMinTemperature ()
    {
        int temperature = weatherData.thirdDayMinTemperature();
        assertThat(temperature, is(0));
    }
    
    @Test
    public void fourthDayWeather ()
    {
        String weatherIcon = weatherData.fourthDayWeatherIcon();
        assertThat(weatherIcon, is("10d.png"));
    }
    
    @Test
    public void fourthDayMaxTemperature ()
    {
        int temperature = weatherData.fourthDayMaxTemperature();
        assertThat(temperature, is(15));
    }
    
    @Test
    public void fourthMinTemperature ()
    {
        int temperature = weatherData.fourthDayMinTemperature();
        assertThat(temperature, is(2));
    }
    
    @Test
    public void fifthDayWeatherIcon ()
    {
        String icon = weatherData.fifthDayWeatherIcon();
        assertThat(icon, is("10d.png"));
    }
    
    @Test
    public void fifthDayMaxTemperature ()
    {
        int temperature = weatherData.fifthDayMaxTemperature();
        assertThat(temperature, is(6));
    }
    
    @Test
    public void fifthDayMinTemperature ()
    {
        int temperature = weatherData.fifthDayMinTemperature();
        assertThat(temperature, is(4));
    }
}
