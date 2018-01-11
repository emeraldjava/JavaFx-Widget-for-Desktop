package com.arivan.amin.widget;

import com.arivan.amin.widget.ui.boxes.LeftBox;
import com.arivan.amin.widget.ui.boxes.RightBox;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.jetbrains.annotations.NotNull;

public class ApplicationMain extends Application
{
    private PropertiesManager properties;
    private Scene scene;
    
    public static void main (String[] args)
    {
        launch(args);
    }
    
    @Override
    public void start (Stage primaryStage)
    {
        properties = PropertiesManager.newInstance();
        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.setTitle("JavaFx Widget");
        primaryStage.maximizedProperty().addListener(e ->
        {
            properties.setStageMaximized(primaryStage.isMaximized());
            properties.storeValues();
        });
        primaryStage.setMaximized(properties.getIsStageMaximized());
        HBox mainHBox = new HBox();
        mainHBox.getStyleClass().add("pane");
        mainHBox.setPadding(new Insets(10));
        scene = new Scene(mainHBox, properties.getWidth(), properties.getHeight());
        scene.getStylesheets().add("/MainStyle.css");
        mainHBox.getChildren().add(LeftBox.newInstance(mainHBox));
        mainHBox.getChildren().add(RightBox.newInstance(mainHBox));
        primaryStage.setScene(scene);
        primaryStage.setX(properties.getStageX());
        primaryStage.setY(properties.getStageY());
        mainHBox.prefWidthProperty().bind(scene.widthProperty());
        mainHBox.prefHeightProperty().bind(scene.heightProperty());
        setStageChangeListeners(primaryStage);
        primaryStage.getIcons().add(new Image("widget icon.png"));
        primaryStage.show();
    }
    
    private void setStageChangeListeners (@NotNull Stage primaryStage)
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
    
    private void storePropertyValues (@NotNull Stage primaryStage)
    {
        properties.setWidth((int) scene.widthProperty().get());
        properties.setHeight((int) scene.heightProperty().get());
        properties.setStageX((int) primaryStage.xProperty().get());
        properties.setStageY((int) primaryStage.yProperty().get());
        properties.storeValues();
    }
}
