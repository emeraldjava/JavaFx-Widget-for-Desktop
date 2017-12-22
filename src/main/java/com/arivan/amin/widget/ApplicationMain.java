package com.arivan.amin.widget;

import com.arivan.amin.widget.clock.Clock;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ApplicationMain extends Application
{
    public static void main (String[] args)
    {
        launch(args);
    }
    @Override
    public void start (Stage primaryStage) throws Exception
    {
        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane, 300, 300);
        Clock clock = Clock.newInstance(borderPane);
        borderPane.setCenter(clock);
        primaryStage.setScene(scene);
        borderPane.prefWidthProperty().bind(scene.widthProperty());
        borderPane.prefHeightProperty().bind(scene.heightProperty());
        primaryStage.show();
    }
}
