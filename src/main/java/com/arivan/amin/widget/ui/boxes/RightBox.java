package com.arivan.amin.widget.ui.boxes;

import com.arivan.amin.widget.cpu.CpuProgressBar;
import com.arivan.amin.widget.memory.MemoryProgressBar;
import com.arivan.amin.widget.wireless.WirelessMonitorBox;
import javafx.beans.property.DoubleProperty;
import javafx.scene.layout.VBox;

import java.util.logging.Logger;

public class RightBox extends VBox
{
    private static final double RIGHT_BOX_WIDTH = 0.18;
    private static final int SPACING = 20;
    private final Logger logger = Logger.getLogger(getClass().getName());
    
    private RightBox (DoubleProperty parentWidthProperty, DoubleProperty parentHeightProperty)
    {
        setSpacing(SPACING);
        bindBoxToParent(parentWidthProperty, parentHeightProperty);
        addItemToBox();
    }
    
    private void addItemToBox ()
    {
        getChildren().add(CpuProgressBar.newInstance(prefWidthProperty(), prefHeightProperty()));
        getChildren().add(MemoryProgressBar.newInstance(prefWidthProperty(), prefHeightProperty()));
        getChildren()
                .add(WirelessMonitorBox.newInstance(prefWidthProperty(), prefHeightProperty()));
    }
    
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
