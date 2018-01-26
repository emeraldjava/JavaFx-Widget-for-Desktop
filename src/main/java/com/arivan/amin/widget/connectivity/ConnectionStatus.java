package com.arivan.amin.widget.connectivity;

public interface ConnectionStatus
{
    boolean isConnected ();
    
    double pingTime ();
    
    void updateData ();
}
