package com.arivan.amin.widget.news.feed;

import java.util.List;

public interface RssReader
{
    List<RssItem> newsList ();
    
    void updateNewsData ();
}
