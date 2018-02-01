package com.arivan.amin.widget.forecast;

public interface LocationProvider
{
    String countryName ();
    
    String cityName ();
    
    double latitude ();
    
    double longitude ();
    
    String timezone ();
}
