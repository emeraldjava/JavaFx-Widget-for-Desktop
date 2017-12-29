package com.arivan.amin.widget.forecast;

import javafx.scene.layout.Pane;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public class WeatherPane extends Pane
{
    private final Logger logger = Logger.getLogger(getClass().getName());
    
    private WeatherPane ()
    {
        super();
    }
    
    @NotNull
    public static WeatherPane newInstance ()
    {
        return new WeatherPane();
    }
}
