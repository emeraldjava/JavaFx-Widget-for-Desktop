package com.arivan.amin.widget.cpu;

import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.*;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public class CpuProgressBar extends Pane
{
    private static final int UPDATE_SPEED_IN_SECONDS = 1;
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final CpuMonitor processor;
    private final ProgressBar cpuBar;
    private final Label cpuLabel;
    private Pane pane;
    
    private CpuProgressBar (@NotNull Pane pane)
    {
        super();
        processor = LinuxCpuMonitor.newInstance();
        VBox mainVBox = new VBox(5);
        mainVBox.setPadding(new Insets(10));
        cpuBar = new ProgressBar();
        cpuLabel = new Label();
        BorderPane cpuBorderPane = new BorderPane();
        cpuBorderPane.setLeft(new Label("CPU"));
        cpuBorderPane.setRight(cpuLabel);
        mainVBox.getChildren().addAll(cpuBorderPane, cpuBar);
        cpuBar.prefWidthProperty().bind(pane.widthProperty());
        animateBar();
        getChildren().add(mainVBox);
    }
    
    @NotNull
    public static CpuProgressBar newInstance (Pane pane)
    {
        return new CpuProgressBar(pane);
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
