package com.arivan.amin.widget.ui.boxes;

import com.arivan.amin.widget.system.details.SystemDetailsPane;
import javafx.animation.*;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Locale;
import java.util.logging.Logger;

public class ClockBox extends VBox
{
    private final Logger logger = Logger.getLogger(getClass().getName());
    
    private ClockBox (DoubleProperty parentWidth, DoubleProperty parentHeight)
    {
        super();
        setSpacing(20);
        prefWidthProperty().bind(parentWidth.multiply(0.3));
        prefHeightProperty().bind(parentHeight);
        VBox dateTimeVBox = new VBox();
        dateTimeVBox.setAlignment(Pos.TOP_CENTER);
        VBox systemDetailsVBox = new VBox();
        Label timeLabel = new Label();
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e ->
        {
            LocalTime time = LocalTime.now();
            timeLabel.setText(time.getHour() + ":" + time.getMinute());
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        timeLabel.getStyleClass().add("time-label");
        Label dateLabel = new Label(getCurrentDate());
        dateLabel.getStyleClass().add("date-label");
        dateTimeVBox.getChildren().addAll(timeLabel, dateLabel);
        getChildren().addAll(dateTimeVBox, systemDetailsVBox);
        systemDetailsVBox.getChildren().add(SystemDetailsPane.newInstance(systemDetailsVBox));
        dateTimeVBox.prefWidthProperty().bind(widthProperty());
        systemDetailsVBox.prefWidthProperty().bind(widthProperty());
        dateTimeVBox.prefHeightProperty().bind(heightProperty().multiply(0.33));
        systemDetailsVBox.prefHeightProperty().bind(heightProperty().multiply(0.66));
    }
    
    @NotNull
    public static ClockBox newInstance (DoubleProperty parentWidth, DoubleProperty parentHeight)
    {
        return new ClockBox(parentWidth, parentHeight);
    }
    
    @NotNull
    private String getCurrentDate ()
    {
        LocalDate date = LocalDate.now();
        String day = date.getDayOfWeek().name().toLowerCase(Locale.ENGLISH);
        day = day.substring(0, 1) + day.substring(1);
        switch (date.getDayOfMonth())
        {
            case 1:
                day += ' ' + String.valueOf(date.getDayOfMonth()) + "st of ";
                break;
            case 2:
                day += ' ' + String.valueOf(date.getDayOfMonth()) + "nd of ";
                break;
            case 3:
                day += ' ' + String.valueOf(date.getDayOfMonth()) + "rd of ";
                break;
            default:
                day += ' ' + String.valueOf(date.getDayOfMonth()) + "th of ";
                break;
        }
        String month = date.getMonth().name().toLowerCase(Locale.ENGLISH);
        month = month.substring(0, 1) + month.substring(1);
        return day + month;
    }
}
