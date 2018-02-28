package com.arivan.amin.widget.file_system;

import com.arivan.amin.widget.network.LinuxNetworkMonitor;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class WindowsFileSystem implements FileSystem
{
    private final Logger logger = Logger.getLogger(getClass().getName());
    
    private WindowsFileSystem ()
    {
    }
    
    public static WindowsFileSystem newInstance ()
    {
        return new WindowsFileSystem();
    }
    
    @Override
    public List<FileSystemItem> partitions ()
    {
        String output;
        // wmic logicaldisk get size,freespace,caption
        // Caption  FreeSpace Size
        // C:       36229566464  53160701952
        // D:       0            57866240
        try
        {
            List<FileSystemItem> fileSystemItems = new ArrayList<>(10);
            output = getCommandOutput(
                    List.of("wmic", "logicaldisk", "get", "size,", "freespace,", "caption"));
            List<String> drivesList = List.of(output.trim().split("\n"));
            drivesList = drivesList.stream().skip(1).collect(Collectors.toList());
            drivesList.forEach(s ->
            {
                String[] driveInfo =
                        LinuxFileSystem.EXTRA_SPACE.matcher(s).replaceAll(" ").trim().split(" ");
                double size = Double.parseDouble(driveInfo[2]);
                double free = Double.parseDouble(driveInfo[1]);
                double used = size - free;
                double usedPercentage = 1 - (free / size);
                fileSystemItems.add(FileSystemItem.newInstance(driveInfo[0],
                        LinuxNetworkMonitor.bytesIntoHumanReadable(Long.parseLong(driveInfo[2])),
                        String.valueOf(used), String.valueOf(free), usedPercentage));
            });
            return fileSystemItems;
        }
        catch (Exception e)
        {
            logger.warning(e.getMessage());
            return Collections.emptyList();
        }
    }
}
