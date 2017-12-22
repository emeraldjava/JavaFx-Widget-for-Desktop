package com.arivan.amin.widget;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ApplicationMain extends Application
{
    public static void main (String[] args)
    {
        launch(args);
    }
    
    @Override
    public void start (Stage primaryStage) throws Exception
    {
        primaryStage.initStyle(StageStyle.UTILITY);
        PropertiesManager properties = PropertiesManager.newInstance();
        HBox mainHBox = new HBox();
        Scene scene = new Scene(mainHBox, properties.getWidth(), properties.getHeight());
        VBox leftVBox = new VBox();
        VBox topVBox = new VBox(new Label("top vbox"));
        VBox bottomVBox = new VBox(new Label("bottom vbox"));
        leftVBox.getChildren().addAll(topVBox, bottomVBox);
        VBox rightVBox = new VBox(new Label("right vbox"));
        mainHBox.getChildren().addAll(leftVBox, rightVBox);
        // Clock clock = Clock.newInstance(mainVBox);
        // mainVBox.getChildren().add(clock);
        primaryStage.setScene(scene);
        primaryStage.setX(properties.getX());
        primaryStage.setY(properties.getY());
        mainHBox.prefWidthProperty().bind(scene.widthProperty());
        mainHBox.prefHeightProperty().bind(scene.heightProperty());
        leftVBox.prefWidthProperty().bind(mainHBox.widthProperty());
        rightVBox.prefWidthProperty().bind(mainHBox.widthProperty());
        leftVBox.prefHeightProperty().bind(mainHBox.heightProperty());
        rightVBox.prefHeightProperty().bind(mainHBox.heightProperty());
        scene.widthProperty().addListener(e ->
        {
            storePropertyValues(primaryStage, properties, scene);
        });
        scene.heightProperty().addListener(e ->
        {
            storePropertyValues(primaryStage, properties, scene);
        });
        primaryStage.xProperty().addListener(e ->
        {
            storePropertyValues(primaryStage, properties, scene);
        });
        primaryStage.yProperty().addListener(e ->
        {
            storePropertyValues(primaryStage, properties, scene);
        });
        primaryStage.show();
    }
    
    private static void storePropertyValues (Stage primaryStage, PropertiesManager properties,
            Scene scene)
    {
        properties.setWidth((int) scene.widthProperty().get());
        properties.setHeight((int) scene.heightProperty().get());
        properties.setX((int) primaryStage.xProperty().get());
        properties.setY((int) primaryStage.yProperty().get());
        properties.storeValues();
    }
}
