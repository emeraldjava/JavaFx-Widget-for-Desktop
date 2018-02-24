package com.arivan.amin.widget.power;

public class WindowsBatteryState implements BatteryState
{
    private WindowsBatteryState ()
    {
    }
    
    public static WindowsBatteryState newInstance ()
    {
        return new WindowsBatteryState();
    }
    
    @Override
    public String batteryState ()
    {
        return "";
    }
    
    @Override
    public String timeToFull ()
    {
        return "";
    }
    
    @Override
    public String timeToEmpty ()
    {
        return "";
    }
    
    @Override
    public String percentage ()
    {
        return "";
    }
    
    @Override
    public void updateData ()
    {
    }
}
