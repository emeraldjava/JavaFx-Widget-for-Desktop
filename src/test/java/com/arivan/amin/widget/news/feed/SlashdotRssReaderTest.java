package com.arivan.amin.widget.news.feed;

import org.junit.Before;
import org.junit.Test;

public class SlashdotRssReaderTest
{
    RssReader rssReader;
    @Before
    public void setUp () throws Exception
    {
        rssReader = SlashdotRssReader.newInstance();
    }
    
    @Test
    public void newsList ()
    {
        rssReader.newsList();
    }
}
