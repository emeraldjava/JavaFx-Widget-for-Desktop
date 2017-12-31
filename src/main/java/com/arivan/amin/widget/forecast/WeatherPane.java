package com.arivan.amin.widget.forecast;

import com.arivan.amin.widget.icons.ApplicationIcons;
import com.arivan.amin.widget.icons.HtcIcons;
import javafx.animation.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public class WeatherPane extends Pane
{
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final WeatherData weatherData;
    ApplicationIcons icons;
    private final Label temperatureLabel;
    private final ImageView currentWeatherImage;
    private final Label conditionLabel;
    private final Label humidityLabel;
    private final Label windLabel;
    private final Label cloudsLabel;
    private final Label sunLabel;
    
    private WeatherPane (@NotNull Pane pane)
    {
        super();
        prefWidthProperty().bind(pane.widthProperty());
        prefHeightProperty().bind(pane.heightProperty());
        weatherData = OpenWeatherMap.newInstance();
        icons = HtcIcons.newInstance();
        HBox mainHBox = new HBox(10);
        VBox todayVBox = new VBox(10);
        HBox iconHBox = new HBox(10);
        temperatureLabel = new Label();
        currentWeatherImage = new ImageView();
        iconHBox.getChildren().add(currentWeatherImage);
        conditionLabel = new Label();
        humidityLabel = new Label();
        windLabel = new Label();
        cloudsLabel = new Label();
        sunLabel = new Label();
        // TODO when there is precipitation show amount
        todayVBox.getChildren()
                .addAll(iconHBox, temperatureLabel, conditionLabel, humidityLabel, windLabel,
                        cloudsLabel, sunLabel);
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
        currentWeatherImage.fitWidthProperty().bind(iconHBox.widthProperty().multiply(0.95));
        currentWeatherImage.fitHeightProperty().bind(iconHBox.heightProperty().multiply(0.95));
        updateValues();
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), e ->
        {
            updateValues();
        }));
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(30)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    
    @NotNull
    public static WeatherPane newInstance (Pane pane)
    {
        return new WeatherPane(pane);
    }
    
    private void updateValues ()
    {
        weatherData.updateWeatherData();
        currentWeatherImage.setImage(new Image(weatherData.weatherIcon()));
        temperatureLabel.setText(
                "Temperature: " + weatherData.temperatureValue() + weatherData.temperatureUnit());
        conditionLabel.setText("Weather condition: " + weatherData.weatherCondition());
        humidityLabel.setText("Humidity: " + weatherData.humidityValue() + '%');
        windLabel.setText(
                "Wind: " + weatherData.windName() + "  " + weatherData.windsSpeed() + " mps " +
                        weatherData.windDirection());
        cloudsLabel.setText("Clouds: " + weatherData.cloudsRate() + '%');
        sunLabel.setText("Sunrise: " + weatherData.sunrise() + " Sunset: " + weatherData.sunset());
    }
    
    @Override
    public String toString ()
    {
        return "WeatherPane{" + "weatherData=" + weatherData + '}';
    }
}
