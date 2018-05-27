package com.arivan.amin.widget.uptime;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;

public class WindowsUptimeMonitor implements UptimeMonitor
{
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final DateTimeFormatter dateTimeFormatter;
    
    private WindowsUptimeMonitor ()
    {
        dateTimeFormatter = DateTimeFormatter.ofPattern("M/dd/yyyy hh:mm:ss a");
    }
    
    public static WindowsUptimeMonitor newInstance ()
    {
        return new WindowsUptimeMonitor();
    }
    
    // systeminfo to find if connected to wifi or ethernet 
    // Network Card(s):           2 NIC(s) Installed.
    //     [01]: Realtek PCIe GBE Family Controller
    // Connection Name: Ethernet
    // Status:          Media disconnected
    //                        [02]: Ralink RT3290 802.11bgn Wi-Fi Adapter
    // Connection Name: Wi-Fi
    // Status:          Media disconnected
    // when connected 
    // Network Card(s):           2 NIC(s) Installed.
    //     [01]: Realtek PCIe GBE Family Controller
    // Connection Name: Ethernet
    // Status:          Media disconnected
    //                        [02]: Ralink RT3290 802.11bgn Wi-Fi Adapter
    // Connection Name: Wi-Fi
    // DHCP Enabled:    Yes
    // DHCP Server:     192.168.43.1
    // IP address(es)
    //         [01]: 192.168.43.88
    //     [02]: fe80::8c3:aa09:3a2e:8e5a
    // systeminfo | find "System Boot Time:"     returns the last time system rebooted
    @Override
    public String systemUptime ()
    {
        try
        {
            String output = getCommandOutput(List.of("systeminfo"));
            String delimiter = "System Boot Time:";
            output = output.substring(output.indexOf(delimiter) + delimiter.length());
            output = output.substring(0, output.indexOf('\n'));
            String dateTime = output.trim();
            String date = dateTime.substring(0, dateTime.indexOf(','));
            String time = dateTime.substring(dateTime.indexOf(',') + 1);
            LocalDateTime bootDateTime = LocalDateTime.parse(date + time, dateTimeFormatter);
            LocalDateTime now = LocalDateTime.now();
            System.out.println("boot date time " + bootDateTime);
            System.out.println("now " + now);
            return dateTime;
        }
        catch (Exception e)
        {
            logger.warning(e.getMessage());
            return "";
        }
    }
}
