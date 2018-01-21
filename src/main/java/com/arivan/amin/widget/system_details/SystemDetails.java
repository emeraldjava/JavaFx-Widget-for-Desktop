package com.arivan.amin.widget.system_details;

public interface SystemDetails
{
    //should return linux, windows
    String systemName ();
    
    // should return linux distribution on Linux, windows version on windows 
    String operatingSystemName ();
    
    // return build number
    String operatingSystemVersion ();
    
    // return url of os homepage
    String systemHomeUrl ();
    
    // return x86 or x86_64
    String architecture ();
}
