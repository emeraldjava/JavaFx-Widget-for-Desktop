package com.arivan.amin.widget.internet.connectivity;

public class LinuxConnectionStatus implements ConnectionStatus
{
    
    private LinuxConnectionStatus ()
    {
    }
    
    public static LinuxConnectionStatus newInstance ()
    {
        return new LinuxConnectionStatus();
    }
    
    @Override
    public String status ()
    {
        return null;
    }
    
    @Override
    public String pingTime ()
    {
        return null;
    }
    
    @Override
    public void updateData ()
    {
    }
}
