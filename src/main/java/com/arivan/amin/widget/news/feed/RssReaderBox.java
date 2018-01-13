package com.arivan.amin.widget.news.feed;

import javafx.animation.*;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.logging.Logger;

public class RssReaderBox extends VBox
{
    private static final int MINUTES_BETWEEN_RSS_UPDATES = 10;
    private final Logger logger = Logger.getLogger(getClass().getName());
    private RssReader rssReader;
    
    private RssReaderBox (DoubleProperty parentWidth, DoubleProperty parentHeight)
    {
        bindBoxSizeToParent(parentWidth, parentHeight);
        determineRssReader();
        setSpacing(5);
        fetchNewsPeriodically();
    }
    
    private void fetchNewsPeriodically ()
    {
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), this::newsUpdateHandler));
        timeline.getKeyFrames().add(new KeyFrame(Duration.minutes(MINUTES_BETWEEN_RSS_UPDATES)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    
    private void bindBoxSizeToParent (DoubleProperty parentWidth, DoubleProperty parentHeight)
    {
        prefWidthProperty().bind(parentWidth.multiply(0.4));
        prefHeightProperty().bind(parentHeight);
    }
    
    private void determineRssReader ()
    {
        rssReader = SlashdotRssReader.newInstance();
    }
    
    @NotNull
    public static RssReaderBox newInstance (DoubleProperty parentWidth, DoubleProperty parentHeight)
    {
        return new RssReaderBox(parentWidth, parentHeight);
    }
    
    @Override
    public String toString ()
    {
        return "RssReaderBox{" + "rssReader=" + rssReader + '}';
    }
    
    private void newsUpdateHandler (ActionEvent e)
    {
        clearBox();
        List<RssItem> items = rssReader.newsList();
        Label label = createNewsLabel();
        addNewsToLabel(items, label);
        getChildren().add(label);
    }
    
    private void addNewsToLabel (List<RssItem> items, Label label)
    {
        for (int i = 0; i < items.size(); i++)
        {
            label.setText(label.getText() + (i + 1) + "-- " + items.get(i).getTitle() +
                    System.lineSeparator() + System.lineSeparator());
        }
    }
    
    @NotNull
    private Label createNewsLabel ()
    {
        Label label = new Label();
        label.setWrapText(true);
        label.getStyleClass().add("rss-item");
        return label;
    }
    
    private void clearBox ()
    {
        getChildren().clear();
        getChildren().add(new Label("Feed from Slashdot"));
    }
}
