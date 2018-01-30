package com.arivan.amin.widget.forecast;

import com.arivan.amin.widget.PropertiesManager;
import javafx.animation.*;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.time.LocalDate;
import java.util.Locale;
import java.util.logging.Logger;

public class WeatherBox extends HBox
{
    private final Logger logger = Logger.getLogger(getClass().getName());
    private WeatherData weatherData;
    private Label temperatureLabel;
    private ImageView currentWeatherImage;
    private Label conditionLabel;
    private Label humidityLabel;
    private Label windLabel;
    private Label cloudsLabel;
    private Label sunLabel;
    private VBox fourDaysVBox;
    private Label precipitationLabel;
    @SuppressWarnings ("InstanceVariableOfConcreteClass")
    private PropertiesManager propertiesManager;
    
    private WeatherBox (DoubleProperty parentWidth, DoubleProperty parentHeight)
    {
        determineWeatherProvider();
        initializeFields();
        VBox todayVBox = new VBox();
        HBox iconHBox = new HBox();
        VBox labelsBox = new VBox(5);
        addItemsToBoxes(iconHBox, labelsBox);
        todayVBox.getChildren().addAll(iconHBox, labelsBox);
        getChildren().addAll(todayVBox, fourDaysVBox);
        bindBoxSizeToParent(parentWidth, parentHeight);
        bindOtherBoxes(todayVBox, iconHBox);
        fetchDataPeriodically();
    }
    
    private void addItemsToBoxes (HBox iconHBox, VBox labelsBox)
    {
        iconHBox.getChildren().add(currentWeatherImage);
        labelsBox.getChildren()
                .addAll(temperatureLabel, conditionLabel, humidityLabel, windLabel, cloudsLabel,
                        sunLabel, precipitationLabel);
    }
    
    private void bindOtherBoxes (VBox todayVBox, HBox iconHBox)
    {
        todayVBox.prefWidthProperty().bind(prefWidthProperty());
        todayVBox.prefHeightProperty().bind(prefHeightProperty().multiply(0.6));
        fourDaysVBox.prefWidthProperty().bind(prefWidthProperty());
        fourDaysVBox.prefHeightProperty().bind(prefHeightProperty().multiply(0.3));
        iconHBox.prefWidthProperty().bind(todayVBox.widthProperty().multiply(0.7));
        iconHBox.prefHeightProperty().bind(todayVBox.heightProperty());
        currentWeatherImage.fitWidthProperty().bind(iconHBox.widthProperty().multiply(0.95));
        currentWeatherImage.fitHeightProperty().bind(iconHBox.heightProperty().multiply(0.95));
    }
    
    private void bindBoxSizeToParent (DoubleProperty parentWidth, DoubleProperty parentHeight)
    {
        prefWidthProperty().bind(parentWidth.multiply(0.4));
        prefHeightProperty().bind(parentHeight);
    }
    
    private void determineWeatherProvider ()
    {
        weatherData = OpenWeatherMap.newInstance(OpenWeatherMapProvider.newInstance());
    }
    
    private void initializeFields ()
    {
        propertiesManager = PropertiesManager.newInstance();
        temperatureLabel = new Label();
        currentWeatherImage = new ImageView();
        currentWeatherImage.setPreserveRatio(true);
        conditionLabel = new Label();
        humidityLabel = new Label();
        windLabel = new Label();
        cloudsLabel = new Label();
        sunLabel = new Label();
        precipitationLabel = new Label();
        fourDaysVBox = new VBox();
    }
    
    private void fetchDataPeriodically ()
    {
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), this::weatherUpdateHandler));
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
        Label temperaturesLabel =
                new Label(convertTemperature(maxTemp) + "   /   " + convertTemperature(minTemp));
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
    
    private void weatherUpdateHandler (ActionEvent e)
    {
        updateWeatherDataFromProvider();
        setNewIconImage();
        updateLabelValues();
        setPrecipitationIfExists();
        updateFourDaysForecast();
    }
    
    private void updateWeatherDataFromProvider ()
    {
        weatherData.updateWeatherData();
    }
    
    private void updateFourDaysForecast ()
    {
        fourDaysVBox.getChildren().clear();
        createWeatherBoxes();
    }
    
    private void updateLabelValues ()
    {
        temperatureLabel
                .setText("Temperature: " + convertTemperature(weatherData.temperatureValue()));
        conditionLabel.setText("Condition: " + weatherData.weatherCondition());
        humidityLabel.setText("Humidity: " + weatherData.humidityValue() + '%');
        windLabel.setText(
                "Wind: " + weatherData.windsSpeed() + " mps " + weatherData.windDirection());
        cloudsLabel.setText("Clouds: " + weatherData.cloudsRate() + '%');
        sunLabel.setText("Sun rise: " + weatherData.sunrise() + " set: " + weatherData.sunset());
    }
    
    private String convertTemperature (int temp)
    {
        String tempUnit = propertiesManager.getTemperature();
        if ("k".equalsIgnoreCase(tempUnit))
        {
            return UnitConverter.convertCelsiusToKelvin(temp) + " K";
        }
        if ("f".equalsIgnoreCase(tempUnit))
        {
            return UnitConverter.convertCelsiusToFahrenheit(temp) + " F";
        }
        return temp + " C";
    }
    
    private void setNewIconImage ()
    {
        currentWeatherImage.setImage(new Image(weatherData.weatherIcon()));
    }
    
    private void setPrecipitationIfExists ()
    {
        if (!weatherData.precipitationType().isEmpty())
        {
            precipitationLabel.setText("Precipitation: " + weatherData.precipitationType() + "  " +
                    weatherData.precipitationValue() + "mm");
        }
        else
        {
            precipitationLabel.setText("");
        }
    }
    
    @Override
    public String toString ()
    {
        return "WeatherBox{" + "weatherData=" + weatherData + ", temperatureLabel=" +
                temperatureLabel + ", currentWeatherImage=" + currentWeatherImage +
                ", conditionLabel=" + conditionLabel + ", humidityLabel=" + humidityLabel +
                ", windLabel=" + windLabel + ", cloudsLabel=" + cloudsLabel + ", sunLabel=" +
                sunLabel + ", fourDaysVBox=" + fourDaysVBox + ", precipitationLabel=" +
                precipitationLabel + ", propertiesManager=" + propertiesManager + '}';
    }
}
