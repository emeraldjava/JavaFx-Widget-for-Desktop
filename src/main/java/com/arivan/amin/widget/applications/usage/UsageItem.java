package com.arivan.amin.widget.applications.usage;

import org.jetbrains.annotations.NotNull;

public class UsageItem
{
    private String user;
    private String cpu;
    private String memory;
    private String command;
    
    private UsageItem ()
    {
        super();
    }
    
    private UsageItem (String user, String cpu, String memory, String command)
    {
        super();
        this.user = user;
        this.cpu = cpu;
        this.memory = memory;
        this.command = command;
    }
    
    @NotNull
    public static UsageItem newInstance ()
    {
        return new UsageItem();
    }
    
    @NotNull
    public static UsageItem newInstance (String user, String cpu, String memory, String command)
    {
        return new UsageItem(user, cpu, memory, command);
    }
    
    public String getUser ()
    {
        return user;
    }
    
    public void setUser (String user)
    {
        this.user = user;
    }
    
    public String getCpu ()
    {
        return cpu;
    }
    
    public void setCpu (String cpu)
    {
        this.cpu = cpu;
    }
    
    public String getMemory ()
    {
        return memory;
    }
    
    public void setMemory (String memory)
    {
        this.memory = memory;
    }
    
    public String getCommand ()
    {
        return command;
    }
    
    public void setCommand (String command)
    {
        this.command = command;
    }
    
    @Override
    public String toString ()
    {
        return "UsageItem{" + "user='" + user + '\'' + ", cpu='" + cpu + '\'' + ", memory='" +
                memory + '\'' + ", command='" + command + '\'' + '}';
    }
}
