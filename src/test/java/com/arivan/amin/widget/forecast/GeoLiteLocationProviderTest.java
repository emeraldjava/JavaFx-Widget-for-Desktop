package com.arivan.amin.widget.forecast;

import org.junit.Test;

import static org.junit.Assert.*;

public class GeoLiteLocationProviderTest
{
    
    @Test
    public void countryName ()
    {
        System.out.println(GeoLiteLocationProvider.newInstance().countryName());
    }
    
    @Test
    public void cityName ()
    {
        System.out.println(GeoLiteLocationProvider.newInstance().cityName());
    }
    
    @Test
    public void timezone ()
    {
        System.out.println(GeoLiteLocationProvider.newInstance().timezone());
    }
    
    @Test
    public void latitude ()
    {
        System.out.println(GeoLiteLocationProvider.newInstance().latitude());
    }
    
    @Test
    public void longitude ()
    {
        System.out.println(GeoLiteLocationProvider.newInstance().longitude());
    }
}
