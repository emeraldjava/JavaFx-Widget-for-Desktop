package com.arivan.amin.widget.news_feed;

import java.util.List;

public interface RssReader
{
    List<RssItem> newsList ();
    
    void updateNewsData ();
}
