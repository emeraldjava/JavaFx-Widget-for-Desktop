package com.arivan.amin.widget.internet.connectivity;

public interface ConnectionStatus
{
    String status ();
    
    String pingTime ();
    
    void updateData ();
}
