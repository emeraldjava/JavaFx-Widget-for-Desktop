package com.arivan.amin.widget.ui.boxes;

import javafx.scene.layout.*;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public class LeftBox extends VBox
{
    private static final double LEFT_BOX_WIDTH = 0.80;
    private final Logger logger = Logger.getLogger(getClass().getName());
    
    private LeftBox (@NotNull Pane container)
    {
        super();
        prefWidthProperty().bind(container.widthProperty().multiply(LEFT_BOX_WIDTH));
        prefHeightProperty().bind(container.heightProperty());
        HBox middleHBox = new HBox();
        middleHBox.prefHeightProperty().bind(heightProperty().multiply(0.1));
        getChildren().add(LeftTopBox.newInstance(prefWidthProperty(), prefHeightProperty()));
        getChildren().addAll(middleHBox);
        getChildren().add(LeftBottomBox.newInstance(prefWidthProperty(), prefHeightProperty()));
    }
    
    @NotNull
    public static LeftBox newInstance (Pane pane)
    {
        return new LeftBox(pane);
    }
}
