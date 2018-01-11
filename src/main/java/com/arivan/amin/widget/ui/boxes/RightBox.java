package com.arivan.amin.widget.ui.boxes;

import com.arivan.amin.widget.cpu.CpuProgressBar;
import com.arivan.amin.widget.memory.MemoryProgressBar;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public class RightBox extends VBox
{
    private static final double RIGHT_BOX_WIDTH = 0.18;
    private final Logger logger = Logger.getLogger(getClass().getName());
    
    private RightBox (@NotNull Pane container)
    {
        super();
        VBox mainBox = new VBox();
        mainBox.prefWidthProperty().bind(container.widthProperty().multiply(RIGHT_BOX_WIDTH));
        mainBox.prefHeightProperty().bind(container.heightProperty());
        mainBox.getChildren().add(CpuProgressBar.newInstance(mainBox));
        mainBox.getChildren().add(MemoryProgressBar.newInstance(mainBox));
        getChildren().add(mainBox);
    }
    
    @NotNull
    public static RightBox newInstance (Pane container)
    {
        return new RightBox(container);
    }
}
