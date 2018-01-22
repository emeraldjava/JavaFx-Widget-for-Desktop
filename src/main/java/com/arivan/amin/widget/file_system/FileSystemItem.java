package com.arivan.amin.widget.file_system;

public class FileSystemItem
{
    private String name;
    private String size;
    private String used;
    private String available;
    private double usedPercentage;
    
    private FileSystemItem (String name)
    {
        this.name = name;
    }
    
    private FileSystemItem (String name, String size, String used, String available,
            double usedPercentage)
    {
        this.name = name;
        this.size = size;
        this.used = used;
        this.available = available;
        this.usedPercentage = usedPercentage;
    }
    
    public static FileSystemItem newInstance (String name)
    {
        return new FileSystemItem(name);
    }
    
    public static FileSystemItem newInstance (String name, String size, String used,
            String available, double usedPercentage)
    {
        return new FileSystemItem(name, size, used, available, usedPercentage);
    }
    
    public String getName ()
    {
        return name;
    }
    
    public void setName (String name)
    {
        this.name = name;
    }
    
    public String getSize ()
    {
        return size;
    }
    
    public void setSize (String size)
    {
        this.size = size;
    }
    
    public String getUsed ()
    {
        return used;
    }
    
    public void setUsed (String used)
    {
        this.used = used;
    }
    
    public String getAvailable ()
    {
        return available;
    }
    
    public void setAvailable (String available)
    {
        this.available = available;
    }
    
    public double getUsedPercentage ()
    {
        return usedPercentage;
    }
    
    public void setUsedPercentage (double usedPercentage)
    {
        this.usedPercentage = usedPercentage;
    }
}
