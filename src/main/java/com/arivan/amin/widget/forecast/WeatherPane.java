package com.arivan.amin.widget.forecast;

import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.Locale;
import java.util.logging.Logger;

public class WeatherPane extends Pane
{
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
    
    private WeatherPane (@NotNull Pane pane)
    {
        super();
        bindPanePropertiesWithContainer(pane);
        weatherData = OpenWeatherMap.newInstance(OpenWeatherMapProvider.newInstance());
        HBox mainHBox = new HBox(10);
        getChildren().add(mainHBox);
        VBox todayVBox = new VBox(5);
        HBox iconHBox = new HBox(10);
        // TODO 1/8/18 provide location selection for forecast
        temperatureLabel = new Label();
        currentWeatherImage = new ImageView();
        iconHBox.getChildren().add(currentWeatherImage);
        conditionLabel = new Label();
        humidityLabel = new Label();
        windLabel = new Label();
        cloudsLabel = new Label();
        sunLabel = new Label();
        precipitationLabel = new Label();
        todayVBox.getChildren()
                .addAll(iconHBox, temperatureLabel, conditionLabel, humidityLabel, windLabel,
                        cloudsLabel, sunLabel, precipitationLabel);
        fourDaysVBox = new VBox();
        mainHBox.getChildren().addAll(todayVBox, fourDaysVBox);
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
    
    private void bindPanePropertiesWithContainer (@NotNull Pane pane)
    {
        prefWidthProperty().bind(pane.widthProperty());
        prefHeightProperty().bind(pane.heightProperty());
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
                day = String.valueOf(date.getDayOfMonth()) + "st of ";
                break;
            case 2:
                day = String.valueOf(date.getDayOfMonth()) + "nd of ";
                break;
            case 3:
                day = String.valueOf(date.getDayOfMonth()) + "rd of ";
                break;
            default:
                day = String.valueOf(date.getDayOfMonth()) + "th of ";
                break;
        }
        String month = date.getMonth().name().toLowerCase(Locale.ENGLISH);
        month = month.substring(0, 1).toUpperCase(Locale.ENGLISH) + month.substring(1);
        return dayOfWeek + "  " + day + month;
    }
    
    @NotNull
    public static WeatherPane newInstance (Pane pane)
    {
        return new WeatherPane(pane);
    }
    
    private void createWeatherBox (String weatherIcon, int maxTemp, int minTemp, String date)
    {
        HBox dayHBox = new HBox();
        HBox dayIconHBox = new HBox();
        ImageView dayImageView = new ImageView(new Image(weatherIcon));
        dayIconHBox.getChildren().add(dayImageView);
        VBox labelsVBox = new VBox();
        labelsVBox.setPadding(new Insets(10, 0, 0, 0));
        Label temperaturesLabel = new Label(maxTemp + "C   /  " + minTemp + 'C');
        Label dateLabel = new Label(date);
        labelsVBox.getChildren().addAll(temperaturesLabel, dateLabel);
        dayHBox.getChildren().addAll(dayIconHBox, labelsVBox);
        dayHBox.prefWidthProperty().bind(fourDaysVBox.widthProperty());
        dayHBox.prefHeightProperty().bind(fourDaysVBox.heightProperty().multiply(0.245));
        dayIconHBox.prefWidthProperty().bind(dayHBox.widthProperty().multiply(0.4));
        dayIconHBox.prefHeightProperty().bind(dayHBox.heightProperty());
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
        conditionLabel.setText("Weather condition: " + weatherData.weatherCondition());
        humidityLabel.setText("Humidity: " + weatherData.humidityValue() + '%');
        windLabel.setText(
                "Wind: " + weatherData.windName() + "  " + weatherData.windsSpeed() + " mps " +
                        weatherData.windDirection());
        cloudsLabel.setText("Clouds: " + weatherData.cloudsRate() + '%');
        sunLabel.setText("Sunrise: " + weatherData.sunrise() + " Sunset: " + weatherData.sunset());
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
        return "WeatherPane{" + "weatherData=" + weatherData + ", temperatureLabel=" +
                temperatureLabel + ", currentWeatherImage=" + currentWeatherImage +
                ", conditionLabel=" + conditionLabel + ", humidityLabel=" + humidityLabel +
                ", windLabel=" + windLabel + ", cloudsLabel=" + cloudsLabel + ", sunLabel=" +
                sunLabel + ", fourDaysVBox=" + fourDaysVBox + ", precipitationLabel=" +
                precipitationLabel + '}';
    }
}
