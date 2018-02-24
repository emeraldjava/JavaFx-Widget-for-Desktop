package com.arivan.amin.widget.network;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.logging.Logger;

public class NetworkBox extends VBox
{
    private final Logger logger = Logger.getLogger(getClass().getName());
    private Label downloadLabel;
    private Label uploadLabel;
    private NetworkMonitor networkMonitor;
    private Label ipLabel;
    
    private NetworkBox ()
    {
        determineOperatingSystem();
        setAlignment(Pos.TOP_CENTER);
        setSpacing(10);
        initializeAndAddFields();
        updateDataPeriodically();
    }
    
    private void updateDataPeriodically ()
    {
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), this::updateHandler));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    
    private void updateHandler (ActionEvent e)
    {
        networkMonitor.updateData();
        downloadLabel.setText(networkMonitor.downloadSpeed());
        uploadLabel.setText(networkMonitor.uploadSpeed());
        ipLabel.setText(networkMonitor.ipAddress());
    }
    
    private void initializeAndAddFields ()
    {
        initializeFields();
        getChildren().add(new Label("Network usage"));
        getChildren().add(ipLabel);
        BorderPane downloadBorderPane = new BorderPane();
        downloadBorderPane.setLeft(new Label("download"));
        downloadBorderPane.setRight(downloadLabel);
        BorderPane uploadBorderPane = new BorderPane();
        uploadBorderPane.setLeft(new Label("Upload"));
        uploadBorderPane.setRight(uploadLabel);
        getChildren().addAll(downloadBorderPane, uploadBorderPane);
    }
    
    private void initializeFields ()
    {
        ipLabel = new Label();
        downloadLabel = new Label();
        uploadLabel = new Label();
    }
    
    private void determineOperatingSystem ()
    {
        if (System.getProperty("os.name").contains("Windows"))
        {
            networkMonitor = WindowsNetworkMonitor.newInstance();
        }
        else if (System.getProperty("os.name").contains("Linux"))
        {
            networkMonitor = LinuxNetworkMonitor.newInstance();
        }
    }
    
    public static NetworkBox newInstance ()
    {
        return new NetworkBox();
    }
    
    @Override
    public String toString ()
    {
        return "NetworkBox{" + "downloadLabel=" + downloadLabel + ", uploadLabel=" + uploadLabel +
                ", networkMonitor=" + networkMonitor + ", ipLabel=" + ipLabel + '}';
    }
}
