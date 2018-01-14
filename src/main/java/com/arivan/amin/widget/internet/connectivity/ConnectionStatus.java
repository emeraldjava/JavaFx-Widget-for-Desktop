package com.arivan.amin.widget.internet.connectivity;

public interface ConnectionStatus
{
    boolean isConnected ();
    
    double pingTime ();
    
    void updateData ();
}
