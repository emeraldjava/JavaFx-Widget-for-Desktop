package com.arivan.amin.widget.forecast;

import java.util.logging.Logger;

public class UnitConverter
{
    private final Logger logger = Logger.getLogger(getClass().getName());
    
    private static final int KELVIN_WATER_FREEZING_POINT = 273;
    
    private UnitConverter ()
    {
    }
    
    public static int convertKelvinToCelsius (int kelvin)
    {
        return kelvin - KELVIN_WATER_FREEZING_POINT;
    }
}
