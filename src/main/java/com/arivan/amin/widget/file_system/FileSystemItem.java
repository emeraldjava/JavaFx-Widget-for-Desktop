package com.arivan.amin.widget.file_system;

/**
 * The type File system item.
 */
@SuppressWarnings ({ "PublicMethodNotExposedInInterface", "ClassWithoutLogger" })
public class FileSystemItem
{
    private String name;
    private String size;
    private String used;
    private String available;
    private double usedPercentage;
    
    private FileSystemItem ()
    {
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
    
    /**
     * New instance file system item.
     *
     * @return the file system item
     */
    public static FileSystemItem newInstance ()
    {
        return new FileSystemItem();
    }
    
    /**
     * New instance file system item.
     *
     * @param name the name of the partition.
     * @param size the total size of the partition.
     * @param used the used amount of space.
     * @param available the available space.
     * @param usedPercentage the used percentage between 0 and 1 
     * where 1 indicates a partition with no available space.
     *
     * @return the file system item
     */
    @SuppressWarnings ("MethodWithTooManyParameters")
    public static FileSystemItem newInstance (String name, String size, String used,
            String available, double usedPercentage)
    {
        return new FileSystemItem(name, size, used, available, usedPercentage);
    }
    
    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName ()
    {
        return name;
    }
    
    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName (String name)
    {
        this.name = name;
    }
    
    /**
     * Gets size.
     *
     * @return the size
     */
    public String getSize ()
    {
        return size;
    }
    
    /**
     * Sets size.
     *
     * @param size the size
     */
    public void setSize (String size)
    {
        this.size = size;
    }
    
    /**
     * Gets used.
     *
     * @return the used
     */
    public String getUsed ()
    {
        return used;
    }
    
    /**
     * Sets used.
     *
     * @param used the used
     */
    public void setUsed (String used)
    {
        this.used = used;
    }
    
    /**
     * Gets available.
     *
     * @return the available
     */
    public String getAvailable ()
    {
        return available;
    }
    
    /**
     * Sets available.
     *
     * @param available the available
     */
    public void setAvailable (String available)
    {
        this.available = available;
    }
    
    /**
     * Gets used percentage.
     *
     * @return the used percentage
     */
    public double getUsedPercentage ()
    {
        return usedPercentage;
    }
    
    /**
     * Sets used percentage.
     *
     * @param usedPercentage the used percentage
     */
    public void setUsedPercentage (double usedPercentage)
    {
        this.usedPercentage = usedPercentage;
    }
    
    @Override
    public String toString ()
    {
        return "FileSystemItem{" + "name='" + name + '\'' + ", size='" + size + '\'' + ", used='" +
                used + '\'' + ", available='" + available + '\'' + ", usedPercentage=" +
                usedPercentage + '}';
    }
}
