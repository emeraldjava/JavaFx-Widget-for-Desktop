package com.arivan.amin.widget.news.feed;

import org.jetbrains.annotations.NotNull;

public class RssItem
{
    private String title;
    private String link;
    private String description;
    
    private RssItem ()
    {
        super();
    }
    
    private RssItem (String title, String link, String description)
    {
        super();
        this.title = title;
        this.link = link;
        this.description = description;
    }
    
    @NotNull
    public static RssItem newInstance ()
    {
        return new RssItem();
    }
    
    @NotNull
    public static RssItem newInstance (String title, String link, String description)
    {
        return new RssItem(title, link, description);
    }
    
    public String getTitle ()
    {
        return title;
    }
    
    public void setTitle (String title)
    {
        this.title = title;
    }
    
    public String getLink ()
    {
        return link;
    }
    
    public void setLink (String link)
    {
        this.link = link;
    }
    
    public String getDescription ()
    {
        return description;
    }
    
    public void setDescription (String description)
    {
        this.description = description;
    }
}
