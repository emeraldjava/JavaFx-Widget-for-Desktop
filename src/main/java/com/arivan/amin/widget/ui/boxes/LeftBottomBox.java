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
    private LeftBottomBox (@NotNull DoubleProperty parentWidthProperty,
            @NotNull DoubleProperty parentHeightProperty)
    {
        super();
        prefWidthProperty().bind(parentWidthProperty);
        prefHeightProperty().bind(parentHeightProperty.multiply(0.5));
        VBox rssVBox = new VBox();
        VBox batteryStateVBox = new VBox();
        VBox todoVBox = new VBox();
        getChildren().addAll(rssVBox, batteryStateVBox, todoVBox);
        rssVBox.getChildren().add(RssReaderBox.newInstance());
        batteryStateVBox.getChildren().add(BatteryStateBox.newInstance());
        todoVBox.getChildren().add(TodoItemsBox.newInstance());
        rssVBox.prefWidthProperty().bind(widthProperty().multiply(0.49));
        batteryStateVBox.prefWidthProperty().bind(widthProperty().multiply(0.29));
        todoVBox.prefWidthProperty().bind(widthProperty().multiply(0.2));
        rssVBox.prefHeightProperty().bind(heightProperty());
        batteryStateVBox.prefHeightProperty().bind(heightProperty());
        todoVBox.prefHeightProperty().bind(heightProperty());
    }
    
    public static LeftBottomBox newInstance (@NotNull DoubleProperty parentWidthProperty,
            @NotNull DoubleProperty parentHeightProperty)
    {
        return new LeftBottomBox(parentWidthProperty, parentHeightProperty);
    }
}
