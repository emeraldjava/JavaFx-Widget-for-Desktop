package com.arivan.amin.widget.system.details;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinuxSystemDetails implements SystemDetails
{
    private static final String[] OS_RELEASE_COMMAND = { "cat", "/etc/os-release" };
    private static final String[] UNAME_COMMAND = { "uname", "-a" };
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
            output = output.replace("\n", " ");
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
        String output = getCommandOutput(UNAME_COMMAND);
        output = output.substring(0, output.indexOf(' '));
        output = output.trim();
        return output;
    }
    
    @Override
    public String operatingSystemName ()
    {
        String output = getCommandOutput(OS_RELEASE_COMMAND);
        String name = "NAME=";
        output = output.substring(output.indexOf(name) + name.length(), output.indexOf("VERSION"));
        output = output.trim();
        return output;
    }
    
    @Override
    public String operatingSystemVersion ()
    {
        String output = getCommandOutput(OS_RELEASE_COMMAND);
        String version = "VERSION=\"";
        output = output.substring(output.indexOf(version) + version.length());
        output = output.substring(0, output.indexOf('"'));
        return output;
    }
    
    @Override
    public String systemHomeUrl ()
    {
        String output = getCommandOutput(OS_RELEASE_COMMAND);
        String homeUrl = "HOME_URL=\"";
        output = output.substring(output.indexOf(homeUrl) + homeUrl.length());
        output = output.substring(0, output.indexOf('"'));
        return output;
    }
    
    @Override
    public String architecture ()
    {
        String arch = "";
        String output = getCommandOutput(UNAME_COMMAND);
        Pattern compile = Pattern.compile("(.*)(x[0-9]{2}_[0-9]{2})(.)*");
        Matcher matcher = compile.matcher(output);
        if (matcher.matches())
        {
            arch = matcher.group(2);
        }
        return arch;
    }
}
