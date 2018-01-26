package com.arivan.amin.widget.ui_boxes;

import com.arivan.amin.widget.system_details.SystemDetailsPane;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.css.Styleable;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Locale;
import java.util.logging.Logger;

public class ClockBox extends VBox
{
    private static final int BOX_SPACING = 20;
    private static final double DATE_TIME_BOX_HEIGHT = 0.33;
    private static final double BOX_WIDTH = 0.3;
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final Label timeLabel;
    
    private ClockBox (DoubleProperty parentWidth, DoubleProperty parentHeight)
    {
        bindBoxToParent(parentWidth, parentHeight);
        VBox dateTimeVBox = new VBox();
        dateTimeVBox.setAlignment(Pos.TOP_CENTER);
        timeLabel = new Label();
        Label dateLabel = new Label(getCurrentDate());
        addStylingClassToLabels(timeLabel, dateLabel);
        dateTimeVBox.getChildren().addAll(timeLabel, dateLabel);
        addItemsToBox(dateTimeVBox);
        bindDateTimeBoxToParent(dateTimeVBox);
        animateClock();
    }
    
    private void addItemsToBox (VBox dateTimeVBox)
    {
        setSpacing(BOX_SPACING);
        Platform.runLater(() ->
        {
            getChildren().addAll(dateTimeVBox,
                    SystemDetailsPane.newInstance(prefWidthProperty(), prefHeightProperty()));
        });
    }
    
    private void bindDateTimeBoxToParent (VBox dateTimeVBox)
    {
        dateTimeVBox.prefWidthProperty().bind(widthProperty());
        dateTimeVBox.prefHeightProperty().bind(heightProperty().multiply(DATE_TIME_BOX_HEIGHT));
    }
    
    private void addStylingClassToLabels (Styleable timeLabel, Styleable dateLabel)
    {
        timeLabel.getStyleClass().add("time-label");
        dateLabel.getStyleClass().add("date-label");
    }
    
    private void animateClock ()
    {
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), this::updateHandler));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    
    @SuppressWarnings ("TypeMayBeWeakened")
    private void bindBoxToParent (DoubleProperty parentWidth, DoubleProperty parentHeight)
    {
        prefWidthProperty().bind(parentWidth.multiply(BOX_WIDTH));
        prefHeightProperty().bind(parentHeight);
    }
    
    public static ClockBox newInstance (DoubleProperty parentWidth, DoubleProperty parentHeight)
    {
        return new ClockBox(parentWidth, parentHeight);
    }
    
    private String getCurrentDate ()
    {
        String day = getDayOfWeek();
        String month = getMonth();
        return day + month;
    }
    
    private String getMonth ()
    {
        String month = getMonthAndConvertToLowercase();
        month = capitalizeFirstLetter(month);
        return month;
    }
    
    private String capitalizeFirstLetter (String text)
    {
        return text.substring(0, 1) + text.substring(1);
    }
    
    private String getMonthAndConvertToLowercase ()
    {
        return LocalDate.now().getMonth().name().toLowerCase(Locale.ENGLISH);
    }
    
    private String getDayOfWeek ()
    {
        LocalDate date = LocalDate.now();
        String day = getDayOfMonthAndConvertToLowercase(date);
        day = capitalizeFirstLetter(day);
        day = getDaySuffix(date, day);
        return day;
    }
    
    private String getDaySuffix (LocalDate date, String day)
    {
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
        return day;
    }
    
    private String getDayOfMonthAndConvertToLowercase (LocalDate date)
    {
        return date.getDayOfWeek().name().toLowerCase(Locale.ENGLISH);
    }
    
    private void updateHandler (ActionEvent e)
    {
        LocalTime time = LocalTime.now();
        timeLabel.setText(time.getHour() + ":" + time.getMinute());
    }
    
    @Override
    public String toString ()
    {
        return "ClockBox{" + "timeLabel=" + timeLabel + '}';
    }
}
