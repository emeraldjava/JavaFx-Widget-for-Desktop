package com.arivan.amin.widget;

import com.arivan.amin.widget.forecast.UndergroundWeatherData;
import com.arivan.amin.widget.forecast.WeatherData;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ApplicationMain extends Application
{
    private PropertiesManager properties;
    WeatherData weatherData;
    private Scene scene;
    
    public static void main (String[] args)
    {
        launch(args);
    }
    
    @Override
    public void start (Stage primaryStage) throws Exception
    {
        properties = PropertiesManager.newInstance();
        weatherData = UndergroundWeatherData.newInstance();
        System.out.println("current today status");
        System.out.println("temp is " + weatherData.getTemperature() + " C");
        System.out.println("pressure is " + weatherData.getPressure());
        System.out.println("humidity is " + weatherData.getHumidity() + " percent");
        System.out.println("weather condition is " + weatherData.getWeather());
        System.out.println("weather description is " + weatherData.getWeatherDescription());
        System.out.println("clouds rate is " + weatherData.getCloudsRate());
        System.out.println("wind speed is " + weatherData.getWindsSpeed());
        System.out.println("wind direction is " + weatherData.getWindsDirection());
        System.out.println();
        System.out.println("second day forecast");
        // primaryStage.initStyle(StageStyle.UTILITY);
        // HBox mainHBox = new HBox();
        // mainHBox.getStyleClass().add("pane");
        // mainHBox.setPadding(new Insets(10));
        // scene = new Scene(mainHBox, properties.getWidth(), properties.getHeight());
        // scene.getStylesheets().add("/MainStyle.css");
        // VBox leftVBox = new VBox();
        // VBox topVBox = new VBox();
        // HBox clockWeatherHBox = new HBox();
        // VBox clockDateVBox = new VBox();
        // clockDateVBox.setAlignment(Pos.TOP_CENTER);
        // Label timeLabel = new Label("9:27");
        // timeLabel.getStyleClass().add("time-label");
        // Label dateLabel = new Label("23-12-2017");
        // dateLabel.getStyleClass().add("date-label");
        // clockDateVBox.getChildren().addAll(timeLabel, dateLabel);
        // VBox todayWeatherVBox = new VBox(new Label("forecast "));
        // VBox fourDaysWeatherVBox = new VBox(new Label("4 days forecast "));
        // clockWeatherHBox.getChildren().addAll(clockDateVBox, todayWeatherVBox, fourDaysWeatherVBox);
        // topVBox.getChildren().add(clockWeatherHBox);
        // VBox bottomVBox = new VBox(new Label("bottom vbox"));
        // leftVBox.getChildren().addAll(topVBox, bottomVBox);
        // VBox rightVBox = new VBox();
        // rightVBox.getChildren().add(CpuProgressBar.newInstance(rightVBox));
        // rightVBox.getChildren().add(MemoryProgressBar.newInstance(rightVBox));
        // mainHBox.getChildren().addAll(leftVBox, rightVBox);
        // primaryStage.setScene(scene);
        // primaryStage.setX(properties.getX());
        // primaryStage.setY(properties.getY());
        // mainHBox.prefWidthProperty().bind(scene.widthProperty());
        // mainHBox.prefHeightProperty().bind(scene.heightProperty());
        // leftVBox.prefWidthProperty().bind(mainHBox.widthProperty().multiply(0.75));
        // rightVBox.prefWidthProperty().bind(mainHBox.widthProperty().multiply(0.23));
        // leftVBox.prefHeightProperty().bind(mainHBox.heightProperty());
        // rightVBox.prefHeightProperty().bind(mainHBox.heightProperty());
        // topVBox.prefHeightProperty().bind(leftVBox.heightProperty().multiply(0.5));
        // clockWeatherHBox.prefHeightProperty().bind(topVBox.widthProperty());
        // clockWeatherHBox.prefHeightProperty().bind(topVBox.heightProperty());
        // bottomVBox.prefHeightProperty().bind(leftVBox.heightProperty().multiply(0.5));
        // clockDateVBox.prefWidthProperty().bind(clockWeatherHBox.widthProperty().multiply(0.33));
        // todayWeatherVBox.prefWidthProperty().bind(clockWeatherHBox.widthProperty().multiply(0.33));
        // fourDaysWeatherVBox.prefWidthProperty().bind(clockWeatherHBox.widthProperty().multiply(0.33));
        // clockDateVBox.prefHeightProperty().bind(clockWeatherHBox.heightProperty());
        // todayWeatherVBox.prefHeightProperty().bind(clockWeatherHBox.heightProperty());
        // fourDaysWeatherVBox.prefHeightProperty().bind(clockWeatherHBox.heightProperty());
        // setStageChangeListeners(primaryStage);
        // primaryStage.show();
        System.exit(0);
    }
    
    private void setStageChangeListeners (Stage primaryStage)
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
