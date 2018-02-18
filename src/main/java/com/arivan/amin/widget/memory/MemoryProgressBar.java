package com.arivan.amin.widget.memory;

import javafx.animation.*;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.logging.Logger;

public class MemoryProgressBar extends VBox
{
    private static final int UPDATE_FREQUENCY_IN_SECONDS = 1;
    private final Logger logger = Logger.getLogger(getClass().getName());
    private MemoryMonitor monitor;
    private ProgressBar memoryBar;
    private Label memoryLabel;
    
    @SuppressWarnings ("TypeMayBeWeakened")
    private MemoryProgressBar (DoubleProperty parentWidthProperty,
            DoubleProperty parentHeightProperty)
    {
        prefWidthProperty().bind(parentWidthProperty);
        determineOperatingSystem();
        setSpacing(5);
        initializeFields();
        BorderPane memoryBorderPane = new BorderPane();
        memoryBorderPane.setLeft(new Label("RAM"));
        memoryBorderPane.setRight(memoryLabel);
        getChildren().addAll(memoryBorderPane, memoryBar);
        animateBar();
    }
    
    private void initializeFields ()
    {
        memoryBar = new ProgressBar();
        memoryLabel = new Label();
        memoryBar.prefWidthProperty().bind(prefWidthProperty());
    }
    
    private void determineOperatingSystem ()
    {
        monitor = LinuxMemoryMonitor.newInstance();
    }
    
    public static MemoryProgressBar newInstance (DoubleProperty parentWidthProperty,
            DoubleProperty parentHeightProperty)
    {
        return new MemoryProgressBar(parentWidthProperty, parentHeightProperty);
    }
    
    private void animateBar ()
    {
        Timeline memoryTimeLine = new Timeline();
        memoryTimeLine.getKeyFrames()
                .add(new KeyFrame(Duration.seconds(UPDATE_FREQUENCY_IN_SECONDS),
                        this::dataUpdateHandler));
        memoryTimeLine.setCycleCount(Animation.INDEFINITE);
        memoryTimeLine.play();
    }
    
    private void dataUpdateHandler (ActionEvent e)
    {
        try
        {
            double data = monitor.getUsedMemory();
            memoryBar.setProgress(data);
            memoryLabel.setText((int) (data * 100) + " ");
        }
        catch (Exception ex)
        {
            logger.warning(ex.getMessage());
        }
    }
    
    @Override
    public String toString ()
    {
        return "MemoryProgressBar{" + "monitor=" + monitor + ", memoryBar=" + memoryBar +
                ", memoryLabel=" + memoryLabel + '}';
    }
}
