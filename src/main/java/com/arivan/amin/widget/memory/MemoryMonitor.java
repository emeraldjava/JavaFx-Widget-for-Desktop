package com.arivan.amin.widget.memory;

import com.arivan.amin.widget.SystemCommandHandler;

import java.util.regex.Pattern;

public interface MemoryMonitor extends SystemCommandHandler
{
    double PERCENT = 100.0;
    
    Pattern NON_DIGITS = Pattern.compile("[a-z :/A-Z]+");
    
    double getUsedMemory ();
}
