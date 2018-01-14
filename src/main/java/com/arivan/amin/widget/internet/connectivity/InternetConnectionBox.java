package com.arivan.amin.widget.internet.connectivity;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.logging.Logger;

public class InternetConnectionBox extends VBox
{
    private final Logger logger = Logger.getLogger(getClass().getName());
    private ConnectionStatus connectionStatus;
    private final Label statusLabel;
    private final Label pingTimeLabel;
    
    private InternetConnectionBox ()
    {
        connectionStatus = LinuxConnectionStatus.newInstance();
        setSpacing(10);
        setAlignment(Pos.TOP_CENTER);
        statusLabel = new Label();
        pingTimeLabel = new Label();
        getChildren().addAll(statusLabel, pingTimeLabel);
        updateDataPeriodically();
    }
    
    private void updateHandler (ActionEvent e)
    {
        connectionStatus.updateData();
        statusLabel.setText(connectionStatus.status());
        pingTimeLabel.setText("ping time =" + connectionStatus.pingTime());
    }
    
    private void updateDataPeriodically ()
    {
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), this::updateHandler));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    
    public static InternetConnectionBox newInstance ()
    {
        return new InternetConnectionBox();
    }
}
