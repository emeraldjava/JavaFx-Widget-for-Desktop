package com.arivan.amin.widget.connectivity;

import org.junit.Test;

public class LinuxConnectionStatusTest
{
    
    @Test
    public void isConnected ()
    {
        System.out.println(LinuxConnectionStatus.newInstance().isConnected());
    }
    
    @Test
    public void pingTime ()
    {
        int count = 0;
        LinuxConnectionStatus newInstance = LinuxConnectionStatus.newInstance();
        for (int i = 0; i < 100; i++)
        {
            newInstance.updateData();
            double pingTime = newInstance.pingTime();
            System.out.println(pingTime + " ms");
            System.out.println("done " + ++count + " ping");
        }
    }
}
