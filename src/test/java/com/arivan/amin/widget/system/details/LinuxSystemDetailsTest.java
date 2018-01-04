package com.arivan.amin.widget.system.details;

import org.junit.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LinuxSystemDetailsTest
{
    SystemDetails systemDetails;
    
    @Before
    public void setUp () throws Exception
    {
        systemDetails = LinuxSystemDetails.newInstance();
    }
    
    @Test
    public void systemName ()
    {
        String systemName = systemDetails.systemName();
        assertThat(systemName, is("Linux"));
    }
    
    @Test
    public void operatingSystemName ()
    {
        String name = systemDetails.operatingSystemName();
        assertThat(name, is("Fedora"));
    }
    
    // @Test
    public void operatingSystemVersion ()
    {
    }
    
    // @Test
    public void systemHomeUrl ()
    {
    }
    
    // @Test
    public void architecture ()
    {
    }
}
