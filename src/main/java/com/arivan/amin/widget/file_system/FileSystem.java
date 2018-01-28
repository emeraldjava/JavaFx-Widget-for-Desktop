package com.arivan.amin.widget.file_system;

import com.arivan.amin.widget.SystemCommandHandler;

import java.util.List;

/**
 * The interface File system.
 */
@FunctionalInterface
public interface FileSystem extends SystemCommandHandler
{
    /**
     * Partitions list of the system containing all the drives.
     * their name, size, used space and available space.
     *
     * @return the list of partation items.
     */
    List<FileSystemItem> partitions ();
}
