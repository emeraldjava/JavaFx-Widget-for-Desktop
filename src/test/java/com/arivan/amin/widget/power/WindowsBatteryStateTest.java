package com.arivan.amin.widget.power;

import org.junit.Test;

public class WindowsBatteryStateTest
{
    
    @Test
    public void batteryState ()
    {
        String batteryState = WindowsBatteryState.newInstance().batteryState();
        System.out.println("battery state ==================================");
        System.out.println(batteryState);
    }
    
    @Test
    public void timeToFull ()
    {
        System.out.println("time to full " + WindowsBatteryState.newInstance().timeToFull());
    }
    
    @Test
    public void timeToEmpty ()
    {
        String time = WindowsBatteryState.newInstance().timeToEmpty();
        System.out.println("time to empty =======================================");
        System.out.println(time);
    }
    
    @Test
    public void percentage ()
    {
        String percentage = WindowsBatteryState.newInstance().percentage();
        System.out.println("percentage ============================================");
        System.out.println(percentage);
    }
}
