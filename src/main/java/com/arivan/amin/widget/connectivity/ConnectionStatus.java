package com.arivan.amin.widget.connectivity;

import com.arivan.amin.widget.SystemCommandHandler;

public interface ConnectionStatus extends SystemCommandHandler
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
