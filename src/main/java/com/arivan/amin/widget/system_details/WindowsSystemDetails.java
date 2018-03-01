package com.arivan.amin.widget.system_details;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class WindowsSystemDetails implements SystemDetails
{
    private final Logger logger = Logger.getLogger(getClass().getName());
    private String data;
    
    private WindowsSystemDetails ()
    {
        getSystemDetails();
    }
    
    private String getProperty (String name)
    {
        String dataCopy = new String(data);
        String propertyName = name + ':';
        dataCopy = data.substring(dataCopy.indexOf(propertyName) + propertyName.length());
        dataCopy = dataCopy.substring(0, dataCopy.indexOf('\n'));
        return dataCopy.trim();
    }
    
    private void getSystemDetails ()
    {
        try
        {
            data = getCommandOutput(List.of("systeminfo"));
        }
        catch (IOException e)
        {
            logger.warning(e.getMessage());
            data = "";
        }
    }
    
    public static WindowsSystemDetails newInstance ()
    {
        return new WindowsSystemDetails();
    }
    
    @Override
    public String systemName ()
    {
        return "Windows";
    }
    
    @Override
    public String operatingSystemName ()
    {
        return getProperty("OS Name");
    }
    
    @Override
    public String operatingSystemVersion ()
    {
        return getProperty("OS Version");
    }
    
    @Override
    public String systemHomeUrl ()
    {
        return "https://www.microsoft.com/en-us/windows/";
    }
    
    @Override
    public String architecture ()
    {
        return getProperty("System Type");
    }
}
