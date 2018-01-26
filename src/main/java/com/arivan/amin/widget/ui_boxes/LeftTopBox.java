package com.arivan.amin.widget.ui_boxes;

import com.arivan.amin.widget.forecast.WeatherBox;
import com.arivan.amin.widget.power.BatteryStateBox;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.scene.layout.HBox;

import java.util.logging.Logger;

public class LeftTopBox extends HBox
{
    private final Logger logger = Logger.getLogger(getClass().getName());
    
    private LeftTopBox (DoubleProperty parentWidthProperty, DoubleProperty parentHeightProperty)
    {
        prefWidthProperty().bind(parentWidthProperty);
        prefHeightProperty().bind(parentHeightProperty.multiply(0.4));
        Platform.runLater(() ->
        {
            getChildren().add(0, ClockBox.newInstance(prefWidthProperty(), prefHeightProperty()));
        });
        Platform.runLater(() ->
        {
            getChildren().add(1, WeatherBox.newInstance(prefWidthProperty(), prefHeightProperty()));
        });
        Platform.runLater(() ->
        {
            getChildren()
                    .add(2, BatteryStateBox.newInstance(prefWidthProperty(), prefHeightProperty()));
        });
    }
    
    public static LeftTopBox newInstance (DoubleProperty widthProperty,
            DoubleProperty heightProperty)
    {
        return new LeftTopBox(widthProperty, heightProperty);
    }
}
