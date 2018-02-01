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
    private WeatherDataProvider weatherDataProvider;
    private LocationProvider locationProvider;
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
        labelsBox.getChildren().addAll(new Label(
                "City: " + locationProvider.cityName() + ", " + locationProvider.countryName()));
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
        weatherDataProvider = OpenWeatherMap.newInstance(OpenWeatherMapProvider.newInstance());
    }
    
    private void initializeFields ()
    {
        determineWeatherProvider();
        locationProvider = GeoLiteLocationProvider.newInstance();
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
        createWeatherBox(weatherDataProvider.secondDayWeatherIcon(),
                weatherDataProvider.secondDayMaxTemperature(),
                weatherDataProvider.secondDayMinTemperature(), getWeatherBoxDate(1));
        createWeatherBox(weatherDataProvider.thirdDayWeatherIcon(),
                weatherDataProvider.thirdDayMaxTemperature(),
                weatherDataProvider.thirdDayMinTemperature(), getWeatherBoxDate(2));
        createWeatherBox(weatherDataProvider.fourthDayWeatherIcon(),
                weatherDataProvider.fourthDayMaxTemperature(),
                weatherDataProvider.fourthDayMinTemperature(), getWeatherBoxDate(3));
        createWeatherBox(weatherDataProvider.fifthDayWeatherIcon(),
                weatherDataProvider.fifthDayMaxTemperature(),
                weatherDataProvider.fifthDayMinTemperature(), getWeatherBoxDate(4));
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
        weatherDataProvider.updateWeatherData();
    }
    
    private void updateFourDaysForecast ()
    {
        fourDaysVBox.getChildren().clear();
        createWeatherBoxes();
    }
    
    private void updateLabelValues ()
    {
        temperatureLabel.setText(
                "Temperature: " + convertTemperature(weatherDataProvider.temperatureValue()));
        conditionLabel.setText("Condition: " + weatherDataProvider.weatherCondition());
        humidityLabel.setText("Humidity: " + weatherDataProvider.humidityValue() + '%');
        windLabel.setText("Wind: " + weatherDataProvider.windsSpeed() + " mps " +
                weatherDataProvider.windDirection());
        cloudsLabel.setText("Clouds: " + weatherDataProvider.cloudsRate() + '%');
        sunLabel.setText("Sun rise: " + weatherDataProvider.sunrise() + " set: " +
                weatherDataProvider.sunset());
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
        currentWeatherImage.setImage(new Image(weatherDataProvider.weatherIcon()));
    }
    
    private void setPrecipitationIfExists ()
    {
        if (!weatherDataProvider.precipitationType().isEmpty())
        {
            precipitationLabel.setText(
                    "Precipitation: " + weatherDataProvider.precipitationType() + "  " +
                            weatherDataProvider.precipitationValue() + "mm");
        }
        else
        {
            precipitationLabel.setText("");
        }
    }
    
    @Override
    public String toString ()
    {
        return "WeatherBox{" + "weatherDataProvider=" + weatherDataProvider +
                ", temperatureLabel=" + temperatureLabel + ", currentWeatherImage=" +
                currentWeatherImage + ", conditionLabel=" + conditionLabel + ", humidityLabel=" +
                humidityLabel + ", windLabel=" + windLabel + ", cloudsLabel=" + cloudsLabel +
                ", sunLabel=" + sunLabel + ", fourDaysVBox=" + fourDaysVBox +
                ", precipitationLabel=" + precipitationLabel + ", propertiesManager=" +
                propertiesManager + '}';
    }
}
