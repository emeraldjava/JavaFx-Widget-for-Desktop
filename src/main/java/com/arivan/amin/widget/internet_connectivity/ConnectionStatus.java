package com.arivan.amin.widget.internet_connectivity;

public interface ConnectionStatus
{
    boolean isConnected ();
    
    double pingTime ();
    
    void updateData ();
}
