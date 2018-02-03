package com.arivan.amin.widget.connectivity;

public interface ConnectionStatus
{
    /**
     * Is connected boolean.
     *
     * @return the boolean specifying whether the computer is connected to a wireless access point
     */
    boolean isConnected ();
    
    /**
     * Ping time double.
     *
     * @return the ping time of the access point
     */
    double pingTime ();
    
    void updateData ();
}
