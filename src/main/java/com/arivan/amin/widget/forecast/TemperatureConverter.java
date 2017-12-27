package com.arivan.amin.widget.forecast;

public class TemperatureConverter
{
    private static final double KELVIN_WATER_FREEZING_POINT = 273.15;
    
    public static int convertKelvinToCelsius (double kelvin)
    {
        return Integer.parseInt(String.valueOf(kelvin - KELVIN_WATER_FREEZING_POINT));
    }
    
    public static int convertCelsiusToKelvin (double celsius)
    {
        return Integer.parseInt(String.valueOf(celsius + KELVIN_WATER_FREEZING_POINT));
    }
}
