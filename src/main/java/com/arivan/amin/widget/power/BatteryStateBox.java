package com.arivan.amin.widget.power;

import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public class BatteryStateBox extends VBox
{
    private final Logger logger = Logger.getLogger(getClass().getName());
    BatteryState batteryState;
    
    private BatteryStateBox ()
    {
        super();
        batteryState = LinuxBatteryState.newInstance();
    }
    
    @NotNull
    public static BatteryStateBox newInstance ()
    {
        return new BatteryStateBox();
    }
}
