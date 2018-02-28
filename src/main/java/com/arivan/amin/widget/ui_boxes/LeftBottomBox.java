package com.arivan.amin.widget.ui_boxes;

import javafx.beans.property.DoubleProperty;
import javafx.scene.layout.HBox;

import java.util.logging.Logger;

public class LeftBottomBox extends HBox
{
    private static final double BOX_HEIGHT = 0.5;
    private final Logger logger = Logger.getLogger(getClass().getName());
    
    @SuppressWarnings ("TypeMayBeWeakened")
    private LeftBottomBox (DoubleProperty parentWidth, DoubleProperty parentHeight)
    {
        prefWidthProperty().bind(parentWidth);
        prefHeightProperty().bind(parentHeight.multiply(BOX_HEIGHT));
        // Platform.runLater(() ->
        // {
        //     getChildren()
        //             .add(0, RssReaderBox.newInstance(prefWidthProperty(), prefHeightProperty()));
        // });
    }
    
    // hddtemp to get hard disk temp
    public static LeftBottomBox newInstance (DoubleProperty parentWidth,
            DoubleProperty parentHeight)
    {
        return new LeftBottomBox(parentWidth, parentHeight);
    }
}
