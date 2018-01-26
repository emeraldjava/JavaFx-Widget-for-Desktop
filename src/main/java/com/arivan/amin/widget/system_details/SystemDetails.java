package com.arivan.amin.widget.system_details;

import com.arivan.amin.widget.SystemCommandHandler;

public interface SystemDetails extends SystemCommandHandler
{
    String systemName ();
    
    String operatingSystemName ();
    
    String operatingSystemVersion ();
    
    String systemHomeUrl ();
    
    String architecture ();
}
