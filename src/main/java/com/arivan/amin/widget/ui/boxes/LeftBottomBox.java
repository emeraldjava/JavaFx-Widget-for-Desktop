package com.arivan.amin.widget.ui.boxes;

import com.arivan.amin.widget.news.feed.RssReaderBox;
import javafx.beans.property.DoubleProperty;
import javafx.scene.layout.HBox;
import org.jetbrains.annotations.NotNull;

// todo show updates available
// todo show internet connection status and network statistics
// todo show wakatime chart
// todo show gmail status
// todo show article from wikipedia
// todo show cpu temp and fan status
public class LeftBottomBox extends HBox
{
    private LeftBottomBox (@NotNull DoubleProperty parentWidth,
            @NotNull DoubleProperty parentHeight)
    {
        super();
        prefWidthProperty().bind(parentWidth);
        prefHeightProperty().bind(parentHeight.multiply(0.5));
        getChildren().add(RssReaderBox.newInstance(prefWidthProperty(), prefHeightProperty()));
        // getChildren().add(WakaTimeBox.newInstance(prefWidthProperty(), prefHeightProperty()));
    }
    
    @NotNull
    public static LeftBottomBox newInstance (@NotNull DoubleProperty parentWidthProperty,
            @NotNull DoubleProperty parentHeightProperty)
    {
        return new LeftBottomBox(parentWidthProperty, parentHeightProperty);
    }
}
