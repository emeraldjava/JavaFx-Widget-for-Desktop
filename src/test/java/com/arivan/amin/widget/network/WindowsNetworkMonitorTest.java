package com.arivan.amin.widget.network;

import org.junit.Test;

import static org.junit.Assert.*;

public class WindowsNetworkMonitorTest
{

    @Test
    public void downloadSpeed()
    {
        String speed = WindowsNetworkMonitor.newInstance().downloadSpeed();
        System.out.println("download =========================================");
        System.out.println(speed);
    }

    @Test
    public void uploadSpeed()
    {
        String speed = WindowsNetworkMonitor.newInstance().uploadSpeed();
        System.out.println("upload =========================================");
        System.out.println(speed);
    }
}