package com.arivan.amin.widget.news_feed;

import javafx.animation.*;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.logging.Logger;

public class RssReaderBox extends VBox
{
    private static final double RSS_BOX_WIDTH = 0.4;
    private final Logger logger = Logger.getLogger(getClass().getName());
    private static final int MINUTES_BETWEEN_RSS_UPDATES = 10;
    private RssReader rssReader;
    
    private RssReaderBox (DoubleProperty parentWidth, DoubleProperty parentHeight)
    {
        bindBoxSizeToParent(parentWidth, parentHeight);
        determineRssReader();
        setBoxProperties();
        fetchNewsPeriodically();
    }
    
    private void setBoxProperties ()
    {
        setSpacing(5);
    }
    
    private void fetchNewsPeriodically ()
    {
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), this::newsUpdateHandler));
        timeline.getKeyFrames().add(new KeyFrame(Duration.minutes(MINUTES_BETWEEN_RSS_UPDATES)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    
    @SuppressWarnings ("TypeMayBeWeakened")
    private void bindBoxSizeToParent (DoubleProperty parentWidth, DoubleProperty parentHeight)
    {
        prefWidthProperty().bind(parentWidth.multiply(RSS_BOX_WIDTH));
        prefHeightProperty().bind(parentHeight);
    }
    
    private void determineRssReader ()
    {
        rssReader = SlashdotRssReader.newInstance();
    }
    
    public static RssReaderBox newInstance (DoubleProperty parentWidth, DoubleProperty parentHeight)
    {
        return new RssReaderBox(parentWidth, parentHeight);
    }
    
    private void newsUpdateHandler (ActionEvent e)
    {
        clearBox();
        Label label = createNewsLabel();
        addNewsToLabel(label);
        getChildren().add(label);
    }
    
    private void addNewsToLabel (Label label)
    {
        rssReader.newsList().forEach(rssItem ->
        {
            label.setText(label.getText() + rssItem.getTitle() + System.lineSeparator() +
                    System.lineSeparator());
        });
    }
    
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
    
    @Override
    public String toString ()
    {
        return "RssReaderBox{" + "rssReader=" + rssReader + '}';
    }
}
