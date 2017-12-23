package com.arivan.amin.widget.memory;

import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.*;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public class MemoryProgressBar extends Pane
{
    private static final int ANIMATION_SPEED = 300;
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final Monitor monitor;
    private final ProgressBar memoryBar;
    private final Label memoryLabel;
    private Pane pane;
    
    private MemoryProgressBar (@NotNull Pane pane)
    {
        super();
        monitor = LinuxMonitor.newInstance();
        VBox mainVBox = new VBox(5);
        mainVBox.setPadding(new Insets(10));
        memoryBar = new ProgressBar();
        memoryLabel = new Label();
        BorderPane memoryBorderPane = new BorderPane();
        memoryBorderPane.setLeft(new Label("RAM"));
        memoryBorderPane.setRight(memoryLabel);
        mainVBox.getChildren().addAll(memoryBorderPane, memoryBar);
        memoryBar.prefWidthProperty().bind(pane.widthProperty());
        animateBar();
        getChildren().add(mainVBox);
    }
    
    @NotNull
    public static MemoryProgressBar newInstance (Pane pane)
    {
        return new MemoryProgressBar(pane);
    }
    
    private void animateBar ()
    {
        Timeline cpuTimeline = new Timeline(new KeyFrame(Duration.millis(ANIMATION_SPEED), e ->
        {
            try
            {
                double data = monitor.getCommandData();
                memoryBar.setProgress(data);
                memoryLabel.setText((int) (data * 100) + " ");
            }
            catch (Exception ex)
            {
                logger.warning(ex.getMessage());
            }
        }));
        cpuTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(5)));
        cpuTimeline.setCycleCount(Animation.INDEFINITE);
        cpuTimeline.play();
    }
    
    @Override
    public String toString ()
    {
        return "MemoryProgressBar{" + "monitor=" + monitor + ", memoryBar=" + memoryBar +
                ", memoryLabel=" + memoryLabel + ", pane=" + pane + '}';
    }
}
