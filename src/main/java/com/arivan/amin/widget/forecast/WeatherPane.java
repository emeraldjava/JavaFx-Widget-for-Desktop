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
        weatherData = OpenWeatherMap.newInstance();
        HBox mainHBox = new HBox(10);
        VBox todayVBox = new VBox(10);
        HBox iconHBox = new HBox(10);
        Label temperatureLabel = new Label("temperature");
        ImageView imageView = new ImageView(new Image("/cl2.png"));
        iconHBox.getChildren().addAll(imageView, temperatureLabel);
        Label conditionLabel = new Label("Condition");
        Label minMaxTempLabel = new Label("min and max temp");
        Label humidityLabel = new Label("humidity");
        Label windLabel = new Label("wind");
        Label cloudsLabel = new Label("clouds");
        todayVBox.getChildren()
                .addAll(iconHBox, conditionLabel, minMaxTempLabel, humidityLabel, windLabel,
                        cloudsLabel);
        VBox fourDaysVBox = new VBox(new ImageView(new Image("cl64.png")));
        mainHBox.getChildren().addAll(todayVBox, fourDaysVBox);
        getChildren().add(mainHBox);
        prefWidthProperty().bind(pane.widthProperty());
        prefHeightProperty().bind(pane.heightProperty());
        mainHBox.prefWidthProperty().bind(widthProperty());
        mainHBox.prefHeightProperty().bind(heightProperty());
        todayVBox.prefWidthProperty().bind(mainHBox.widthProperty().multiply(0.5));
        fourDaysVBox.prefWidthProperty().bind(mainHBox.widthProperty().multiply(0.5));
        todayVBox.prefHeightProperty().bind(mainHBox.heightProperty());
        fourDaysVBox.prefHeightProperty().bind(mainHBox.heightProperty());
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
