package com.arivan.amin.widget.power;

import org.junit.Before;
import org.junit.Test;

public class LinuxBatteryStateTest
{
    BatteryState batteryState;
    
    @Before
    public void setUp () throws Exception
    {
        batteryState = LinuxBatteryState.newInstance();
    }
    
    @Test
    public void batteryState ()
    {
    }
    
    @Test
    public void timeToFull ()
    {
    }
    
    @Test
    public void percentage ()
    {
    }
    
    @Test
    public void currentStateIcon ()
    {
        System.out.println(batteryState.currentStateIcon());
    }
    
    @Test
    public void timeToEmpty ()
    {
    }
}
