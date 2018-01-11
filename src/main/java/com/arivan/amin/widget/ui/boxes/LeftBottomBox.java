package com.arivan.amin.widget.ui.boxes;

import com.arivan.amin.widget.news.feed.RssReaderBox;
import com.arivan.amin.widget.power.BatteryStateBox;
import com.arivan.amin.widget.todo.TodoItemsBox;
import javafx.beans.property.DoubleProperty;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;

public class LeftBottomBox extends HBox
{
    private LeftBottomBox (@NotNull DoubleProperty parentWidth,
            @NotNull DoubleProperty parentHeight)
    {
        super();
        // todo show updates available
        // todo show internet connection status 
        // todo show wakatime chart
        // todo show gmail status
        // todo show article from wikipidia
        // todo show cpu temp and fan status
        
        prefWidthProperty().bind(parentWidth);
        prefHeightProperty().bind(parentHeight.multiply(0.5));
        VBox rssVBox = new VBox();
        VBox todoVBox = new VBox();
        getChildren().add(rssVBox);
        getChildren().add(BatteryStateBox.newInstance(prefWidthProperty(), prefHeightProperty()));
        getChildren().add(todoVBox);
        rssVBox.getChildren().add(RssReaderBox.newInstance());
        todoVBox.getChildren().add(TodoItemsBox.newInstance());
        rssVBox.prefWidthProperty().bind(prefWidthProperty().multiply(0.5));
        todoVBox.prefWidthProperty().bind(prefWidthProperty().multiply(0.2));
        rssVBox.prefHeightProperty().bind(heightProperty());
        todoVBox.prefHeightProperty().bind(heightProperty());
    }
    
    public static LeftBottomBox newInstance (@NotNull DoubleProperty parentWidthProperty,
            @NotNull DoubleProperty parentHeightProperty)
    {
        return new LeftBottomBox(parentWidthProperty, parentHeightProperty);
    }
}
