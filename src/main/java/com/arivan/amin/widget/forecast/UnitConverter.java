package com.arivan.amin.widget.forecast;

import java.util.logging.Logger;

public class UnitConverter
{
    private final Logger logger = Logger.getLogger(getClass().getName());
    
    private static final int KELVIN_WATER_FREEZING_POINT = 273;
    private static final double FAHRENHEIT_CONVERSION_RATE = 9.0 / 5.0;
    
    private UnitConverter ()
    {
    }
    
    public static int convertCelsiusToKelvin (int celsius)
    {
        return celsius + KELVIN_WATER_FREEZING_POINT;
    }
    
    public static int convertCelsiusToFahrenheit (int celsius)
    {
        return (int) ((celsius * FAHRENHEIT_CONVERSION_RATE) + 32);
    }
}
