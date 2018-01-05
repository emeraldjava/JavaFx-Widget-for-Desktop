package com.arivan.amin.widget.news.feed;

import javafx.scene.layout.Pane;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public class RssReaderPane extends Pane
{
    private final Logger logger = Logger.getLogger(getClass().getName());
    
    private RssReaderPane (Pane container)
    {
        super();
        // TODO 1/5/18 create reader pane
    }
    
    @NotNull
    public static RssReaderPane newInstance (Pane container)
    {
        return new RssReaderPane(container);
    }
}
