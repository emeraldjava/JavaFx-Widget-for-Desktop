package com.arivan.amin.widget.power;

import java.util.List;
import java.util.logging.Logger;

public class WindowsBatteryState implements BatteryState
{
    private final Logger logger = Logger.getLogger(getClass().getName());
    
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
        return getBatteryState(Integer.parseInt(getProperty("batterystatus")));
    }
    
    private String getBatteryState (int state)
    {
        switch (state)
        {
            case 1:
                return "discharging";
            case 2:
                return "charging";
            case 3:
                return "fully-charged";
            case 4:
                return "discharging";
            case 5:
                return "discharging";
            case 6:
                return "charging";
            case 7:
                return "charging";
            case 8:
                return "charging";
            case 9:
                return "charging";
            default:
                return "discharging";
        }
    }
    
    @Override
    public String timeToFull ()
    {
        return "";
    }
    
    @Override
    public String timeToEmpty ()
    {
        return getProperty("estimatedruntime");
    }
    
    @Override
    public String percentage ()
    {
        return getProperty("estimatedchargeremaining");
    }
    
    private String getProperty (String propertyName)
    {
        try
        {
            String output =
                    getCommandOutput(List.of("WMIC", "Path", "Win32_Battery", "Get", propertyName));
            output = output.substring(output.indexOf('\n'));
            output = output.trim();
            return output;
        }
        catch (Exception e)
        {
            logger.warning(e.getMessage());
            return "";
        }
    }
    
    @Override
    public void updateData ()
    {
    }
}
