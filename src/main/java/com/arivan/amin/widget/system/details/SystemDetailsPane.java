package com.arivan.amin.widget.system.details;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public class SystemDetailsPane extends Pane
{
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final SystemDetails systemDetails;
    
    private SystemDetailsPane (@NotNull Pane container)
    {
        super();
        systemDetails = LinuxSystemDetails.newInstance();
        HBox mainHBox = new HBox(10);
        VBox iconVBox = new VBox();
        iconVBox.setAlignment(Pos.CENTER);
        ImageView icon = new ImageView(new Image("linux2.png"));
        icon.setPreserveRatio(true);
        iconVBox.getChildren().add(icon);
        VBox detailsVBox = new VBox();
        detailsVBox.setPadding(new Insets(10));
        detailsVBox.setAlignment(Pos.CENTER);
        mainHBox.prefWidthProperty().bind(container.widthProperty());
        mainHBox.prefHeightProperty().bind(container.heightProperty());
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
        iconVBox.prefWidthProperty().bind(mainHBox.widthProperty().multiply(0.25));
        detailsVBox.prefWidthProperty().bind(mainHBox.widthProperty().multiply(0.75));
        iconVBox.prefHeightProperty().bind(mainHBox.heightProperty());
        detailsVBox.prefHeightProperty().bind(mainHBox.heightProperty());
        icon.fitWidthProperty().bind(iconVBox.widthProperty().multiply(0.95));
        icon.fitHeightProperty().bind(iconVBox.heightProperty().multiply(0.95));
        getChildren().add(mainHBox);
    }
    
    @NotNull
    public static SystemDetailsPane newInstance (Pane container)
    {
        return new SystemDetailsPane(container);
    }
}
