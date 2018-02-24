package com.arivan.amin.widget.file_system;

import java.util.List;

public class WindowsFileSystem implements FileSystem
{
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
        return List.of(FileSystemItem.newInstance("D", "500G", "120G", "300G", 0.25));
    }
}
