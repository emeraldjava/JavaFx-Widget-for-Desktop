package com.arivan.amin.widget.forecast;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.time.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.*;

public class OpenWeatherMap implements WeatherData
{
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final WeatherProvider weatherProvider;
    private List<Element> elementList;
    
    private OpenWeatherMap (WeatherProvider weatherProvider)
    {
        this.weatherProvider = weatherProvider;
    }
    
    public static OpenWeatherMap newInstance (WeatherProvider weatherProvider)
    {
        return new OpenWeatherMap(weatherProvider);
    }
    
    @Override
    public final void updateWeatherData ()
    {
        try
        {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(weatherProvider.getWeatherDataUri());
            elementList = createList(document.getDocumentElement().getChildNodes());
        }
        catch (Exception e)
        {
            elementList = Collections.emptyList();
            logger.warning(e.getMessage());
        }
    }
    
    private static List<Element> createList (NodeList nodes)
    {
        return IntStream.range(0, nodes.getLength()).mapToObj(nodes::item)
                .filter(item -> item instanceof Element).map(item -> (Element) item)
                .collect(Collectors.toCollection(() -> new ArrayList<>(50)));
    }
    
    @Override
    public String locationName ()
    {
        Element element =
                elementList.stream().filter(e -> "location".equals(e.getTagName())).findAny().get();
        return createList(element.getChildNodes()).stream()
                .filter(x -> "name".equals(x.getTagName())).map(Node::getTextContent)
                .collect(Collectors.joining());
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
    
    private String getSunAttribute (String attr)
    {
        return elementList.stream().filter(e -> "sun".equals(e.getTagName()))
                .map(e -> convertTimeZoneAndExtractTime(attr, e)).collect(Collectors.joining());
    }
    
    private String convertTimeZoneAndExtractTime (String attr, Element e)
    {
        String time = changeTimeZoneToLocal(e.getAttribute(attr));
        time = time.substring(time.indexOf('T') + 1, time.lastIndexOf(':'));
        return time;
    }
    
    private CharSequence getTimeNodeAttribute (int nodeNumber)
    {
        return elementList.stream().filter(e -> "forecast".equals(e.getTagName()))
                .map(e -> changeTimeZoneToLocal(
                        createList(e.getChildNodes()).get(nodeNumber).getAttribute("from")))
                .findAny().get();
    }
    
    private Collection<Element> getTimeNodes ()
    {
        return createList(
                elementList.stream().filter(e -> "forecast".equals(e.getTagName())).findAny().get()
                        .getChildNodes());
    }
    
    private String getTimeNodeChildAttribute (int nodeNumber, String tagName, String attribute)
    {
        List<Element> list = elementList.stream().filter(e -> "forecast".equals(e.getTagName()))
                .map(e -> createList(createList(e.getChildNodes()).get(nodeNumber).getChildNodes()))
                .findAny().get();
        return list.stream().filter(x -> tagName.equals(x.getTagName()))
                .map(x -> x.getAttribute(attribute)).collect(Collectors.joining());
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
    
    private List<String> getAttributesOfDaysFromNow (int dayNumber, String tagName,
            String attribute)
    {
        List<String> list = new ArrayList<>(50);
        LocalDateTime dayStart = getDayStart(dayNumber);
        LocalDateTime dayEnd = dayStart.plusDays(1);
        for (int i = 0; i < getTimeNodes().size(); i++)
        {
            LocalDateTime weatherDataTime =
                    LocalDateTime.parse(getTimeNodeAttribute(i)).plusMinutes(1);
            if (isWeatherDataForRequiredDay(dayStart, dayEnd, weatherDataTime))
            {
                list.add(getTimeNodeChildAttribute(i, tagName, attribute));
            }
        }
        return list;
    }
    
    @SuppressWarnings ("TypeMayBeWeakened")
    private boolean isWeatherDataForRequiredDay (LocalDateTime dayStart, LocalDateTime dayEnd,
            LocalDateTime weatherDataTime)
    {
        return weatherDataTime.isAfter(dayStart) && weatherDataTime.isBefore(dayEnd);
    }
    
    private LocalDateTime getDayStart (int dayNumber)
    {
        LocalDateTime dayStart = LocalDateTime.parse(changeTimeZoneToLocal(getTimeNodeAttribute(0)))
                .plusDays(dayNumber);
        dayStart = dayStart.minusHours(dayStart.getHour());
        return dayStart;
    }
    
    private int getMaxTemperature (int daysFromNow)
    {
        int maxTemp = -1000;
        List<String> list = getAttributesOfDaysFromNow(daysFromNow, "temperature", "value");
        for (String e : list)
        {
            int temp = Double.valueOf(e).intValue();
            if (temp > maxTemp)
            {
                maxTemp = temp;
            }
        }
        return maxTemp;
    }
    
    private int getMinTemperature (int daysFromNow)
    {
        int minTemp = 1000;
        List<String> list = getAttributesOfDaysFromNow(daysFromNow, "temperature", "value");
        for (String e : list)
        {
            int temp = Double.valueOf(e).intValue();
            if (temp < minTemp)
            {
                minTemp = temp;
            }
        }
        return minTemp;
    }
    
    private String getWeatherIcon (int daysFromNow)
    {
        List<String> list = getAttributesOfDaysFromNow(daysFromNow, "symbol", "var");
        List<String> collect = list.stream().map(e -> e.replace('n', 'd')).
                collect(Collectors.toList());
        Map<String, Long> set = collect.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        String max =
                Collections.max(set.entrySet(), Comparator.comparing(Entry::getValue)).getKey();
        return max + ".png";
    }
    
    @Override
    public String secondDayWeatherIcon ()
    {
        return getWeatherIcon(1);
    }
    
    @Override
    public int secondDayMaxTemperature ()
    {
        return getMaxTemperature(1);
    }
    
    @Override
    public int secondDayMinTemperature ()
    {
        return getMinTemperature(1);
    }
    
    @Override
    public String thirdDayWeatherIcon ()
    {
        return getWeatherIcon(2);
    }
    
    @Override
    public int thirdDayMaxTemperature ()
    {
        return getMaxTemperature(2);
    }
    
    @Override
    public int thirdDayMinTemperature ()
    {
        return getMinTemperature(2);
    }
    
    @Override
    public String fourthDayWeatherIcon ()
    {
        return getWeatherIcon(3);
    }
    
    @Override
    public int fourthDayMaxTemperature ()
    {
        return getMaxTemperature(3);
    }
    
    @Override
    public int fourthDayMinTemperature ()
    {
        return getMinTemperature(3);
    }
    
    @Override
    public String fifthDayWeatherIcon ()
    {
        return getWeatherIcon(4);
    }
    
    @Override
    public int fifthDayMaxTemperature ()
    {
        return getMaxTemperature(4);
    }
    
    @Override
    public int fifthDayMinTemperature ()
    {
        return getMinTemperature(4);
    }
    
    // todo timeZone Selection
    private static String changeTimeZoneToLocal (CharSequence dateTime)
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
    
    @Override
    public String toString ()
    {
        return "OpenWeatherMap{" + "weatherProvider=" + weatherProvider + ", elementList=" +
                elementList + '}';
    }
}
