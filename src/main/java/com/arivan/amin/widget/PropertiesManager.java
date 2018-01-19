package com.arivan.amin.widget;

import java.io.*;
import java.util.Properties;
import java.util.logging.Logger;

@SuppressWarnings ({ "PublicMethodNotExposedInInterface", "OverlyLongMethod",
        "BooleanMethodNameMustStartWithQuestion", "OverlyBroadThrowsClause" })
public final class PropertiesManager
{
    private final Logger logger = Logger.getLogger(getClass().getName());
    private static final String WIDTH_KEY = "width";
    private static final String HEIGHT_KEY = "height";
    private static final String X_KEY = "x";
    private static final String Y_KEY = "y";
    private static final String IS_MAXIMIZED_KEY = "is-maximized";
    private static final String PROPERTY_FILE = "widget.properties";
    private static final int DEFAULT_WIDTH = 1200;
    private static final int DEFAULT_HEIGHT = 700;
    private static final int DEFAULT_X = 10;
    private static final int DEFAULT_Y = 10;
    private static final boolean DEFAULT_MAXIMIZED_STATE = false;
    private int width;
    private int height;
    private int stageX;
    private int stageY;
    private boolean isStageMaximized;
    
    private PropertiesManager ()
    {
        loadProperties();
    }
    
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
        properties.setProperty(X_KEY, String.valueOf(stageX));
        properties.setProperty(Y_KEY, String.valueOf(stageY));
        properties.setProperty(IS_MAXIMIZED_KEY, String.valueOf(isStageMaximized));
        properties.store(getOutputStream(), "");
    }
    
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
            stageX = Integer.parseInt(properties.getProperty(X_KEY));
            stageY = Integer.parseInt(properties.getProperty(Y_KEY));
            isStageMaximized = Boolean.parseBoolean(properties.getProperty(IS_MAXIMIZED_KEY));
        }
        catch (Exception e)
        {
            width = DEFAULT_WIDTH;
            height = DEFAULT_HEIGHT;
            stageX = DEFAULT_X;
            stageY = DEFAULT_Y;
            isStageMaximized = DEFAULT_MAXIMIZED_STATE;
            logger.warning(e.getMessage());
        }
    }
    
    private static FileInputStream getInputStream () throws FileNotFoundException
    {
        return new FileInputStream(PROPERTY_FILE);
    }
    
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
    
    public int getStageX ()
    {
        return stageX;
    }
    
    public void setStageX (int stageX)
    {
        this.stageX = stageX;
    }
    
    public int getStageY ()
    {
        return stageY;
    }
    
    public void setStageY (int stageY)
    {
        this.stageY = stageY;
    }
    
    public boolean getIsStageMaximized ()
    {
        return isStageMaximized;
    }
    
    public void setIsStageMaximized (boolean stageMaximized)
    {
        isStageMaximized = stageMaximized;
    }
    
    @Override
    public String toString ()
    {
        return "PropertiesManager{" + "width=" + width + ", height=" + height + ", stageX=" +
                stageX + ", stageY=" + stageY + ", getIsStageMaximized=" + isStageMaximized + '}';
    }
}
