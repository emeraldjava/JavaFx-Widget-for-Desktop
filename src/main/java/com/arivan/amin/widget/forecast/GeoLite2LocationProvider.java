package com.arivan.amin.widget.forecast;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.DatabaseReader.Builder;
import com.maxmind.geoip2.model.CityResponse;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class GeoLite2LocationProvider implements GeoLocationProvider
{
    private static final String GEO_LITE2_CITY_DATABASE_FILE = "GeoLite2-City.mmdb";
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final Path databaseFilePath;
    private final CityResponse response;
    
    private GeoLite2LocationProvider ()
    {
        databaseFilePath = Paths.get(GEO_LITE2_CITY_DATABASE_FILE);
        response = getResponse();
    }
    
    public static GeoLite2LocationProvider newInstance ()
    {
        return new GeoLite2LocationProvider();
    }
    
    private CityResponse getResponse ()
    {
        try (DatabaseReader databaseReader = new Builder(databaseFilePath.toFile()).build())
        {
            return databaseReader.city(InetAddress.getByName(getPublicIp()));
        }
        catch (Exception e)
        {
            logger.warning(e.getMessage());
            return null;
        }
    }
    
    private String getPublicIp ()
    {
        try (InputStream stream = new URL("http://checkip.amazonaws.com/").openStream())
        {
            return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        }
        catch (IOException e)
        {
            logger.warning(e.getMessage());
            return "";
        }
    }
    
    @Override
    public String countryName ()
    {
        return response.getCountry().getName();
    }
    
    @Override
    public String cityName ()
    {
        return response.getCity().getName();
    }
    
    @Override
    public double latitude ()
    {
        return response.getLocation().getLatitude();
    }
    
    @Override
    public double longitude ()
    {
        return response.getLocation().getLongitude();
    }
    
    @Override
    public String timezone ()
    {
        return response.getLocation().getTimeZone();
    }
    
    @Override
    public String toString ()
    {
        return "GeoLite2LocationProvider{" + "databaseFilePath=" + databaseFilePath +
                ", response=" + response + '}';
    }
}
