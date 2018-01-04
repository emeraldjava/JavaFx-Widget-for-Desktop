package com.arivan.amin.widget.system.details;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public class SystemDetailsPane extends Pane
{
    private final Logger logger = Logger.getLogger(getClass().getName());
    
    private SystemDetailsPane (@NotNull Pane container)
    {
        super();
        VBox mainVBox = new VBox();
        mainVBox.prefWidthProperty().bind(container.widthProperty());
        mainVBox.prefHeightProperty().bind(container.heightProperty());
        getChildren().add(mainVBox);
    }
    
    @NotNull
    public static SystemDetailsPane newInstance (Pane container)
    {
        return new SystemDetailsPane(container);
    }
}
