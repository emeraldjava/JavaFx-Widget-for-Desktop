package com.arivan.amin.widget;

import com.arivan.amin.widget.cpu.CpuProgressBar;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
        primaryStage.initStyle(StageStyle.UTILITY);
        properties = PropertiesManager.newInstance();
        HBox mainHBox = new HBox();
        mainHBox.setPadding(new Insets(10));
        scene = new Scene(mainHBox, properties.getWidth(), properties.getHeight());
        VBox leftVBox = new VBox();
        VBox topVBox = new VBox(new Label("top vbox"));
        VBox bottomVBox = new VBox(new Label("bottom vbox"));
        leftVBox.getChildren().addAll(topVBox, bottomVBox);
        VBox rightVBox = new VBox();
        rightVBox.getChildren().add(CpuProgressBar.newInstance(rightVBox));
        mainHBox.getChildren().addAll(leftVBox, rightVBox);
        // Clock clock = Clock.newInstance(mainVBox);
        // mainVBox.getChildren().add(clock);
        primaryStage.setScene(scene);
        primaryStage.setX(properties.getX());
        primaryStage.setY(properties.getY());
        mainHBox.prefWidthProperty().bind(scene.widthProperty());
        mainHBox.prefHeightProperty().bind(scene.heightProperty());
        leftVBox.prefWidthProperty().bind(mainHBox.widthProperty().multiply(0.75));
        rightVBox.prefWidthProperty().bind(mainHBox.widthProperty().multiply(0.23));
        leftVBox.prefHeightProperty().bind(mainHBox.heightProperty());
        rightVBox.prefHeightProperty().bind(mainHBox.heightProperty());
        topVBox.prefHeightProperty().bind(leftVBox.heightProperty().multiply(0.5));
        bottomVBox.prefHeightProperty().bind(leftVBox.heightProperty().multiply(0.5));
        setStageChangeListeners(primaryStage);
        primaryStage.show();
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
