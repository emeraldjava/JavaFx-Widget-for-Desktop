package com.arivan.amin.widget.connectivity;

public class WindowsConnectionStatus implements ConnectionStatus
{
    private WindowsConnectionStatus ()
    {
    }
    
    public static WindowsConnectionStatus newInstance ()
    {
        return new WindowsConnectionStatus();
    }
    
    @Override
    public boolean isConnected ()
    {
        return false;
    }
    
    @Override
    public double pingTime ()
    {
        return 0;
    }
    
    @Override
    public void updateData ()
    {
    }
}
