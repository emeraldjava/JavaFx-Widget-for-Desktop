package com.arivan.amin.widget;

import com.arivan.amin.widget.cpu.CpuProgressBar;
import com.arivan.amin.widget.forecast.WeatherPane;
import com.arivan.amin.widget.memory.MemoryProgressBar;
import com.arivan.amin.widget.news.feed.RssReaderBox;
import com.arivan.amin.widget.system.details.SystemDetailsPane;
import com.arivan.amin.widget.todo.TasksBox;
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
    public void start (Stage primaryStage)
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
        topVBox.getChildren().add(clockWeatherHBox);
        HBox middleHBox = new HBox();
        HBox bottomHBox = new HBox();
        leftVBox.getChildren().addAll(topVBox, middleHBox, bottomHBox);
        VBox rssVBox = new VBox();
        VBox topProcessesVBox = new VBox();
        VBox todoVBox = new VBox();
        bottomHBox.getChildren().addAll(rssVBox, topProcessesVBox, todoVBox);
        VBox rightVBox = new VBox();
        rightVBox.getChildren().add(CpuProgressBar.newInstance(rightVBox));
        rightVBox.getChildren().add(MemoryProgressBar.newInstance(rightVBox));
        rssVBox.getChildren().add(RssReaderBox.newInstance());
        todoVBox.getChildren().add(TasksBox.newInstance());
        mainHBox.getChildren().addAll(leftVBox, rightVBox);
        primaryStage.setScene(scene);
        primaryStage.setX(properties.getX());
        primaryStage.setY(properties.getY());
        mainHBox.prefWidthProperty().bind(scene.widthProperty());
        mainHBox.prefHeightProperty().bind(scene.heightProperty());
        leftVBox.prefWidthProperty().bind(mainHBox.widthProperty().multiply(LEFT_BOX_WIDTH));
        rightVBox.prefWidthProperty().bind(mainHBox.widthProperty().multiply(RIGHT_BOX_WIDTH));
        leftVBox.prefHeightProperty().bind(mainHBox.heightProperty());
        rightVBox.prefHeightProperty().bind(mainHBox.heightProperty());
        topVBox.prefHeightProperty().bind(leftVBox.heightProperty().multiply(0.5));
        clockWeatherHBox.prefHeightProperty().bind(topVBox.widthProperty());
        clockWeatherHBox.prefHeightProperty().bind(topVBox.heightProperty());
        middleHBox.prefHeightProperty().bind(leftVBox.heightProperty().multiply(0.1));
        bottomHBox.prefHeightProperty().bind(leftVBox.heightProperty().multiply(0.40));
        bottomHBox.prefWidthProperty().bind(leftVBox.widthProperty());
        clockDateVBox.prefWidthProperty().bind(clockWeatherHBox.widthProperty().multiply(0.33));
        weatherVBox.prefWidthProperty().bind(clockWeatherHBox.widthProperty().multiply(0.66));
        clockDateVBox.prefHeightProperty().bind(clockWeatherHBox.heightProperty());
        weatherVBox.prefHeightProperty().bind(clockWeatherHBox.heightProperty());
        dateTimeVBox.prefWidthProperty().bind(clockDateVBox.widthProperty());
        systemDetailsVBox.prefWidthProperty().bind(clockDateVBox.widthProperty());
        dateTimeVBox.prefHeightProperty().bind(clockDateVBox.heightProperty().multiply(0.33));
        systemDetailsVBox.prefHeightProperty().bind(clockDateVBox.heightProperty().multiply(0.66));
        rssVBox.prefWidthProperty().bind(bottomHBox.widthProperty().multiply(0.49));
        topProcessesVBox.prefWidthProperty().bind(bottomHBox.widthProperty().multiply(0.29));
        todoVBox.prefWidthProperty().bind(bottomHBox.widthProperty().multiply(0.2));
        rssVBox.prefHeightProperty().bind(bottomHBox.heightProperty());
        topProcessesVBox.prefHeightProperty().bind(bottomHBox.heightProperty());
        todoVBox.prefHeightProperty().bind(bottomHBox.heightProperty());
        setStageChangeListeners(primaryStage);
        primaryStage.getIcons().add(new Image("widget icon.png"));
        primaryStage.show();
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
