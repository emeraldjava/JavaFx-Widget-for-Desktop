package com.arivan.amin.widget.forecast;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class OpenWeatherMap implements WeatherData
{
    private final Logger logger = Logger.getLogger(getClass().getName());
    private WeatherProvider weatherProvider;
    private List<Element> elementList;
    
    private OpenWeatherMap ()
    {
        super();
        // weatherProvider = OpenWeatherMapProvider.newInstance();
        weatherProvider = TextFileWeatherProvider.newInstance();
        updateWeatherData();
    }
    
    @NotNull
    public static OpenWeatherMap newInstance ()
    {
        return new OpenWeatherMap();
    }
    
    @Override
    public final void updateWeatherData ()
    {
        try
        {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(weatherProvider.getWeatherDataStream());
            elementList = createList(document.getDocumentElement().getChildNodes());
        }
        catch (Exception e)
        {
            elementList = Collections.emptyList();
            logger.warning(e.getMessage());
        }
    }
    
    private static List<Element> createList (@NotNull NodeList nodes)
    {
        return IntStream.range(0, nodes.getLength()).mapToObj(nodes::item)
                .filter(item -> item instanceof Element).map(item -> (Element) item)
                .collect(Collectors.toCollection(() -> new ArrayList<>(50)));
    }
    
    @Override
    public String locationName ()
    {
        StringBuilder builder = new StringBuilder(10);
        elementList.forEach(e ->
        {
            if (e.getTagName().equals("location"))
            {
                List<Element> list = createList(e.getChildNodes());
                list.stream().filter(x ->
                {
                    return x.getTagName().equals("name");
                }).forEach(x ->
                {
                    builder.append(x.getTextContent());
                });
            }
        });
        return builder.toString();
    }
    
    @Override
    public String sunrise ()
    {
        return getSunAttribute("rise");
    }
    
    @Override
    public String sunset ()
    {
        return getSunAttribute("set");
    }
    
    @NotNull
    private String getSunAttribute (String attr)
    {
        StringBuilder builder = new StringBuilder(10);
        elementList.forEach(e ->
        {
            if ("sun".equals(e.getTagName()))
            {
                String rise = e.getAttribute(attr);
                rise = rise.substring(rise.indexOf('T') + 1, rise.lastIndexOf(':'));
                builder.append(rise);
            }
        });
        return builder.toString();
    }
    
    private String getTimeNodeAttribute (int nodeNumber, String tagName, String attribute)
    {
        StringBuilder builder = new StringBuilder(10);
        elementList.stream().filter(e ->
        {
            return "forecast".equals(e.getTagName());
        }).forEach(e ->
        {
            List<Element> list =
                    createList(createList(e.getChildNodes()).get(nodeNumber).getChildNodes());
            list.stream().filter(x ->
            {
                return tagName.equals(x.getTagName());
            }).forEach(x ->
            {
                builder.append(x.getAttribute(attribute));
            });
        });
        return builder.toString();
    }
    
    @Override
    public String weatherCondition ()
    {
        return getTimeNodeAttribute(0, "symbol", "name");
    }
    
    @Override
    public String weatherIcon ()
    {
        return getTimeNodeAttribute(0, "symbol", "var") + ".png";
    }
    
    @Override
    public String precipitationType ()
    {
        return getTimeNodeAttribute(0, "precipitation", "type");
    }
    
    @Override
    public String precipitationValue ()
    {
        return getTimeNodeAttribute(0, "precipitation", "value");
    }
    
    @Override
    public String windDirection ()
    {
        return getTimeNodeAttribute(0, "windDirection", "name");
    }
    
    @Override
    public int windsSpeed ()
    {
        return Double.valueOf(getTimeNodeAttribute(0, "windSpeed", "mps")).intValue();
    }
    
    @Override
    public String windName ()
    {
        return getTimeNodeAttribute(0, "windSpeed", "name");
    }
    
    @Override
    public String temperatureUnit ()
    {
        return String.valueOf(
                getTimeNodeAttribute(0, "temperature", "unit").toUpperCase(Locale.ENGLISH)
                        .charAt(0));
    }
    
    @Override
    public int temperatureValue ()
    {
        String temp = getTimeNodeAttribute(0, "temperature", "value");
        return (int) Math.round(Double.valueOf(temp));
    }
    
    @Override
    public String pressureUnit ()
    {
        return getTimeNodeAttribute(0, "pressure", "unit");
    }
    
    @Override
    public String pressureValue ()
    {
        String pressure = getTimeNodeAttribute(0, "pressure", "value");
        return pressure.substring(0, pressure.indexOf('.'));
    }
    
    @Override
    public String humidityValue ()
    {
        return getTimeNodeAttribute(0, "humidity", "value");
    }
    
    @Override
    public String cloudsName ()
    {
        return getTimeNodeAttribute(0, "clouds", "value");
    }
    
    @Override
    public String cloudsRate ()
    {
        return getTimeNodeAttribute(0, "clouds", "all");
    }
    
    @Override
    public String secondDayWeather ()
    {
        return null;
    }
    
    @Override
    public int secondDayMaxTemperature ()
    {
        return 0;
    }
    
    @Override
    public int secondMinTemperature ()
    {
        return 0;
    }
    
    @Override
    public String thirdDayWeather ()
    {
        return null;
    }
    
    @Override
    public int thirdDayMaxTemperature ()
    {
        return 0;
    }
    
    @Override
    public int thirdMinTemperature ()
    {
        return 0;
    }
    
    @Override
    public String fourthDayWeather ()
    {
        return null;
    }
    
    @Override
    public int fourthDayMaxTemperature ()
    {
        return 0;
    }
    
    @Override
    public int fourthMinTemperature ()
    {
        return 0;
    }
    
    public void setWeatherProvider (WeatherProvider weatherProvider)
    {
        this.weatherProvider = weatherProvider;
    }
    
    public WeatherProvider getWeatherProvider ()
    {
        return weatherProvider;
    }
    
    @Override
    public String toString ()
    {
        return "OpenWeatherMap{" + "weatherProvider=" + weatherProvider + ", elementList=" +
                elementList + '}';
    }
}
