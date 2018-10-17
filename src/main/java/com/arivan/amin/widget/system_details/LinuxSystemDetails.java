package com.arivan.amin.widget.system_details;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinuxSystemDetails implements SystemDetails
{
    private final Logger logger = Logger.getLogger(getClass().getName());
    private static final String[] OS_RELEASE_COMMAND = { "cat", "/etc/os-release" };
    private static final String[] SYSTEM_DETAILS_COMMAND = { "uname", "-a" };
    
    private LinuxSystemDetails ()
    {
    }
    
    public static LinuxSystemDetails newInstance ()
    {
        return new LinuxSystemDetails();
    }
    
    private String getSystemCommand (String[] command)
    {
        try
        {
            return getCommandOutput(List.of(command)).replace("\n", " ");
        }
        catch (IOException e)
        {
            logger.warning(e.getMessage());
            return "";
        }
    }
    
    @Override
    public String systemName ()
    {
        try
        {
            String output = getSystemCommand(SYSTEM_DETAILS_COMMAND);
            output = output.substring(0, output.indexOf(' '));
            output = output.trim();
            return output;
        }
        catch (Exception e)
        {
            logger.warning(e.getMessage());
            return "";
        }
    }
    
    @Override
    public String operatingSystemName ()
    {
        String output = getSystemCommand(OS_RELEASE_COMMAND);
        String name = "NAME=";
        output = output.substring(output.indexOf(name) + name.length(), output.indexOf("ID"));
        output = output.trim();
        return output;
    }
    
    @Override
    public String operatingSystemVersion ()
    {
        String output = getSystemCommand(OS_RELEASE_COMMAND);
        String version = "VERSION=\"";
        output = output.substring(output.indexOf(version) + version.length());
        output = output.substring(0, output.indexOf('"'));
        return output;
    }
    
    @Override
    public String systemHomeUrl ()
    {
        String output = getSystemCommand(OS_RELEASE_COMMAND);
        String homeUrl = "HOME_URL=\"";
        output = output.substring(output.indexOf(homeUrl) + homeUrl.length());
        output = output.substring(0, output.indexOf('"'));
        return output;
    }
    
    @Override
    public String architecture ()
    {
        String arch = "";
        String output = getSystemCommand(SYSTEM_DETAILS_COMMAND);
        Pattern compile = Pattern.compile("(.*)(x[0-9]{2}_[0-9]{2})(.)*");
        Matcher matcher = compile.matcher(output);
        if (matcher.matches())
        {
            arch = matcher.group(2);
        }
        return arch;
    }
}
