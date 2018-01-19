package com.arivan.amin.widget.ui.boxes;

import javafx.beans.property.DoubleProperty;
import javafx.scene.layout.*;

import java.util.logging.Logger;

public class LeftBox extends VBox
{
    private final Logger logger = Logger.getLogger(getClass().getName());
    private static final double LEFT_BOX_WIDTH = 0.80;
    
    private LeftBox (DoubleProperty parentWidth, DoubleProperty parentHeight)
    {
        bindBoxToParent(parentWidth, parentHeight);
        HBox leftMiddleHBox = new HBox();
        leftMiddleHBox.prefHeightProperty().bind(heightProperty().multiply(0.1));
        getChildren().add(LeftTopBox.newInstance(prefWidthProperty(), prefHeightProperty()));
        getChildren().addAll(leftMiddleHBox);
        getChildren().add(LeftBottomBox.newInstance(prefWidthProperty(), prefHeightProperty()));
    }
    
    private void bindBoxToParent (DoubleProperty parentWidth, DoubleProperty parentHeight)
    {
        prefWidthProperty().bind(parentWidth.multiply(LEFT_BOX_WIDTH));
        prefHeightProperty().bind(parentHeight);
    }
    
    public static LeftBox newInstance (DoubleProperty parentWidthProperty,
            DoubleProperty parentHeightProperty)
    {
        return new LeftBox(parentWidthProperty, parentHeightProperty);
    }
}
