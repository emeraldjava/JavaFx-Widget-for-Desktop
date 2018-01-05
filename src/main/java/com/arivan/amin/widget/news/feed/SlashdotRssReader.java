package com.arivan.amin.widget.news.feed;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SlashdotRssReader implements RssReader
{
    private static final String SLASHDOT_RSS_FILE = "slashdot.xml";
    private final Logger logger = Logger.getLogger(getClass().getName());
    private List<Element> elementList;
    
    private SlashdotRssReader ()
    {
        super();
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
    private static RssItem createRssItem (@NotNull Node element)
    {
        List<Element> list = createList(element.getChildNodes());
        return RssItem.newInstance(list.get(0).getTextContent(), list.get(1).getTextContent(),
                list.get(2).getTagName());
    }
    
    private static List<Element> createList (@NotNull NodeList nodes)
    {
        return IntStream.range(0, nodes.getLength()).mapToObj(nodes::item)
                .filter(item -> item instanceof Element).map(item -> (Element) item)
                .collect(Collectors.toCollection(() -> new ArrayList<>(50)));
    }
    
    @Override
    public final void updateNewsData ()
    {
        // TODO 1/5/18 create a text file stream reader for offline use
        // try (InputStream stream = new URL("http://rss.slashdot.org/Slashdot/slashdot").openStream())
        try
        {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(Paths.get(SLASHDOT_RSS_FILE).toUri().toString());
            List<Element> tempList = createList(document.getDocumentElement().getChildNodes());
            elementList = tempList.stream().filter(e ->
            {
                return "item".equals(e.getTagName());
            }).collect(Collectors.toList());
        }
        catch (Exception e)
        {
            elementList = Collections.emptyList();
            logger.warning(e.getMessage());
        }
    }
    
    @Override
    public String toString ()
    {
        return "SlashdotRssReader{" + "elementList=" + elementList + '}';
    }
}
