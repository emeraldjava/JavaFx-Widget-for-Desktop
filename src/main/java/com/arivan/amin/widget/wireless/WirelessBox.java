package com.arivan.amin.widget.wireless;

import javafx.animation.*;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.logging.Logger;

public class WirelessBox extends VBox
{
    private final Logger logger = Logger.getLogger(getClass().getName());
    private Label nameLabel;
    private ProgressBar signalStrengthBar;
    private WirelessMonitor wirelessMonitor;
    
    private WirelessBox (DoubleProperty parentWidth, DoubleProperty parentHeight)
    {
        determineOperatingSystem();
        initializeFields();
        BorderPane labelsBorderPane = new BorderPane();
        labelsBorderPane.setLeft(new Label("Wireless AP"));
        labelsBorderPane.setRight(nameLabel);
        getChildren().addAll(labelsBorderPane, signalStrengthBar);
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
        nameLabel.setText(wirelessMonitor.wirelessName());
        signalStrengthBar.setProgress(wirelessMonitor.signalLevel());
    }
    
    private void initializeFields ()
    {
        nameLabel = new Label();
        signalStrengthBar = new ProgressBar();
    }
    
    public static WirelessBox newInstance (DoubleProperty parentWidth, DoubleProperty parentHeight)
    {
        return new WirelessBox(parentWidth, parentHeight);
    }
    
    private void determineOperatingSystem ()
    {
        wirelessMonitor = LinuxWirelessMonitor.newInstance();
    }
    
    @Override
    public String toString ()
    {
        return "WirelessBox{" + "nameLabel=" + nameLabel + ", signalStrengthBar=" +
                signalStrengthBar + ", wirelessMonitor=" + wirelessMonitor + '}';
    }
}
