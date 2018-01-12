package com.arivan.amin.widget.news.feed;

import javafx.animation.*;
import javafx.beans.property.DoubleProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.logging.Logger;

public class RssReaderBox extends VBox
{
    private static final int MINUTES_BETWEEN_RSS_UPDATES = 20;
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final RssReader rssReader;
    
    private RssReaderBox (DoubleProperty parentWidth, DoubleProperty parentHeight)
    {
        super();
        prefWidthProperty().bind(parentWidth.multiply(0.5));
        prefHeightProperty().bind(parentHeight);
        rssReader = SlashdotRssReader.newInstance();
        setSpacing(10);
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), e ->
        {
            updateValues();
        }));
        timeline.getKeyFrames().add(new KeyFrame(Duration.minutes(MINUTES_BETWEEN_RSS_UPDATES)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    
    @NotNull
    public static RssReaderBox newInstance (DoubleProperty parentWidth, DoubleProperty parentHeight)
    {
        return new RssReaderBox(parentWidth, parentHeight);
    }
    
    private void updateValues ()
    {
        getChildren().clear();
        getChildren().add(new Label("Feed from Slashdot"));
        List<RssItem> items = rssReader.newsList();
        Label label = new Label();
        label.setWrapText(true);
        label.getStyleClass().add("rss-item");
        for (int i = 0; i < items.size(); i++)
        {
            label.setText(label.getText() + (i + 1) + "-- " + items.get(i).getTitle() +
                    System.lineSeparator() + System.lineSeparator());
        }
        getChildren().add(label);
    }
    
    @Override
    public String toString ()
    {
        return "RssReaderBox{" + "rssReader=" + rssReader + '}';
    }
}
