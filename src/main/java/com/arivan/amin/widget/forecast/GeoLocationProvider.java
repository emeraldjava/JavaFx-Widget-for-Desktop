package com.arivan.amin.widget.forecast;

public interface GeoLocationProvider
{
    String countryName ();
    
    String cityName ();
    
    double latitude ();
    
    double longitude ();
}
