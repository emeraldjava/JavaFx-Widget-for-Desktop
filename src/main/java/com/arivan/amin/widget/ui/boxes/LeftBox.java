package com.arivan.amin.widget.ui.boxes;

import javafx.beans.property.DoubleProperty;
import javafx.scene.layout.*;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public class LeftBox extends VBox
{
    private static final double LEFT_BOX_WIDTH = 0.80;
    private final Logger logger = Logger.getLogger(getClass().getName());
    
    private LeftBox (DoubleProperty parentWidthProperty, DoubleProperty parentHeightProperty)
    {
        super();
        prefWidthProperty().bind(parentWidthProperty.multiply(LEFT_BOX_WIDTH));
        prefHeightProperty().bind(parentHeightProperty);
        HBox leftMiddleHBox = new HBox();
        leftMiddleHBox.prefHeightProperty().bind(heightProperty().multiply(0.1));
        getChildren().add(LeftTopBox.newInstance(prefWidthProperty(), prefHeightProperty()));
        getChildren().addAll(leftMiddleHBox);
        getChildren().add(LeftBottomBox.newInstance(prefWidthProperty(), prefHeightProperty()));
    }
    
    @NotNull
    public static LeftBox newInstance (DoubleProperty parentWidthProperty,
            DoubleProperty parentHeightProperty)
    {
        return new LeftBox(parentWidthProperty, parentHeightProperty);
    }
}
