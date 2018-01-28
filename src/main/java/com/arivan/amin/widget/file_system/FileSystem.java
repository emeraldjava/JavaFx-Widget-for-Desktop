package com.arivan.amin.widget.file_system;

import com.arivan.amin.widget.SystemCommandHandler;

import java.util.List;

// todo add package info and javadoc
@FunctionalInterface
public interface FileSystem extends SystemCommandHandler
{
    List<FileSystemItem> partitions ();
}
