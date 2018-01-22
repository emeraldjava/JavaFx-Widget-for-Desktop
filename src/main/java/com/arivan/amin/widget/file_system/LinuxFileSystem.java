package com.arivan.amin.widget.file_system;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class LinuxFileSystem implements FileSystem
{
    private final Logger logger = Logger.getLogger(getClass().getName());
    private String data;
    
    private LinuxFileSystem ()
    {
        try
        {
            data = getCommandOutput(List.of("df", "-h"));
        }
        catch (IOException e)
        {
            logger.warning(e.getMessage());
            data = "";
        }
    }
    
    public static LinuxFileSystem newInstance ()
    {
        return new LinuxFileSystem();
    }
    
    @Override
    public List<FileSystemItem> partitions ()
    {
        Stream<String> lines =
                List.of(data.split("\n")).stream().filter(s -> s.startsWith("/dev/"));
        // todo extract values from lines
        return List.of(FileSystemItem.newInstance("/home", "630G", "94G", "505G", 0.16));
    }
}
