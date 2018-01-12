package com.arivan.amin.widget.forecast;

import javafx.animation.*;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.Locale;
import java.util.logging.Logger;

public class WeatherBox extends HBox
{
    // TODO 1/8/18 provide location selection for forecast
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final WeatherData weatherData;
    private final Label temperatureLabel;
    private final ImageView currentWeatherImage;
    private final Label conditionLabel;
    private final Label humidityLabel;
    private final Label windLabel;
    private final Label cloudsLabel;
    private final Label sunLabel;
    private final VBox fourDaysVBox;
    private final Label precipitationLabel;
    
    private WeatherBox (@NotNull DoubleProperty parentWidth, @NotNull DoubleProperty parentHeight)
    {
        super();
        weatherData = OpenWeatherMap.newInstance(OpenWeatherMapProvider.newInstance());
        prefWidthProperty().bind(parentWidth.multiply(0.4));
        prefHeightProperty().bind(parentHeight);
        setSpacing(10);
        VBox todayVBox = new VBox();
        HBox iconHBox = new HBox();
        VBox labelsBox = new VBox(5);
        temperatureLabel = new Label();
        currentWeatherImage = new ImageView();
        currentWeatherImage.setPreserveRatio(true);
        iconHBox.getChildren().add(currentWeatherImage);
        conditionLabel = new Label();
        humidityLabel = new Label();
        windLabel = new Label();
        cloudsLabel = new Label();
        sunLabel = new Label();
        precipitationLabel = new Label();
        labelsBox.getChildren()
                .addAll(temperatureLabel, conditionLabel, humidityLabel, windLabel, cloudsLabel,
                        sunLabel, precipitationLabel);
        todayVBox.getChildren().addAll(iconHBox, labelsBox);
        fourDaysVBox = new VBox();
        getChildren().addAll(todayVBox, fourDaysVBox);
        todayVBox.prefWidthProperty().bind(prefWidthProperty());
        fourDaysVBox.prefWidthProperty().bind(prefWidthProperty());
        todayVBox.prefHeightProperty().bind(prefHeightProperty().multiply(0.6));
        fourDaysVBox.prefHeightProperty().bind(prefHeightProperty().multiply(0.3));
        iconHBox.prefWidthProperty().bind(todayVBox.widthProperty().multiply(0.7));
        iconHBox.prefHeightProperty().bind(todayVBox.heightProperty());
        currentWeatherImage.fitWidthProperty().bind(iconHBox.widthProperty().multiply(0.95));
        currentWeatherImage.fitHeightProperty().bind(iconHBox.heightProperty().multiply(0.95));
        fetchDataPeriodically();
    }
    
    private void fetchDataPeriodically ()
    {
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), e ->
        {
            updateValues();
        }));
        timeline.getKeyFrames().add(new KeyFrame(Duration.hours(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    
    private void createWeatherBoxes ()
    {
        createWeatherBox(weatherData.secondDayWeatherIcon(), weatherData.secondDayMaxTemperature(),
                weatherData.secondDayMinTemperature(), getWeatherBoxDate(1));
        createWeatherBox(weatherData.thirdDayWeatherIcon(), weatherData.thirdDayMaxTemperature(),
                weatherData.thirdDayMinTemperature(), getWeatherBoxDate(2));
        createWeatherBox(weatherData.fourthDayWeatherIcon(), weatherData.fourthDayMaxTemperature(),
                weatherData.fourthDayMinTemperature(), getWeatherBoxDate(3));
        createWeatherBox(weatherData.fifthDayWeatherIcon(), weatherData.fifthDayMaxTemperature(),
                weatherData.fifthDayMinTemperature(), getWeatherBoxDate(4));
    }
    
    @NotNull
    private static String getWeatherBoxDate (int datePlusDays)
    {
        LocalDate date = LocalDate.now().plusDays(datePlusDays);
        String dayOfWeek = date.getDayOfWeek().name().toLowerCase(Locale.ENGLISH);
        dayOfWeek = dayOfWeek.substring(0, 1).toUpperCase(Locale.ENGLISH) + dayOfWeek.substring(1);
        String day;
        switch (date.getDayOfMonth())
        {
            case 1:
                day = String.valueOf(date.getDayOfMonth()) + "st";
                break;
            case 2:
                day = String.valueOf(date.getDayOfMonth()) + "nd";
                break;
            case 3:
                day = String.valueOf(date.getDayOfMonth()) + "rd";
                break;
            default:
                day = String.valueOf(date.getDayOfMonth()) + "th";
                break;
        }
        return dayOfWeek + "  " + day;
    }
    
    @NotNull
    public static WeatherBox newInstance (DoubleProperty parentWidth, DoubleProperty parentHeight)
    {
        return new WeatherBox(parentWidth, parentHeight);
    }
    
    private void createWeatherBox (String weatherIcon, int maxTemp, int minTemp, String date)
    {
        HBox dayHBox = new HBox();
        HBox dayIconHBox = new HBox();
        ImageView dayImageView = new ImageView(new Image(weatherIcon));
        dayImageView.setPreserveRatio(true);
        dayIconHBox.getChildren().add(dayImageView);
        VBox labelsVBox = new VBox();
        labelsVBox.setPadding(new Insets(10, 0, 0, 0));
        Label temperaturesLabel = new Label(maxTemp + "C   -  " + minTemp + 'C');
        Label dateLabel = new Label(date);
        labelsVBox.getChildren().addAll(temperaturesLabel, dateLabel);
        dayHBox.getChildren().addAll(dayIconHBox, labelsVBox);
        dayHBox.prefWidthProperty().bind(fourDaysVBox.widthProperty());
        dayHBox.prefHeightProperty().bind(fourDaysVBox.heightProperty().multiply(0.23));
        dayIconHBox.prefWidthProperty().bind(dayHBox.prefWidthProperty().multiply(0.5));
        dayIconHBox.prefHeightProperty().bind(dayHBox.prefHeightProperty());
        labelsVBox.prefWidthProperty().bind(dayHBox.prefWidthProperty().multiply(0.5));
        dayImageView.fitWidthProperty().bind(dayIconHBox.widthProperty().multiply(0.95));
        dayImageView.fitHeightProperty().bind(dayIconHBox.heightProperty().multiply(0.95));
        fourDaysVBox.getChildren().add(dayHBox);
    }
    
    private void updateValues ()
    {
        weatherData.updateWeatherData();
        currentWeatherImage.setImage(new Image(weatherData.weatherIcon()));
        temperatureLabel.setText(
                "Temperature: " + weatherData.temperatureValue() + weatherData.temperatureUnit());
        conditionLabel.setText("Condition: " + weatherData.weatherCondition());
        humidityLabel.setText("Humidity: " + weatherData.humidityValue() + '%');
        windLabel.setText(
                "Wind: " + weatherData.windsSpeed() + " mps " + weatherData.windDirection());
        cloudsLabel.setText("Clouds: " + weatherData.cloudsRate() + '%');
        sunLabel.setText("Sun rise: " + weatherData.sunrise() + " set: " + weatherData.sunset());
        if (!weatherData.precipitationType().isEmpty())
        {
            precipitationLabel.setText("Precipitation: " + weatherData.precipitationType() + "  " +
                    weatherData.precipitationValue() + "mm");
        }
        else
        {
            precipitationLabel.setText("");
        }
        fourDaysVBox.getChildren().clear();
        createWeatherBoxes();
    }
    
    @Override
    public String toString ()
    {
        return "WeatherBox{" + "weatherData=" + weatherData + ", temperatureLabel=" +
                temperatureLabel + ", currentWeatherImage=" + currentWeatherImage +
                ", conditionLabel=" + conditionLabel + ", humidityLabel=" + humidityLabel +
                ", windLabel=" + windLabel + ", cloudsLabel=" + cloudsLabel + ", sunLabel=" +
                sunLabel + ", fourDaysVBox=" + fourDaysVBox + ", precipitationLabel=" +
                precipitationLabel + '}';
    }
}
