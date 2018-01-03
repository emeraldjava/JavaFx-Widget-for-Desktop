package com.arivan.amin.widget;

import com.arivan.amin.widget.cpu.CpuProgressBar;
import com.arivan.amin.widget.forecast.*;
import com.arivan.amin.widget.memory.MemoryProgressBar;
import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Locale;

public class ApplicationMain extends Application
{
    private static final double LEFT_BOX_WIDTH = 0.80;
    private static final double RIGHT_BOX_WIDTH = 0.18;
    private PropertiesManager properties;
    private Scene scene;
    
    public static void main (String[] args)
    {
        launch(args);
    }
    
    @Override
    public void start (Stage primaryStage) throws Exception
    {
        properties = PropertiesManager.newInstance();
        primaryStage.initStyle(StageStyle.UTILITY);
        HBox mainHBox = new HBox();
        mainHBox.getStyleClass().add("pane");
        mainHBox.setPadding(new Insets(10));
        scene = new Scene(mainHBox, properties.getWidth(), properties.getHeight());
        scene.getStylesheets().add("/MainStyle.css");
        VBox leftVBox = new VBox();
        VBox topVBox = new VBox();
        HBox clockWeatherHBox = new HBox();
        VBox clockDateVBox = new VBox();
        clockDateVBox.setAlignment(Pos.TOP_CENTER);
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
        clockDateVBox.getChildren().addAll(timeLabel, dateLabel);
        VBox weatherVBox = new VBox();
        weatherVBox.getChildren().add(WeatherPane.newInstance(weatherVBox));
        clockWeatherHBox.getChildren().addAll(clockDateVBox, weatherVBox);
        topVBox.getChildren().add(clockWeatherHBox);
        VBox bottomVBox = new VBox(new Label("bottom V box"));
        leftVBox.getChildren().addAll(topVBox, bottomVBox);
        VBox rightVBox = new VBox();
        rightVBox.getChildren().add(CpuProgressBar.newInstance(rightVBox));
        rightVBox.getChildren().add(MemoryProgressBar.newInstance(rightVBox));
        mainHBox.getChildren().addAll(leftVBox, rightVBox);
        primaryStage.setScene(scene);
        primaryStage.setX(properties.getX());
        primaryStage.setY(properties.getY());
        mainHBox.prefWidthProperty().bind(scene.widthProperty());
        mainHBox.prefHeightProperty().bind(scene.heightProperty());
        // TODO 1/3/18 refactoring of these binding values
        leftVBox.prefWidthProperty().bind(mainHBox.widthProperty().multiply(LEFT_BOX_WIDTH));
        rightVBox.prefWidthProperty().bind(mainHBox.widthProperty().multiply(RIGHT_BOX_WIDTH));
        leftVBox.prefHeightProperty().bind(mainHBox.heightProperty());
        rightVBox.prefHeightProperty().bind(mainHBox.heightProperty());
        topVBox.prefHeightProperty().bind(leftVBox.heightProperty().multiply(0.5));
        clockWeatherHBox.prefHeightProperty().bind(topVBox.widthProperty());
        clockWeatherHBox.prefHeightProperty().bind(topVBox.heightProperty());
        bottomVBox.prefHeightProperty().bind(leftVBox.heightProperty().multiply(0.5));
        clockDateVBox.prefWidthProperty().bind(clockWeatherHBox.widthProperty().multiply(0.33));
        weatherVBox.prefWidthProperty().bind(clockWeatherHBox.widthProperty().multiply(0.66));
        clockDateVBox.prefHeightProperty().bind(clockWeatherHBox.heightProperty());
        weatherVBox.prefHeightProperty().bind(clockWeatherHBox.heightProperty());
        setStageChangeListeners(primaryStage);
        primaryStage.getIcons().add(new Image("widget icon.png"));
        primaryStage.show();
        primaryStage.setMaximized(true);
    }
    
    @NotNull
    private String getCurrentDate ()
    {
        LocalDate date = LocalDate.now();
        String day = date.getDayOfWeek().name().toLowerCase(Locale.ENGLISH);
        day = day.substring(0, 1) + day.substring(1);
        if (date.getDayOfMonth() == 1)
        {
            day += ' ' + String.valueOf(date.getDayOfMonth()) + "st of ";
        }
        else if (date.getDayOfMonth() == 2)
        {
            day += ' ' + String.valueOf(date.getDayOfMonth()) + "nd of ";
        }
        else if (date.getDayOfMonth() == 3)
        {
            day += ' ' + String.valueOf(date.getDayOfMonth()) + "rd of ";
        }
        else
        {
            day += ' ' + String.valueOf(date.getDayOfMonth()) + "th of ";
        }
        String month = date.getMonth().name().toLowerCase(Locale.ENGLISH);
        month = month.substring(0, 1) + month.substring(1);
        return day + month;
    }
    
    private void setStageChangeListeners (@NotNull Stage primaryStage)
    {
        scene.widthProperty().addListener(e ->
        {
            storePropertyValues(primaryStage);
        });
        scene.heightProperty().addListener(e ->
        {
            storePropertyValues(primaryStage);
        });
        primaryStage.xProperty().addListener(e ->
        {
            storePropertyValues(primaryStage);
        });
        primaryStage.yProperty().addListener(e ->
        {
            storePropertyValues(primaryStage);
        });
    }
    
    private void storePropertyValues (Stage primaryStage)
    {
        properties.setWidth((int) scene.widthProperty().get());
        properties.setHeight((int) scene.heightProperty().get());
        properties.setX((int) primaryStage.xProperty().get());
        properties.setY((int) primaryStage.yProperty().get());
        properties.storeValues();
    }
}
