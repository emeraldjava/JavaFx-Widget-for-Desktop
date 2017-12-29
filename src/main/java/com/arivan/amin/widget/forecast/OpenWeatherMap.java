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
    
    @Override
    public String weatherCondition ()
    {
        StringBuilder builder = new StringBuilder(10);
        elementList.forEach(e ->
        {
            if (e.getTagName().equals("forecast"))
            {
                List<Element> forecastNodes = createList(e.getChildNodes());
                builder.append(extractWeatherCondition(forecastNodes.get(0)));
            }
        });
        return builder.toString();
    }
    
    @NotNull
    private static String extractWeatherCondition (Element element)
    {
        StringBuilder builder = new StringBuilder(10);
        List<Element> list = createList(element.getChildNodes());
        list.stream().filter(e ->
        {
            return e.getTagName().equals("symbol");
        }).forEach(e ->
        {
            builder.append(e.getAttribute("name"));
        });
        return builder.toString();
    }
    
    @Override
    public String precipitationType ()
    {
        return null;
    }
    
    @Override
    public String precipitationValue ()
    {
        return null;
    }
    
    @Override
    public String windDirection ()
    {
        return null;
    }
    
    @Override
    public int windsSpeed ()
    {
        return 0;
    }
    
    @Override
    public String windName ()
    {
        return null;
    }
    
    @Override
    public String temperatureUnit ()
    {
        return null;
    }
    
    @Override
    public String temperatureValue ()
    {
        return null;
    }
    
    @Override
    public String pressureUnit ()
    {
        return null;
    }
    
    @Override
    public String pressureValue ()
    {
        return null;
    }
    
    @Override
    public String humidityValue ()
    {
        return null;
    }
    
    @Override
    public String cloudsName ()
    {
        return null;
    }
    
    @Override
    public String cloudsRate ()
    {
        return null;
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
