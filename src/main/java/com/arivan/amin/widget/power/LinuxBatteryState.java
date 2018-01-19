package com.arivan.amin.widget.power;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class LinuxBatteryState implements BatteryState
{
    private final Logger logger = Logger.getLogger(getClass().getName());
    private static final Pattern REMOVE_SPACE = Pattern.compile(" ");
    private static final String[] BATTERY_COMMAND =
            { "upower", "-i", "/org/freedesktop/UPower/devices/battery_BAT0" };
    private String outputData;
    
    private LinuxBatteryState ()
    {
        outputData = getBatteryCommandOutput();
    }
    
    @NotNull
    public static LinuxBatteryState newInstance ()
    {
        return new LinuxBatteryState();
    }
    
    @NotNull
    private String getProperty (String propertyName)
    {
        String data = new String(outputData);
        data = data.substring(data.indexOf(propertyName) + propertyName.length());
        data = data.substring(0, data.indexOf(System.lineSeparator()));
        return data;
    }
    
    @Override
    public String batteryState ()
    {
        return getProperty("state:");
    }
    
    @Override
    public String timeToFull ()
    {
        return getProperty("timetofull:");
    }
    
    @Override
    public String timeToEmpty ()
    {
        return getProperty("timetoempty:");
    }
    
    @Override
    public String percentage ()
    {
        return getProperty("percentage:");
    }
    
    @Override
    public void updateData ()
    {
        outputData = getBatteryCommandOutput();
    }
    
    private static String removeExtraData (@NotNull CharSequence output)
    {
        return REMOVE_SPACE.matcher(output).replaceAll("");
    }
    
    @NotNull
    private String getBatteryCommandOutput ()
    {
        try (InputStream stream = new ProcessBuilder(BATTERY_COMMAND).start().getInputStream())
        {
            return removeExtraData(new String(stream.readAllBytes(), StandardCharsets.UTF_8));
        }
        catch (IOException e)
        {
            logger.warning(e.getMessage());
            return "";
        }
    }
    
    @Override
    public String toString ()
    {
        return "LinuxBatteryState{" + "outputData='" + outputData + '\'' + '}';
    }
}
