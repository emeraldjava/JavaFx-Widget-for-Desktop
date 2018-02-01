package com.arivan.amin.widget.forecast;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.DatabaseReader.Builder;
import com.maxmind.geoip2.model.CityResponse;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;
import java.util.logging.Logger;

public class GeoLiteLocationProvider implements LocationProvider
{
    private static final String LOCATION_FILE = "location.txt";
    public static final String TEMP_DATABASE_FILE = "tempDatabase.mmdb";
    public static final String DATABASE_FILE_NAME = "/database.mmdb";
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final Path locationFilePath;
    private File databaseFile;
    
    private GeoLiteLocationProvider ()
    {
        locationFilePath = Paths.get(LOCATION_FILE);
        getDatabaseLocationFile();
        updateData();
    }
    
    private void getDatabaseLocationFile ()
    {
        try (InputStream stream = getClass().getResourceAsStream(DATABASE_FILE_NAME))
        {
            Path tempPath = Paths.get(TEMP_DATABASE_FILE);
            Files.write(tempPath, stream.readAllBytes());
            databaseFile = tempPath.toFile();
        }
        catch (Exception e)
        {
            logger.warning(e.getMessage());
        }
    }
    
    public static GeoLiteLocationProvider newInstance ()
    {
        return new GeoLiteLocationProvider();
    }
    
    private void updateData ()
    {
        try (DatabaseReader databaseReader = new Builder(databaseFile).build())
        {
            CityResponse response = databaseReader.city(InetAddress.getByName(getPublicIp()));
            List<String> data =
                    List.of(response.getCountry().getName(), response.getCity().getName(),
                            response.getLocation().getTimeZone(),
                            String.valueOf(response.getLocation().getLatitude()),
                            String.valueOf(response.getLocation().getLongitude()));
            Files.write(locationFilePath, data);
        }
        catch (Exception e)
        {
            logger.warning(e.getMessage());
        }
    }
    
    private String getPublicIp ()
    {
        try (InputStream stream = new URL("http://checkip.amazonaws.com/").openStream())
        {
            return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        }
        catch (Exception ignored)
        {
            return "";
        }
    }
    
    @Override
    public String countryName ()
    {
        try
        {
            return Files.readAllLines(locationFilePath).get(0);
        }
        catch (IOException e)
        {
            logger.warning(e.getMessage());
            return "";
        }
    }
    
    @Override
    public String cityName ()
    {
        try
        {
            return Files.readAllLines(locationFilePath).get(1);
        }
        catch (IOException e)
        {
            logger.warning(e.getMessage());
            return "";
        }
    }
    
    @Override
    public String timezone ()
    {
        try
        {
            return Files.readAllLines(locationFilePath).get(2);
        }
        catch (IOException e)
        {
            logger.warning(e.getMessage());
            return "";
        }
    }
    
    @Override
    public double latitude ()
    {
        try
        {
            return Double.parseDouble(Files.readAllLines(locationFilePath).get(3));
        }
        catch (IOException e)
        {
            logger.warning(e.getMessage());
            return 0;
        }
    }
    
    @Override
    public double longitude ()
    {
        try
        {
            return Double.parseDouble(Files.readAllLines(locationFilePath).get(4));
        }
        catch (IOException e)
        {
            logger.warning(e.getMessage());
            return 0;
        }
    }
}
