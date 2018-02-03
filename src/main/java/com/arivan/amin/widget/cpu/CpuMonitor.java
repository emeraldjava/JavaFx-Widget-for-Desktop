package com.arivan.amin.widget.cpu;

import com.arivan.amin.widget.SystemCommandHandler;


@FunctionalInterface
public interface CpuMonitor extends SystemCommandHandler
{
    /**
     * Gets cpu usage of the system, all cores combined.
     * The double returned is between 0 and 1 where 1 means full cpu usage
     * the value must be between 0 and 1 to be shown with a progress bar
     *
     * @return the cpu usage between 0 and 1
     */
    double getCpuUsage ();
}
