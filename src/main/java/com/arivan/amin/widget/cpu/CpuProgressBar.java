package com.arivan.amin.widget.cpu;

import javafx.animation.*;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public class CpuProgressBar extends VBox
{
    private static final int UPDATE_FREQUENCY_IN_SECONDS = 1;
    private final Logger logger = Logger.getLogger(getClass().getName());
    private CpuMonitor processor;
    private ProgressBar cpuBar;
    private Label cpuLabel;
    
    private CpuProgressBar (DoubleProperty parentWidthProperty, DoubleProperty parentHeightProperty)
    {
        setSpacing(5);
        initializeFields();
        BorderPane cpuBorderPane = new BorderPane();
        cpuBorderPane.setLeft(new Label("CPU"));
        cpuBorderPane.setRight(cpuLabel);
        getChildren().addAll(cpuBorderPane, cpuBar);
        cpuBar.prefWidthProperty().bind(parentWidthProperty);
        animateBar();
    }
    
    private void initializeFields ()
    {
        determineCpuMonitor();
        cpuBar = new ProgressBar();
        cpuLabel = new Label();
    }
    
    private void determineCpuMonitor ()
    {
        processor = LinuxCpuMonitor.newInstance();
    }
    
    @NotNull
    public static CpuProgressBar newInstance (DoubleProperty parentWidthProperty,
            DoubleProperty parentHeightProperty)
    {
        return new CpuProgressBar(parentWidthProperty, parentHeightProperty);
    }
    
    private void animateBar ()
    {
        Timeline cpuTimeLine = new Timeline();
        cpuTimeLine.getKeyFrames().add(new KeyFrame(Duration.seconds(UPDATE_FREQUENCY_IN_SECONDS),
                this::cpuDataUpdateHandler));
        cpuTimeLine.setCycleCount(Animation.INDEFINITE);
        cpuTimeLine.play();
    }
    
    private void cpuDataUpdateHandler (ActionEvent e)
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
    }
    
    @Override
    public String toString ()
    {
        return "CpuProgressBar{" + "processor=" + processor + ", cpuBar=" + cpuBar + ", cpuLabel=" +
                cpuLabel + '}';
    }
}
