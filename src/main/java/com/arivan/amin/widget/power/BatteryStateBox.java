package com.arivan.amin.widget.power;

import javafx.animation.*;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public class BatteryStateBox extends VBox
{
    private static final int IMAGE_ROTATION_DEGREE = 90;
    private final Logger logger = Logger.getLogger(getClass().getName());
    private BatteryState batteryState;
    private final Label stateLabel;
    private final Label timeRemainingLabel;
    private final ImageView iconImageView;
    
    private BatteryStateBox ()
    {
        super();
        setSpacing(10);
        setAlignment(Pos.TOP_CENTER);
        batteryState = LinuxBatteryState.newInstance();
        stateLabel = new Label();
        timeRemainingLabel = new Label();
        iconImageView = new ImageView();
        iconImageView.setRotate(IMAGE_ROTATION_DEGREE);
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
