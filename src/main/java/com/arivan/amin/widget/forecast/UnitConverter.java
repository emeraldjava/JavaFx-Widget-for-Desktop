package com.arivan.amin.widget.forecast;

import java.util.logging.Logger;

public class UnitConverter
{
    private final Logger logger = Logger.getLogger(getClass().getName());
    
    // todo implement temperature selection for forecast
    private static final int KELVIN_WATER_FREEZING_POINT = 273;
    
    private UnitConverter ()
    {
    }
    
    public static int convertCelsiusToKelvin (int celsius)
    {
        return celsius + KELVIN_WATER_FREEZING_POINT;
    }
}
