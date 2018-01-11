package com.arivan.amin.widget.ui.boxes;

import com.arivan.amin.widget.forecast.WeatherPane;
import com.arivan.amin.widget.system.details.SystemDetailsPane;
import javafx.animation.*;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Locale;

public class LeftTopBox extends VBox
{
    private LeftTopBox (@NotNull DoubleProperty parentWidthProperty,
            @NotNull DoubleProperty parentHeightProperty)
    {
        super();
        prefWidthProperty().bind(parentWidthProperty);
        prefHeightProperty().bind(parentHeightProperty.multiply(0.4));
        HBox clockWeatherHBox = new HBox();
        VBox clockDateVBox = new VBox(20);
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
        clockDateVBox.getChildren().addAll(dateTimeVBox, systemDetailsVBox);
        systemDetailsVBox.getChildren().add(SystemDetailsPane.newInstance(systemDetailsVBox));
        VBox weatherVBox = new VBox();
        weatherVBox.getChildren().add(WeatherPane.newInstance(weatherVBox));
        clockWeatherHBox.getChildren().addAll(clockDateVBox, weatherVBox);
        getChildren().add(clockWeatherHBox);
        clockWeatherHBox.prefHeightProperty().bind(widthProperty());
        clockWeatherHBox.prefHeightProperty().bind(heightProperty());
        clockDateVBox.prefWidthProperty().bind(clockWeatherHBox.widthProperty().multiply(0.33));
        weatherVBox.prefWidthProperty().bind(clockWeatherHBox.widthProperty().multiply(0.66));
        clockDateVBox.prefHeightProperty().bind(clockWeatherHBox.heightProperty());
        weatherVBox.prefHeightProperty().bind(clockWeatherHBox.heightProperty());
        dateTimeVBox.prefWidthProperty().bind(clockDateVBox.widthProperty());
        systemDetailsVBox.prefWidthProperty().bind(clockDateVBox.widthProperty());
        dateTimeVBox.prefHeightProperty().bind(clockDateVBox.heightProperty().multiply(0.33));
        systemDetailsVBox.prefHeightProperty().bind(clockDateVBox.heightProperty().multiply(0.66));
    }
    
    @NotNull
    public static LeftTopBox newInstance (DoubleProperty widthProperty,
            DoubleProperty heightProperty)
    {
        return new LeftTopBox(widthProperty, heightProperty);
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
