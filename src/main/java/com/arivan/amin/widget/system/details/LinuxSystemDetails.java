package com.arivan.amin.widget.system.details;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class LinuxSystemDetails implements SystemDetails
{
    private final Logger logger = Logger.getLogger(getClass().getName());
    
    private LinuxSystemDetails ()
    {
        super();
    }
    
    @NotNull
    public static LinuxSystemDetails newInstance ()
    {
        return new LinuxSystemDetails();
    }
    
    private String getCommandOutput (String... command)
    {
        String output = "";
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        try (InputStream stream = processBuilder.start().getInputStream())
        {
            output = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        }
        catch (IOException e)
        {
            logger.warning(e.getMessage());
        }
        return output;
    }
    
    @Override
    public String systemName ()
    {
        String output = getCommandOutput("uname", "-a");
        output = output.substring(0, output.indexOf(' '));
        return output;
    }
    
    @Override
    public String operatingSystemName ()
    {
        String output = getCommandOutput("cat", "/etc/os-release");
        
        return null;
    }
    
    @Override
    public String operatingSystemVersion ()
    {
        return null;
    }
    
    @Override
    public String systemHomeUrl ()
    {
        return null;
    }
    
    @Override
    public String architecture ()
    {
        return null;
    }
}
