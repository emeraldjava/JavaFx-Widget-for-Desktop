package com.arivan.amin.widget.ui.boxes;

import com.arivan.amin.widget.forecast.WeatherBox;
import com.arivan.amin.widget.power.BatteryStateBox;
import javafx.beans.property.DoubleProperty;
import javafx.scene.layout.HBox;
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
        getChildren().add(WeatherBox.newInstance(prefWidthProperty(), prefHeightProperty()));
        getChildren().add(BatteryStateBox.newInstance(prefWidthProperty(), prefHeightProperty()));
    }
    
    @NotNull
    public static LeftTopBox newInstance (DoubleProperty widthProperty,
            DoubleProperty heightProperty)
    {
        return new LeftTopBox(widthProperty, heightProperty);
    }
}
