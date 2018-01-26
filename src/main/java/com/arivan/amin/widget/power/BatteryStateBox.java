package com.arivan.amin.widget.power;

import com.arivan.amin.widget.connectivity.ConnectionStatusBox;
import javafx.animation.*;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.logging.Logger;

public class BatteryStateBox extends VBox
{
    private static final double BATTERY_PROGRESS_BAR_WIDTH = 0.7;
    private final Logger logger = Logger.getLogger(getClass().getName());
    private BatteryState batteryState;
    private Label stateLabel;
    private Label timeRemainingLabel;
    private ProgressBar batteryBar;
    
    private BatteryStateBox (DoubleProperty parentWidth, DoubleProperty parentHeight)
    {
        bindBoxSizeToParent(parentWidth, parentHeight);
        determineOperatingSystem();
        initializeFields();
        setBoxPropertiesAndAddItems();
        addConnectionStatusBox();
        updateDataPeriodically();
    }
    
    private void addConnectionStatusBox ()
    {
        getChildren().add(ConnectionStatusBox.newInstance());
    }
    
    private void setBoxPropertiesAndAddItems ()
    {
        setSpacing(10);
        setAlignment(Pos.TOP_CENTER);
        getChildren().addAll(new Label("Battery percentage"), batteryBar, stateLabel,
                timeRemainingLabel);
    }
    
    private void initializeFields ()
    {
        stateLabel = new Label();
        timeRemainingLabel = new Label();
        batteryBar = new ProgressBar();
        batteryBar.prefWidthProperty().bind(prefWidthProperty().multiply(BATTERY_PROGRESS_BAR_WIDTH));
    }
    
    private void bindBoxSizeToParent (DoubleProperty parentWidth, DoubleProperty parentHeight)
    {
        prefWidthProperty().bind(parentWidth.multiply(0.3));
        prefHeightProperty().bind(parentHeight);
    }
    
    private void determineOperatingSystem ()
    {
        batteryState = LinuxBatteryState.newInstance();
    }
    
    private void updateDataPeriodically ()
    {
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), this::updateHandler));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    
    public static BatteryStateBox newInstance (DoubleProperty parentWidth,
            DoubleProperty parentHeight)
    {
        return new BatteryStateBox(parentWidth, parentHeight);
    }
    
    private void updateHandler (ActionEvent e)
    {
        batteryState.updateData();
        if ("charging".equals(batteryState.batteryState()))
        {
            stateLabel.setText("charging " + batteryState.percentage());
            timeRemainingLabel.setText(batteryState.timeToFull() + " remaining");
        }
        else if ("fully-charged".equals(batteryState.batteryState()))
        {
            stateLabel.setText("Fully charged");
            timeRemainingLabel.setText("");
        }
        else
        {
            stateLabel.setText(batteryState.percentage());
            timeRemainingLabel.setText(batteryState.timeToEmpty() + " remaining");
        }
        batteryBar.setProgress(convertBatteryPercentage());
    }
    
    private double convertBatteryPercentage ()
    {
        String currentPercentString = batteryState.percentage().replace("%", "");
        return Double.parseDouble(currentPercentString) / 100;
    }
    
    @Override
    public String toString ()
    {
        return "BatteryStateBox{" + "batteryState=" + batteryState + ", stateLabel=" + stateLabel +
                ", timeRemainingLabel=" + timeRemainingLabel + ", batteryBar=" + batteryBar + '}';
    }
}
