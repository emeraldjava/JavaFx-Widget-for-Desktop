package com.arivan.amin.widget.applications.usage;

import javafx.animation.*;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.logging.Logger;

public class UsageMonitorBox extends VBox
{
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final Label usageLabel;
    private final UsageMonitor usageMonitor;
    
    private UsageMonitorBox ()
    {
        super();
        usageMonitor = LinuxUsageMonitor.newInstance();
        usageLabel = new Label();
        Timeline timeline = new Timeline();
        getChildren().add(usageLabel);
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), e ->
        {
            updateUsageData();
        }));
        timeline.getKeyFrames().add(new KeyFrame(Duration.minutes(1)));
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
        usageLabel.setText("");
        items.forEach(e ->
        {
            usageLabel.setText(usageLabel.getText() + e.getCommand() + " " + e.getCpu() +
                    System.lineSeparator());
        });
    }
    
    @Override
    public String toString ()
    {
        return "UsageMonitorBox{" + "usageLabel=" + usageLabel + ", usageMonitor=" + usageMonitor +
                '}';
    }
}
