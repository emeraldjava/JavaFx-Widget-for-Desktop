package com.arivan.amin.widget.forecast;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.DatabaseReader.Builder;
import com.maxmind.geoip2.model.CityResponse;

import java.net.InetAddress;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class GeoLite2LocationProvider implements GeoLocationProvider
{
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final Path databaseFilePath;
    private final String publicIp;
    private final CityResponse cityResponse;
    
    private GeoLite2LocationProvider (String publicIp)
    {
        this.publicIp = publicIp;
        // databaseFilePath = Paths.get("src", "main", "resources", "GeoLite2-City.mmdb");
        databaseFilePath = Paths.get("GeoLite2- City.mmdb");
        cityResponse = getCityResponse();
    }
    
    public static GeoLite2LocationProvider newInstance (String publicIp)
    {
        return new GeoLite2LocationProvider(publicIp);
    }
    
    private CityResponse getCityResponse ()
    {
        try (DatabaseReader databaseReader = new Builder(databaseFilePath.toFile()).build())
        {
            return databaseReader.city(InetAddress.getByName(publicIp));
        }
        catch (Exception e)
        {
            logger.warning(e.getMessage());
            return null;
        }
    }
    
    @Override
    public String countryName ()
    {
        return cityResponse.getCountry().getName();
    }
    
    @Override
    public String cityName ()
    {
        return cityResponse.getCity().getName();
    }
    
    @Override
    public double latitude ()
    {
        return cityResponse.getLocation().getLatitude();
    }
    
    @Override
    public double longitude ()
    {
        return cityResponse.getLocation().getLongitude();
    }
    
    @Override
    public String toString ()
    {
        return "GeoLite2LocationProvider{" + "databaseFilePath=" + databaseFilePath +
                ", publicIp='" + publicIp + '\'' + ", cityResponse=" + cityResponse + '}';
    }
}
