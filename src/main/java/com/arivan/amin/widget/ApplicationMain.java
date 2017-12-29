package com.arivan.amin.widget;

import com.arivan.amin.widget.forecast.OpenWeatherMap;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ApplicationMain extends Application
{
    private PropertiesManager properties;
    private Scene scene;
    
    public static void main (String[] args)
    {
        launch(args);
    }
    
    @Override
    public void start (Stage primaryStage) throws Exception
    {
        OpenWeatherMap weatherMap = OpenWeatherMap.newInstance();
        System.out.println(weatherMap.locationName());
        // properties = PropertiesManager.newInstance();
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
        // Label timeLabel = new Label();
        // Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e ->
        // {
        //     LocalTime time = LocalTime.now();
        //     timeLabel.setText(time.getHour() + ":" + time.getMinute() + ":" + time.getSecond());
        // }));
        // timeline.setCycleCount(Animation.INDEFINITE);
        // timeline.play();
        // timeLabel.getStyleClass().add("time-label");
        // Label dateLabel = new Label(LocalDate.now().toString());
        // dateLabel.getStyleClass().add("date-label");
        // clockDateVBox.getChildren().addAll(timeLabel, dateLabel);
        // VBox weatherVBox = new VBox();
        // weatherVBox.getChildren().add(WeatherPane.newInstance(weatherVBox));
        // clockWeatherHBox.getChildren().addAll(clockDateVBox, weatherVBox);
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
        // weatherVBox.prefWidthProperty().bind(clockWeatherHBox.widthProperty().multiply(0.66));
        // clockDateVBox.prefHeightProperty().bind(clockWeatherHBox.heightProperty());
        // weatherVBox.prefHeightProperty().bind(clockWeatherHBox.heightProperty());
        // setStageChangeListeners(primaryStage);
        // primaryStage.show();
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
