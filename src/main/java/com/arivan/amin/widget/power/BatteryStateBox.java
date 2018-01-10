package com.arivan.amin.widget.power;

import javafx.animation.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public class BatteryStateBox extends VBox
{
    private final Logger logger = Logger.getLogger(getClass().getName());
    BatteryState batteryState;
    private final Label stateLabel;
    private final Label timeRemainingLabel;
    private final ImageView iconImageView;
    
    private BatteryStateBox ()
    {
        // TODO 1/10/18 bind image size to box size
        super();
        batteryState = LinuxBatteryState.newInstance();
        stateLabel = new Label();
        timeRemainingLabel = new Label();
        iconImageView = new ImageView();
        getChildren().addAll(stateLabel, timeRemainingLabel, iconImageView);
        setUpdateAnimation();
    }
    
    private void setUpdateAnimation ()
    {
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), e ->
        {
            updateValues();
        }));
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(30)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    
    private void updateValues ()
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
        iconImageView.setImage(new Image(batteryState.currentStateIcon()));
    }
    
    @NotNull
    public static BatteryStateBox newInstance ()
    {
        return new BatteryStateBox();
    }
}
