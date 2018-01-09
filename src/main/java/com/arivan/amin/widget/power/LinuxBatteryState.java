package com.arivan.amin.widget.power;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class LinuxBatteryState implements BatteryState
{
    private static final double PERCENT_DIVIDED_BY_ICON_NUMBERS = 14.2;
    private static final Pattern REMOVE_SPACE = Pattern.compile(" ");
    private final Logger logger = Logger.getLogger(getClass().getName());
    private static final String[] BATTERY_COMMAND =
            { "upower", "-i", "/org/freedesktop/UPower/devices/battery_BAT0" };
    private String outputData;
    
    private LinuxBatteryState ()
    {
        super();
        outputData = getBatteryCommandOutput();
    }
    
    @NotNull
    public static LinuxBatteryState newInstance ()
    {
        return new LinuxBatteryState();
    }
    
    @Override
    public String batteryState ()
    {
        return getProperty("state:");
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
    
    // TODO 1/9/18 show no battery icon when no battery is detected
    // TODO 1/9/18 what to show when percentage drops below 14 percent ?
    @Override
    public String currentStateIcon ()
    {
        String percentage = percentage();
        percentage = percentage.substring(0, percentage.indexOf('%'));
        int percent = ((int) Math
                .round(Double.parseDouble(percentage) / PERCENT_DIVIDED_BY_ICON_NUMBERS));
        String state = "discharging".equals(batteryState()) ? "discharging" : "charging";
        return state + percent + ".png";
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
