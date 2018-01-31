package com.arivan.amin.widget.forecast;

import org.hamcrest.CoreMatchers;
import org.junit.*;

public class GeoLite2LocationProviderTest
{
    GeoLite2LocationProvider locationProvider;
    
    @Before
    public void setUp () throws Exception
    {
        locationProvider = GeoLite2LocationProvider.newInstance();
    }
    
    @Test
    public void countryName ()
    {
        String country = locationProvider.countryName();
        Assert.assertThat(country, CoreMatchers.is("Iraq"));
    }
    
    // @Test
    public void cityName ()
    {
    }
    
    // @Test
    public void latitude ()
    {
    }
    
    // @Test
    public void longitude ()
    {
    }
}
