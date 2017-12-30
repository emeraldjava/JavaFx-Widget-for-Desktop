package com.arivan.amin.widget.forecast;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public class WeatherPane extends Pane
{
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final WeatherData weatherData;
    
    private WeatherPane (@NotNull Pane pane)
    {
        super();
        prefWidthProperty().bind(pane.widthProperty());
        prefHeightProperty().bind(pane.heightProperty());
        weatherData = OpenWeatherMap.newInstance();
        HBox mainHBox = new HBox(10);
        VBox todayVBox = new VBox(10);
        HBox iconHBox = new HBox(10);
        Label temperatureLabel = new Label("temperature");
        ImageView imageView = new ImageView(new Image("/shower and snow.png"));
        iconHBox.getChildren().add(imageView);
        Label conditionLabel = new Label("Condition");
        Label minMaxTempLabel = new Label("min and max temp");
        Label humidityLabel = new Label("humidity");
        Label windLabel = new Label("wind");
        Label cloudsLabel = new Label("clouds");
        todayVBox.getChildren()
                .addAll(iconHBox, temperatureLabel, conditionLabel, minMaxTempLabel, humidityLabel,
                        windLabel, cloudsLabel);
        VBox fourDaysVBox = new VBox(new Label("four days forecast"));
        mainHBox.getChildren().addAll(todayVBox, fourDaysVBox);
        getChildren().add(mainHBox);
        mainHBox.prefWidthProperty().bind(widthProperty());
        mainHBox.prefHeightProperty().bind(heightProperty());
        todayVBox.prefWidthProperty().bind(mainHBox.widthProperty().multiply(0.5));
        fourDaysVBox.prefWidthProperty().bind(mainHBox.widthProperty().multiply(0.5));
        todayVBox.prefHeightProperty().bind(mainHBox.heightProperty());
        fourDaysVBox.prefHeightProperty().bind(mainHBox.heightProperty());
        iconHBox.prefWidthProperty().bind(todayVBox.widthProperty());
        iconHBox.prefHeightProperty().bind(todayVBox.heightProperty().multiply(0.5));
        imageView.fitWidthProperty().bind(iconHBox.widthProperty().multiply(0.95));
        imageView.fitHeightProperty().bind(iconHBox.heightProperty().multiply(0.95));
    }
    
    @NotNull
    public static WeatherPane newInstance (Pane pane)
    {
        return new WeatherPane(pane);
    }
    
    @Override
    public String toString ()
    {
        return "WeatherPane{" + "weatherData=" + weatherData + '}';
    }
}
