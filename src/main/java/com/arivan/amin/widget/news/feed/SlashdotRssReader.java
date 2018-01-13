package com.arivan.amin.widget.news.feed;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.*;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SlashdotRssReader implements RssReader
{
    private final Logger logger = Logger.getLogger(getClass().getName());
    private static final String SLASHDOT_RSS_FILE = "slashdot.xml";
    private List<Element> elementList;
    
    private SlashdotRssReader ()
    {
        updateNewsData();
    }
    
    @NotNull
    public static SlashdotRssReader newInstance ()
    {
        return new SlashdotRssReader();
    }
    
    @Override
    public List<RssItem> newsList ()
    {
        List<RssItem> items = new ArrayList<>(50);
        elementList.forEach(e ->
        {
            items.add(createRssItem(e));
        });
        return items;
    }
    
    @NotNull
    private RssItem createRssItem (@NotNull Node element)
    {
        List<Element> list = createList(element.getChildNodes());
        return RssItem.newInstance(list.get(0).getTextContent(), list.get(1).getTextContent(),
                list.get(2).getTagName());
    }
    
    private List<Element> createList (@NotNull NodeList nodes)
    {
        return IntStream.range(0, nodes.getLength()).mapToObj(nodes::item)
                .filter(item -> item instanceof Element).map(item -> (Element) item)
                .collect(Collectors.toCollection(() -> new ArrayList<>(50)));
    }
    
    @Override
    public void updateNewsData ()
    {
        Path path = Paths.get(SLASHDOT_RSS_FILE);
        try (InputStream stream = new URL("http://rss.slashdot.org/Slashdot/slashdot").openStream())
        {
            Files.write(path, stream.readAllBytes());
        }
        catch (Exception e)
        {
            elementList = Collections.emptyList();
            logger.warning(e.getMessage());
        }
        createElementsList(path);
    }
    
    private void createElementsList (Path path)
    {
        try
        {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(path.toUri().toString());
            List<Element> tempList = createList(document.getDocumentElement().getChildNodes());
            elementList = tempList.stream().filter(e ->
            {
                return "item".equals(e.getTagName());
            }).collect(Collectors.toList());
        }
        catch (Exception e)
        {
            logger.warning(e.getMessage());
        }
    }
    
    @Override
    public String toString ()
    {
        return "SlashdotRssReader{" + "elementList=" + elementList + '}';
    }
}
