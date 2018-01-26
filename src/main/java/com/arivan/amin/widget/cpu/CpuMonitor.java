package com.arivan.amin.widget.cpu;

import com.arivan.amin.widget.SystemCommandHandler;

@FunctionalInterface
public interface CpuMonitor extends SystemCommandHandler
{
    double PERCENT = 100.0;
    
    double getCpuUsage ();
}
