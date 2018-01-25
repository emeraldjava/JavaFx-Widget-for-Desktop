package com.arivan.amin.widget;

import com.arivan.amin.widget.ui_boxes.LeftBox;
import com.arivan.amin.widget.ui_boxes.RightBox;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

@SuppressWarnings ({ "ClassHasNoToStringMethod", "PublicConstructor", "ClassWithoutLogger" })
public class ApplicationMain extends Application
{
    @SuppressWarnings ("InstanceVariableOfConcreteClass")
    private PropertiesManager properties;
    private Scene scene;
    
    public static void main (String[] args)
    {
        launch(args);
    }
    
    @SuppressWarnings ("PublicMethodNotExposedInInterface")
    @Override
    public void start (Stage primaryStage)
    {
        properties = PropertiesManager.newInstance();
        HBox mainHBox = createBoxAndSetProperties();
        createScene(mainHBox);
        bindBoxToSceneAndAddItems(mainHBox);
        setStageProperties(primaryStage);
    }
    
    private HBox createBoxAndSetProperties ()
    {
        HBox mainHBox = new HBox();
        mainHBox.getStyleClass().add("pane");
        mainHBox.setPadding(new Insets(10));
        return mainHBox;
    }
    
    private void createScene (HBox mainHBox)
    {
        scene = new Scene(mainHBox, properties.getWidth(), properties.getHeight());
        scene.getStylesheets().add("/MainStyle.css");
    }
    
    private void bindBoxToSceneAndAddItems (HBox mainHBox)
    {
        mainHBox.prefWidthProperty().bind(scene.widthProperty());
        mainHBox.prefHeightProperty().bind(scene.heightProperty());
        mainHBox.getChildren().add(LeftBox
                .newInstance(mainHBox.prefWidthProperty(), mainHBox.prefHeightProperty()));
        mainHBox.getChildren().add(RightBox
                .newInstance(mainHBox.prefWidthProperty(), mainHBox.prefHeightProperty()));
    }
    
    // todo application freezes when there is connection problems
    private void setStageProperties (Stage primaryStage)
    {
        setStageChangeListeners(primaryStage);
        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.setTitle("JavaFx Widget");
        addMaximizedListenerToStage(primaryStage);
        primaryStage.setMaximized(properties.getIsStageMaximized());
        primaryStage.setScene(scene);
        primaryStage.setX(properties.getStageX());
        primaryStage.setY(properties.getStageY());
        primaryStage.getIcons().add(new Image("widget icon.png"));
        primaryStage.show();
    }
    
    private void addMaximizedListenerToStage (Stage primaryStage)
    {
        primaryStage.maximizedProperty().addListener(e ->
        {
            properties.setIsStageMaximized(primaryStage.isMaximized());
            properties.storeValues();
        });
    }
    
    private void setStageChangeListeners (Stage primaryStage)
    {
        setSizeListeners(primaryStage);
        setPositionListeners(primaryStage);
    }
    
    private void setPositionListeners (Stage primaryStage)
    {
        primaryStage.xProperty().addListener(e -> storePropertyValues(primaryStage));
        primaryStage.yProperty().addListener(e -> storePropertyValues(primaryStage));
    }
    
    private void setSizeListeners (Stage primaryStage)
    {
        scene.widthProperty().addListener(e -> storePropertyValues(primaryStage));
        scene.heightProperty().addListener(e -> storePropertyValues(primaryStage));
    }
    
    private void storePropertyValues (Stage primaryStage)
    {
        properties.setWidth((int) scene.widthProperty().get());
        properties.setHeight((int) scene.heightProperty().get());
        properties.setStageX((int) primaryStage.xProperty().get());
        properties.setStageY((int) primaryStage.yProperty().get());
        properties.storeValues();
    }
}
