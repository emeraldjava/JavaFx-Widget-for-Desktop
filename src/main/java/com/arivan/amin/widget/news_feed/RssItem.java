package com.arivan.amin.widget.news_feed;

@SuppressWarnings ({ "ClassWithoutLogger", "PublicMethodNotExposedInInterface" })
public class RssItem
{
    private String title;
    private String link;
    private String description;
    
    private RssItem ()
    {
    }
    
    private RssItem (String title, String link, String description)
    {
        this.title = title;
        this.link = link;
        this.description = description;
    }
    
    public static RssItem newInstance ()
    {
        return new RssItem();
    }
    
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
    
    @Override
    public String toString ()
    {
        return "RssItem{" + "title='" + title + '\'' + ", link='" + link + '\'' +
                ", description='" + description + '\'' + '}';
    }
}
