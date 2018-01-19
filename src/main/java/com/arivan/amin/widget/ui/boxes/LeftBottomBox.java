package com.arivan.amin.widget.ui.boxes;

import com.arivan.amin.widget.news.feed.RssReaderBox;
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
        getChildren().add(RssReaderBox.newInstance(prefWidthProperty(), prefHeightProperty()));
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
