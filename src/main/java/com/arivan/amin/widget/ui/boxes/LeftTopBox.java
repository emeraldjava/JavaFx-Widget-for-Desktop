package com.arivan.amin.widget.ui.boxes;

import com.arivan.amin.widget.forecast.WeatherBox;
import com.arivan.amin.widget.power.BatteryStateBox;
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
        getChildren().add(ClockBox.newInstance(prefWidthProperty(), prefHeightProperty()));
        getChildren().add(WeatherBox.newInstance(prefWidthProperty(), prefHeightProperty()));
        getChildren().add(BatteryStateBox.newInstance(prefWidthProperty(), prefHeightProperty()));
    }
    
    public static LeftTopBox newInstance (DoubleProperty widthProperty,
            DoubleProperty heightProperty)
    {
        return new LeftTopBox(widthProperty, heightProperty);
    }
}
