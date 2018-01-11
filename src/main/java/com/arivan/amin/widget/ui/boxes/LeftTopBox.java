package com.arivan.amin.widget.ui.boxes;

import com.arivan.amin.widget.forecast.WeatherPane;
import javafx.beans.property.DoubleProperty;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;

public class LeftTopBox extends HBox
{
    private LeftTopBox (@NotNull DoubleProperty parentWidthProperty,
            @NotNull DoubleProperty parentHeightProperty)
    {
        super();
        prefWidthProperty().bind(parentWidthProperty);
        prefHeightProperty().bind(parentHeightProperty.multiply(0.4));
        getChildren().add(ClockBox.newInstance(prefWidthProperty(), prefHeightProperty()));
        VBox weatherVBox = new VBox();
        weatherVBox.getChildren().add(WeatherPane.newInstance(weatherVBox));
        getChildren().add(weatherVBox);
        weatherVBox.prefWidthProperty().bind(widthProperty().multiply(0.66));
        weatherVBox.prefHeightProperty().bind(heightProperty());
    }
    
    @NotNull
    public static LeftTopBox newInstance (DoubleProperty widthProperty,
            DoubleProperty heightProperty)
    {
        return new LeftTopBox(widthProperty, heightProperty);
    }
}
