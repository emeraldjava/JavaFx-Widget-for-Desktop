package com.arivan.amin.widget.applications.usage;

import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.logging.Logger;

public class UsageMonitorBox extends HBox
{
    private static final int SPACING = 7;
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final UsageMonitor usageMonitor;
    private final VBox namesBox;
    private final VBox cpuUsageBox;
    
    private UsageMonitorBox ()
    {
        super();
        setPadding(new Insets(20));
        usageMonitor = LinuxUsageMonitor.newInstance();
        Timeline timeline = new Timeline();
        namesBox = new VBox(SPACING);
        cpuUsageBox = new VBox(SPACING);
        getChildren().addAll(namesBox, cpuUsageBox);
        namesBox.prefWidthProperty().bind(widthProperty().multiply(0.5));
        cpuUsageBox.prefWidthProperty().bind(widthProperty().multiply(0.5));
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(2), e ->
        {
            updateUsageData();
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    
    @NotNull
    public static UsageMonitorBox newInstance ()
    {
        return new UsageMonitorBox();
    }
    
    private void updateUsageData ()
    {
        List<UsageItem> items = usageMonitor.getProcessesUsage();
        namesBox.getChildren().clear();
        cpuUsageBox.getChildren().clear();
        items.forEach(e ->
        {
            namesBox.getChildren().add(new Label(e.getCommand()));
            cpuUsageBox.getChildren().add(new Label(e.getCpu()));
        });
    }
}
