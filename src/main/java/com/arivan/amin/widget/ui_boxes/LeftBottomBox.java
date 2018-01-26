package com.arivan.amin.widget.ui_boxes;

import com.arivan.amin.widget.news_feed.RssReaderBox;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.scene.layout.HBox;

import java.util.logging.Logger;

public class LeftBottomBox extends HBox
{
    private static final double BOX_HEIGHT = 0.5;
    private final Logger logger = Logger.getLogger(getClass().getName());
    
    private LeftBottomBox (DoubleProperty parentWidth, DoubleProperty parentHeight)
    {
        prefWidthProperty().bind(parentWidth);
        prefHeightProperty().bind(parentHeight.multiply(BOX_HEIGHT));
        Platform.runLater(() ->
        {
            getChildren()
                    .add(0, RssReaderBox.newInstance(prefWidthProperty(), prefHeightProperty()));
        });
    }
    
    public static LeftBottomBox newInstance (DoubleProperty parentWidth,
            DoubleProperty parentHeight)
    {
        return new LeftBottomBox(parentWidth, parentHeight);
    }
    // todo show updates available
    // todo show internet connection isConnected and wireless statistics
    // todo show wakatime chart
    // todo show gmail is Connected
    // todo show article from wikipedia
    // todo show cpu temp and fan isConnected
}
