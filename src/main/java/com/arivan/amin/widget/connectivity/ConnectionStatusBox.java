package com.arivan.amin.widget.connectivity;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.logging.Logger;

public class ConnectionStatusBox extends VBox
{
    private final Logger logger = Logger.getLogger(getClass().getName());
    private static final int TOP_PADDING = 20;
    private static final int PERIOD_BETWEEN_UPDATES = 1;
    private ConnectionStatus connectionStatus;
    private Label statusLabel;
    private Label pingTimeLabel;
    
    private ConnectionStatusBox ()
    {
        setPadding(new Insets(TOP_PADDING, 0, 0, 0));
        determineOperatingSystem();
        adjustBoxProperties();
        initializeAndAddFields();
        updateDataPeriodically();
    }
    
    private void adjustBoxProperties ()
    {
        setSpacing(10);
        setAlignment(Pos.TOP_CENTER);
    }
    
    private void initializeAndAddFields ()
    {
        statusLabel = new Label();
        pingTimeLabel = new Label();
        getChildren().addAll(statusLabel, pingTimeLabel);
    }
    
    private void determineOperatingSystem ()
    {
        if (System.getProperty("os.name").contains("Windows"))
        {
            connectionStatus = WindowsConnectionStatus.newInstance();
        }
        else if (System.getProperty("os.name").contains("Linux"))
        {
            connectionStatus = LinuxConnectionStatus.newInstance();
        }
    }
    
    private void updateHandler (ActionEvent e)
    {
        connectionStatus.updateData();
        if (connectionStatus.isConnected())
        {
            statusLabel.setText("Internet access available");
            pingTimeLabel.setText("ping time =" + connectionStatus.pingTime());
        }
        else
        {
            statusLabel.setText("No internet access");
            pingTimeLabel.setText("");
        }
    }
    
    private void updateDataPeriodically ()
    {
        // todo fix the lagging of ping time retrieval so that the duration can be set back to 1 second
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(5), this::updateHandler));
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(PERIOD_BETWEEN_UPDATES)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    
    public static ConnectionStatusBox newInstance ()
    {
        return new ConnectionStatusBox();
    }
    
    @Override
    public String toString ()
    {
        return "ConnectionStatusBox{" + "connectionStatus=" + connectionStatus + ", statusLabel=" +
                statusLabel + ", pingTimeLabel=" + pingTimeLabel + '}';
    }
}
