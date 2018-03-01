package com.arivan.amin.widget.ui_boxes;

import com.arivan.amin.widget.file_system.FileSystemBox;
import com.arivan.amin.widget.memory.MemoryProgressBar;
import com.arivan.amin.widget.wireless.WirelessMonitorBox;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.scene.layout.VBox;

import java.util.logging.Logger;

public class RightBox extends VBox
{
    private static final double RIGHT_BOX_WIDTH = 0.18;
    private static final int SPACING = 30;
    private final Logger logger = Logger.getLogger(getClass().getName());
    
    private RightBox (DoubleProperty parentWidthProperty, DoubleProperty parentHeightProperty)
    {
        setSpacing(SPACING);
        bindBoxToParent(parentWidthProperty, parentHeightProperty);
        addItemToBox();
    }
    
    private void addItemToBox ()
    {
        // Platform.runLater(() ->
        // {
        //     getChildren()
        //             .add(0, CpuProgressBar.newInstance(prefWidthProperty(), prefHeightProperty()));
        // });
        Platform.runLater(() ->
        {
            getChildren().add(0,
                    MemoryProgressBar.newInstance(prefWidthProperty(), prefHeightProperty()));
        });
        // Platform.runLater(() ->
        // {
        //     getChildren().add(2, NetworkBox.newInstance());
        // });
        Platform.runLater(() ->
        {
            getChildren().add(1,
                    WirelessMonitorBox.newInstance(prefWidthProperty(), prefHeightProperty()));
        });
        Platform.runLater(() ->
        {
            getChildren()
                    .add(2, FileSystemBox.newInstance(prefWidthProperty(), prefHeightProperty()));
        });
    }
    
    @SuppressWarnings ("TypeMayBeWeakened")
    private void bindBoxToParent (DoubleProperty parentWidth, DoubleProperty parentHeight)
    {
        prefWidthProperty().bind(parentWidth.multiply(RIGHT_BOX_WIDTH));
        prefHeightProperty().bind(parentHeight);
    }
    
    public static RightBox newInstance (DoubleProperty parentWidthProperty,
            DoubleProperty parentHeightProperty)
    {
        return new RightBox(parentWidthProperty, parentHeightProperty);
    }
}
