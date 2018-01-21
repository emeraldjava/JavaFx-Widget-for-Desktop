package com.arivan.amin.widget.system_details;

import javafx.beans.property.DoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.logging.Logger;

public class SystemDetailsPane extends Pane
{
    private final Logger logger = Logger.getLogger(getClass().getName());
    private SystemDetails systemDetails;
    
    private SystemDetailsPane (DoubleProperty parentWidth, DoubleProperty parentHeight)
    {
        determineOperatingSystem();
        VBox mainHBox = new VBox(10);
        VBox iconVBox = new VBox();
        iconVBox.setAlignment(Pos.CENTER);
        ImageView icon = new ImageView(new Image("linux.png"));
        icon.setPreserveRatio(true);
        iconVBox.getChildren().add(icon);
        VBox detailsVBox = new VBox();
        detailsVBox.setPadding(new Insets(10));
        detailsVBox.setAlignment(Pos.CENTER);
        mainHBox.prefWidthProperty().bind(parentWidth);
        mainHBox.prefHeightProperty().bind(parentHeight.multiply(0.66));
        Label systemNameLabel = new Label(systemDetails.systemName());
        systemNameLabel.getStyleClass().add("system-detail-label");
        Label osLabel = new Label(systemDetails.operatingSystemName() + "  " +
                systemDetails.operatingSystemVersion());
        osLabel.getStyleClass().add("system-detail-label");
        Label archLabel = new Label(systemDetails.architecture());
        archLabel.getStyleClass().add("system-detail-label");
        Label homeLabel = new Label(systemDetails.systemHomeUrl());
        homeLabel.getStyleClass().add("system-detail-label");
        detailsVBox.getChildren().addAll(systemNameLabel, archLabel, osLabel, homeLabel);
        mainHBox.getChildren().addAll(iconVBox, detailsVBox);
        iconVBox.prefWidthProperty().bind(mainHBox.widthProperty());
        detailsVBox.prefWidthProperty().bind(mainHBox.widthProperty());
        iconVBox.prefHeightProperty().bind(mainHBox.heightProperty().multiply(0.5));
        detailsVBox.prefHeightProperty().bind(mainHBox.heightProperty().multiply(0.5));
        icon.fitWidthProperty().bind(iconVBox.widthProperty().multiply(0.99));
        icon.fitHeightProperty().bind(iconVBox.heightProperty().multiply(0.99));
        getChildren().add(mainHBox);
    }
    
    private void determineOperatingSystem ()
    {
        systemDetails = LinuxSystemDetails.newInstance();
    }
    
    public static SystemDetailsPane newInstance (DoubleProperty parentWidth,
            DoubleProperty parentHeight)
    {
        return new SystemDetailsPane(parentWidth, parentHeight);
    }
}
