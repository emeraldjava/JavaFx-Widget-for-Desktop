package com.arivan.amin.widget;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public final class PropertiesManager
{
    private static final String WIDTH_KEY = "width";
    private static final String HEIGHT_KEY = "height";
    private static final String X_KEY = "x";
    private static final String Y_KEY = "y";
    private static final String PROPERTY_FILE = "widget.properties";
    private static final int DEFAULT_WIDTH = 1200;
    private static final int DEFAULT_HEIGHT = 700;
    private static final int DEFAULT_X = 10;
    private static final int DEFAULT_Y = 10;
    private final Logger logger = Logger.getLogger(getClass().getName() + ".log");
    private int width;
    private int height;
    private int x;
    private int y;
    
    private PropertiesManager ()
    {
        super();
        attachHandler();
        loadProperties();
    }
    
    @NotNull
    public static PropertiesManager newInstance ()
    {
        return new PropertiesManager();
    }
    
    public void storeValues ()
    {
        try
        {
            updateProperties();
        }
        catch (IOException e)
        {
            logger.warning(e.getMessage());
        }
    }
    
    private void updateProperties () throws IOException
    {
        Properties properties = new Properties();
        properties.setProperty(WIDTH_KEY, String.valueOf(width));
        properties.setProperty(HEIGHT_KEY, String.valueOf(height));
        properties.setProperty(X_KEY, String.valueOf(x));
        properties.setProperty(Y_KEY, String.valueOf(y));
        properties.store(getOutputStream(), "");
    }
    
    @NotNull
    private static FileOutputStream getOutputStream () throws FileNotFoundException
    {
        return new FileOutputStream(PROPERTY_FILE);
    }
    
    private void loadProperties ()
    {
        try
        {
            Properties properties = new Properties();
            properties.load(getInputStream());
            width = Integer.parseInt(properties.getProperty(WIDTH_KEY));
            height = Integer.parseInt(properties.getProperty(HEIGHT_KEY));
            x = Integer.parseInt(properties.getProperty(X_KEY));
            y = Integer.parseInt(properties.getProperty(Y_KEY));
        }
        catch (Exception e)
        {
            width = DEFAULT_WIDTH;
            height = DEFAULT_HEIGHT;
            x = DEFAULT_X;
            y = DEFAULT_Y;
            logger.warning(e.getMessage());
        }
    }
    
    @NotNull
    private static FileInputStream getInputStream () throws FileNotFoundException
    {
        return new FileInputStream(PROPERTY_FILE);
    }
    
    @Contract (pure = true)
    public int getWidth ()
    {
        return width;
    }
    
    public void setWidth (int width)
    {
        if (width < 1)
        {
            throw new IllegalArgumentException();
        }
        this.width = width;
    }
    
    @Contract (pure = true)
    public int getHeight ()
    {
        return height;
    }
    
    public void setHeight (int height)
    {
        if (height < 0)
        {
            throw new IllegalArgumentException();
        }
        this.height = height;
    }
    
    @Contract (pure = true)
    public int getX ()
    {
        return x;
    }
    
    public void setX (int x)
    {
        this.x = x;
    }
    
    @Contract (pure = true)
    public int getY ()
    {
        return y;
    }
    
    public void setY (int y)
    {
        this.y = y;
    }
    
    private void attachHandler ()
    {
        try
        {
            logger.addHandler(new FileHandler());
        }
        catch (IOException e)
        {
            logger.warning(e.getMessage());
        }
    }
    
    @Override
    public String toString ()
    {
        return "PropertiesManager{" + "width=" + width + ", height=" + height + '}';
    }
}
