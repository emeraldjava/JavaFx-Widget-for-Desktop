package com.arivan.amin.widget.wireless;

public class LinuxWirelessMonitor implements WirelessMonitor
{
    private LinuxWirelessMonitor ()
    {
    }
    
    public static LinuxWirelessMonitor newInstance ()
    {
        return new LinuxWirelessMonitor();
    }
    
    @Override
    public boolean isConnected ()
    {
        return true;
    }
    
    @Override
    public String wirelessName ()
    {
        return "name";
    }
    
    @Override
    public int linkQuality ()
    {
        return 0;
    }
    
    @Override
    public int signalLevel ()
    {
        return 1;
    }
    
    @Override
    public void updateData ()
    {
    }
}
