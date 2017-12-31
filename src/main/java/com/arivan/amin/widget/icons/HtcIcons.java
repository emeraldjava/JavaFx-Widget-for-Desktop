package com.arivan.amin.widget.icons;

import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public class HtcIcons implements ApplicationIcons
{
    private final Logger logger = Logger.getLogger(getClass().getName());
    
    private HtcIcons ()
    {
        super();
    }
    
    @NotNull
    public static HtcIcons newInstance ()
    {
        return new HtcIcons();
    }
    
    @Override
    public String widgetIcon ()
    {
        return null;
    }
    
    @Override
    public String brokenClouds ()
    {
        return "/htc icons/broken clouds.png";
    }
    @Override
    public String clearDay ()
    {
        return null;
    }
    
    @Override
    public String clearNight ()
    {
        return null;
    }
    
    @Override
    public String fewClouds ()
    {
        return null;
    }
    
    @Override
    public String freezing ()
    {
        return null;
    }
    
    @Override
    public String heavyRain ()
    {
        return null;
    }
    
    @Override
    public String heavySnow ()
    {
        return null;
    }
    
    @Override
    public String hotDay ()
    {
        return null;
    }
    
    @Override
    public String lightRain ()
    {
        return null;
    }
    
    @Override
    public String lightShowerRain ()
    {
        return null;
    }
    
    @Override
    public String mist ()
    {
        return null;
    }
    
    @Override
    public String moderateRain ()
    {
        return null;
    }
    
    @Override
    public String nightBrokenClouds ()
    {
        return null;
    }
    
    @Override
    public String nightFewClouds ()
    {
        return null;
    }
    
    @Override
    public String nightHeavyRain ()
    {
        return null;
    }
    
    @Override
    public String nightOvercastClouds ()
    {
        return null;
    }
    
    @Override
    public String nightScatteredClouds ()
    {
        return null;
    }
    
    @Override
    public String overcastClouds ()
    {
        return null;
    }
    
    @Override
    public String rainAndSnow ()
    {
        return null;
    }
    
    @Override
    public String scatteredClouds ()
    {
        return null;
    }
    
    @Override
    public String showerAndSnow ()
    {
        return null;
    }
    
    @Override
    public String showerRain ()
    {
        return null;
    }
    
    @Override
    public String snow ()
    {
        return null;
    }
    
    @Override
    public String thunderstorm ()
    {
        return null;
    }
}
