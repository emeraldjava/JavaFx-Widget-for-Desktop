package com.arivan.amin.widget.forecast;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.time.*;
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
            if ("location".equals(e.getTagName()))
            {
                List<Element> list = createList(e.getChildNodes());
                list.stream().filter(x ->
                {
                    return "name".equals(x.getTagName());
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
                String time = changeTimeZoneToLocal(e.getAttribute(attr));
                time = time.substring(time.indexOf('T') + 1, time.lastIndexOf(':'));
                builder.append(time);
            }
        });
        return builder.toString();
    }
    
    @NotNull
    public String getTimeNodeAttribute (int nodeNumber, String attribute)
    {
        StringBuilder builder = new StringBuilder(10);
        elementList.stream().filter(e ->
        {
            return "forecast".equals(e.getTagName());
        }).forEach(e ->
        {
            Element element = createList(e.getChildNodes()).get(nodeNumber);
            builder.append(changeTimeZoneToLocal(element.getAttribute(attribute)));
        });
        return builder.toString();
    }
    
    private List<Element> getTimeNodes ()
    {
        List<Element> elements = Collections.emptyList();
        for (Element e : elementList)
        {
            if ("forecast".equals(e.getTagName()))
            {
                elements = createList(e.getChildNodes());
            }
        }
        return elements;
    }
    
    @NotNull
    private String getTimeNodeChildAttribute (int nodeNumber, String tagName, String attribute)
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
        return getTimeNodeChildAttribute(0, "symbol", "name");
    }
    
    @Override
    public String weatherIcon ()
    {
        return getTimeNodeChildAttribute(0, "symbol", "var") + ".png";
    }
    
    @Override
    public String precipitationType ()
    {
        return getTimeNodeChildAttribute(0, "precipitation", "type");
    }
    
    @Override
    public String precipitationValue ()
    {
        return getTimeNodeChildAttribute(0, "precipitation", "value");
    }
    
    @Override
    public String windDirection ()
    {
        return getTimeNodeChildAttribute(0, "windDirection", "name");
    }
    
    @Override
    public int windsSpeed ()
    {
        return Double.valueOf(getTimeNodeChildAttribute(0, "windSpeed", "mps")).intValue();
    }
    
    @Override
    public String windName ()
    {
        return getTimeNodeChildAttribute(0, "windSpeed", "name");
    }
    
    @Override
    public String temperatureUnit ()
    {
        return String.valueOf(
                getTimeNodeChildAttribute(0, "temperature", "unit").toUpperCase(Locale.ENGLISH)
                        .charAt(0));
    }
    
    @Override
    public int temperatureValue ()
    {
        String temp = getTimeNodeChildAttribute(0, "temperature", "value");
        return (int) Math.round(Double.valueOf(temp));
    }
    
    @Override
    public String pressureUnit ()
    {
        return getTimeNodeChildAttribute(0, "pressure", "unit");
    }
    
    @Override
    public String pressureValue ()
    {
        String pressure = getTimeNodeChildAttribute(0, "pressure", "value");
        return pressure.substring(0, pressure.indexOf('.'));
    }
    
    @Override
    public String humidityValue ()
    {
        return getTimeNodeChildAttribute(0, "humidity", "value");
    }
    
    @Override
    public String cloudsName ()
    {
        return getTimeNodeChildAttribute(0, "clouds", "value");
    }
    
    @Override
    public String cloudsRate ()
    {
        return getTimeNodeChildAttribute(0, "clouds", "all");
    }
    
    private int getMaxTemperatureForDayFromNow (int dayNumber)
    {
        int maxTemp = -1000;
        LocalDateTime secondDayStart =
                LocalDateTime.parse(changeTimeZoneToLocal(getTimeNodeAttribute(0, "from")))
                        .plusDays(dayNumber);
        secondDayStart = secondDayStart.minusHours(secondDayStart.getHour());
        LocalDateTime secondDayEnd = secondDayStart.plusDays(1);
        List<Element> timeNodes = getTimeNodes();
        for (int i = 0; i < timeNodes.size(); i++)
        {
            Element node = timeNodes.get(i);
            String from = getTimeNodeAttribute(i, "from");
            LocalDateTime localDateTime = LocalDateTime.parse(from).plusMinutes(1);
            if (localDateTime.isAfter(secondDayStart) && localDateTime.isBefore(secondDayEnd))
            {
                String temperatureString = getTimeNodeChildAttribute(i, "temperature", "value");
                int temp = Double.valueOf(temperatureString).intValue();
                if (temp > maxTemp)
                {
                    maxTemp = temp;
                }
            }
        }
        return maxTemp;
    }
    
    private int getMinTemperatureForDayFromNow (int dayNumber)
    {
        int minTemp = 1000;
        LocalDateTime secondDayStart =
                LocalDateTime.parse(changeTimeZoneToLocal(getTimeNodeAttribute(0, "from")))
                        .plusDays(dayNumber);
        secondDayStart = secondDayStart.minusHours(secondDayStart.getHour());
        LocalDateTime secondDayEnd = secondDayStart.plusDays(1);
        List<Element> timeNodes = getTimeNodes();
        for (int i = 0; i < timeNodes.size(); i++)
        {
            Element node = timeNodes.get(i);
            String from = getTimeNodeAttribute(i, "from");
            LocalDateTime localDateTime = LocalDateTime.parse(from).plusMinutes(1);
            if (localDateTime.isAfter(secondDayStart) && localDateTime.isBefore(secondDayEnd))
            {
                String temperatureString = getTimeNodeChildAttribute(i, "temperature", "value");
                int temp = Double.valueOf(temperatureString).intValue();
                if (temp < minTemp)
                {
                    minTemp = temp;
                }
            }
        }
        return minTemp;
    }
    
    @Override
    public String secondDayWeatherIcon ()
    {
        // TODO 1/2/18 calculate second day weather condition
        return "01d.png";
    }
    
    @Override
    public int secondDayMaxTemperature ()
    {
        return getMaxTemperatureForDayFromNow(1);
    }
    
    @Override
    public int secondDayMinTemperature ()
    {
        return getMinTemperatureForDayFromNow(1);
    }
    
    @Override
    public String thirdDayWeatherIcon ()
    {
        return "10d.png";
    }
    
    @Override
    public int thirdDayMaxTemperature ()
    {
        return getMaxTemperatureForDayFromNow(2);
    }
    
    @Override
    public int thirdDayMinTemperature ()
    {
        return getMinTemperatureForDayFromNow(2);
    }
    
    @Override
    public String fourthDayWeatherIcon ()
    {
        return "13n.png";
    }
    
    @Override
    public int fourthDayMaxTemperature ()
    {
        return getMaxTemperatureForDayFromNow(3);
    }
    
    @Override
    public int fourthDayMinTemperature ()
    {
        return getMinTemperatureForDayFromNow(3);
    }
    
    @Override
    public String fifthDayWeatherIcon ()
    {
        return "11d.png";
    }
    
    @Override
    public int fifthDayMaxTemperature ()
    {
        return getMaxTemperatureForDayFromNow(4);
    }
    
    @Override
    public int fifthDayMinTemperature ()
    {
        return getMinTemperatureForDayFromNow(4);
    }
    
    private String changeTimeZoneToLocal (CharSequence dateTime)
    {
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime);
        ZoneId utcZoneId = ZoneId.of("UTC");
        ZonedDateTime utcZonedDateTime = localDateTime.atZone(utcZoneId);
        ZoneId localZoneId = ZoneId.of("Asia/Baghdad");
        ZonedDateTime localZonedDateTime = utcZonedDateTime.withZoneSameInstant(localZoneId);
        String time = localZonedDateTime.toString();
        time = time.substring(0, time.lastIndexOf('+'));
        return time;
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
