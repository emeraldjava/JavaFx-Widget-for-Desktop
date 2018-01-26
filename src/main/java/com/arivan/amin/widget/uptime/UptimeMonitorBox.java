package com.arivan.amin.widget.uptime;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.logging.Logger;

public class UptimeMonitorBox extends VBox
{
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final Label uptimeLabel;
    UptimeMonitor uptimeMonitor;
    
    private UptimeMonitorBox ()
    {
        setSpacing(10);
        determineOperatingSystem();
        setAlignment(Pos.TOP_CENTER);
        uptimeLabel = new Label("uptime");
        getChildren().add(new Label("System Uptime"));
        getChildren().add(uptimeLabel);
        updateDataPeriodically();
    }
    
    private void updateDataPeriodically ()
    {
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), this::updateHandler));
        timeline.getKeyFrames().add(new KeyFrame(Duration.minutes(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    
    private void updateHandler (ActionEvent e)
    {
        uptimeLabel.setText(uptimeMonitor.systemUptime());
    }
    
    private void determineOperatingSystem ()
    {
        uptimeMonitor = LinuxUptimeMonitor.newInstance();
    }
    
    public static UptimeMonitorBox newInstance ()
    {
        return new UptimeMonitorBox();
    }
}
