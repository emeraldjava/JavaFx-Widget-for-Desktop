package com.arivan.amin.widget.cpu;

import javafx.animation.*;
import javafx.beans.property.DoubleProperty;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public class CpuProgressBar extends VBox
{
    private static final int UPDATE_SPEED_IN_SECONDS = 1;
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final CpuMonitor processor;
    private final ProgressBar cpuBar;
    private final Label cpuLabel;
    
    private CpuProgressBar (DoubleProperty parentWidthProperty, DoubleProperty parentHeightProperty)
    {
        super();
        processor = LinuxCpuMonitor.newInstance();
        setSpacing(5);
        cpuBar = new ProgressBar();
        cpuLabel = new Label();
        BorderPane cpuBorderPane = new BorderPane();
        cpuBorderPane.setLeft(new Label("CPU"));
        cpuBorderPane.setRight(cpuLabel);
        getChildren().addAll(cpuBorderPane, cpuBar);
        cpuBar.prefWidthProperty().bind(parentWidthProperty);
        animateBar();
    }
    
    @NotNull
    public static CpuProgressBar newInstance (DoubleProperty parentWidthProperty,
            DoubleProperty parentHeightProperty)
    {
        return new CpuProgressBar(parentWidthProperty, parentHeightProperty);
    }
    
    private void animateBar ()
    {
        Timeline cpuTimeLine =
                new Timeline(new KeyFrame(Duration.seconds(UPDATE_SPEED_IN_SECONDS), e ->
                {
                    try
                    {
                        double data = processor.getCpuUsage();
                        cpuBar.setProgress(data);
                        cpuLabel.setText((int) (data * 100) + " ");
                    }
                    catch (RuntimeException ex)
                    {
                        logger.warning(ex.getMessage());
                    }
                }));
        cpuTimeLine.setCycleCount(Animation.INDEFINITE);
        cpuTimeLine.play();
    }
}
