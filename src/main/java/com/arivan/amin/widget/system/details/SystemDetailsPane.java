package com.arivan.amin.widget.system.details;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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
        VBox mainVBox = new VBox(10);
        mainVBox.setAlignment(Pos.CENTER);
        mainVBox.prefWidthProperty().bind(container.widthProperty());
        mainVBox.prefHeightProperty().bind(container.heightProperty());
        Label systemNameLabel = new Label(systemDetails.systemName());
        systemNameLabel.getStyleClass().add("system-detail-label");
        Label osLabel = new Label(systemDetails.operatingSystemName() + "  " +
                systemDetails.operatingSystemVersion());
        osLabel.getStyleClass().add("system-detail-label");
        Label archLabel = new Label(systemDetails.architecture());
        archLabel.getStyleClass().add("system-detail-label");
        Label homeLabel = new Label(systemDetails.systemHomeUrl());
        homeLabel.getStyleClass().add("system-detail-label");
        mainVBox.getChildren().addAll(systemNameLabel, archLabel, osLabel, homeLabel);
        getChildren().add(mainVBox);
    }
    
    @NotNull
    public static SystemDetailsPane newInstance (Pane container)
    {
        return new SystemDetailsPane(container);
    }
}
