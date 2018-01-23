package com.arivan.amin.widget.file_system;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class LinuxFileSystem implements FileSystem
{
    private final Logger logger = Logger.getLogger(getClass().getName());
    private static final Pattern EXTRA_SPACE = Pattern.compile(" {2,}");
    private String data;
    
    private LinuxFileSystem ()
    {
        updateFileSystemData();
    }
    
    private void updateFileSystemData ()
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
    
    private FileSystemItem createItemFromLine (String line)
    {
        List<String> splitList = List.of(line.split(" "));
        String percent = splitList.get(4);
        percent = percent.replace("%", "");
        double usedPercentage = Double.parseDouble(percent) / 100;
        return FileSystemItem
                .newInstance(splitList.get(5), splitList.get(1), splitList.get(2), splitList.get(3),
                        usedPercentage);
    }
    
    @Override
    public List<FileSystemItem> partitions ()
    {
        List<String> lines = List.of(data.split("\n")).stream().filter(s -> s.startsWith("/dev/"))
                .collect(Collectors.toList());
        return lines.stream().map(e -> EXTRA_SPACE.matcher(e).replaceAll(" "))
                .map(this::createItemFromLine).collect(Collectors.toList());
    }
}
