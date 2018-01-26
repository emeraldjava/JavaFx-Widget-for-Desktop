package com.arivan.amin.widget.uptime;

import com.arivan.amin.widget.SystemCommandHandler;

@FunctionalInterface
public interface UptimeMonitor extends SystemCommandHandler
{
    String systemUptime ();
}
