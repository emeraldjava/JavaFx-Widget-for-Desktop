package com.arivan.amin.widget.ui.boxes;

import com.arivan.amin.widget.cpu.CpuProgressBar;
import com.arivan.amin.widget.memory.MemoryProgressBar;
import javafx.beans.property.DoubleProperty;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public class RightBox extends VBox
{
    private static final double RIGHT_BOX_WIDTH = 0.18;
    private final Logger logger = Logger.getLogger(getClass().getName());
    
    private RightBox (DoubleProperty parentWidthProperty, DoubleProperty parentHeightProperty)
    {
        super();
        setSpacing(15);
        prefWidthProperty().bind(parentWidthProperty.multiply(RIGHT_BOX_WIDTH));
        prefHeightProperty().bind(parentHeightProperty);
        getChildren().add(CpuProgressBar.newInstance(prefWidthProperty(), prefHeightProperty()));
        getChildren().add(MemoryProgressBar.newInstance(prefWidthProperty(), prefHeightProperty()));
    }
    
    @NotNull
    public static RightBox newInstance (DoubleProperty parentWidthProperty,
            DoubleProperty parentHeightProperty)
    {
        return new RightBox(parentWidthProperty, parentHeightProperty);
    }
}
