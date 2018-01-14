package com.arivan.amin.widget.power;

import com.arivan.amin.widget.internet.connectivity.InternetConnectionBox;
import javafx.animation.*;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public class BatteryStateBox extends VBox
{
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final BatteryState batteryState;
    private final Label stateLabel;
    private final Label timeRemainingLabel;
    private ProgressBar batteryBar;
    
    private BatteryStateBox (DoubleProperty parentWidth, DoubleProperty parentHeight)
    {
        setSpacing(10);
        prefWidthProperty().bind(parentWidth.multiply(0.3));
        prefHeightProperty().bind(parentHeight);
        setAlignment(Pos.TOP_CENTER);
        batteryState = LinuxBatteryState.newInstance();
        stateLabel = new Label();
        timeRemainingLabel = new Label();
        batteryBar = new ProgressBar();
        getChildren().addAll(new Label("Battery percentage"), batteryBar, stateLabel,
                timeRemainingLabel);
        batteryBar.prefWidthProperty().bind(prefWidthProperty().multiply(0.7));
        getChildren().add(InternetConnectionBox.newInstance());
        setUpdateAnimation();
    }
    
    private void setUpdateAnimation ()
    {
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), this::updateHandler));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    
    @NotNull
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
}
