package com.arivan.amin.widget.system_details;

public class WindowsSystemDetails implements SystemDetails
{
    
    private WindowsSystemDetails ()
    {
    }
    
    public static WindowsSystemDetails newInstance ()
    {
        return new WindowsSystemDetails();
    }
    
    @Override
    public String systemName ()
    {
        return "";
    }
    
    @Override
    public String operatingSystemName ()
    {
        return "";
    }
    
    @Override
    public String operatingSystemVersion ()
    {
        return "";
    }
    
    @Override
    public String systemHomeUrl ()
    {
        return "";
    }
    
    @Override
    public String architecture ()
    {
        return "";
    }
}
