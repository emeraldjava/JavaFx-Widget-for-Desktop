package com.arivan.amin.widget.system_details;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.logging.Logger;

public class SystemDetailsBox extends VBox
{
    private static final double SYSTEM_DETAILS_BOX_SIZE = 0.66;
    private final Logger logger = Logger.getLogger(getClass().getName());
    private SystemDetails systemDetails;
    
    @SuppressWarnings ({ "TypeMayBeWeakened", "OverlyLongMethod" })
    private SystemDetailsBox (DoubleProperty parentWidth, DoubleProperty parentHeight)
    {
        determineOperatingSystem();
        VBox iconVBox = new VBox();
        iconVBox.setAlignment(Pos.CENTER);
        ImageView icon = new ImageView(new Image("linux.png"));
        icon.setPreserveRatio(true);
        iconVBox.getChildren().add(icon);
        VBox detailsVBox = new VBox();
        detailsVBox.setPadding(new Insets(10));
        detailsVBox.setAlignment(Pos.CENTER);
        bindBoxToParent(prefWidthProperty(), parentWidth, prefHeightProperty(), parentHeight,
                SYSTEM_DETAILS_BOX_SIZE);
        Label systemNameLabel = new Label(systemDetails.systemName());
        Label osLabel = new Label(systemDetails.operatingSystemName() + "  " +
                systemDetails.operatingSystemVersion());
        Label archLabel = new Label(systemDetails.architecture());
        Label homeLabel = new Label(systemDetails.systemHomeUrl());
        addStyleClasses(systemNameLabel, osLabel, archLabel, homeLabel);
        detailsVBox.getChildren().addAll(systemNameLabel, archLabel, osLabel, homeLabel);
        getChildren().addAll(iconVBox, detailsVBox);
        bindBoxesToParents(iconVBox, icon, detailsVBox);
    }
    
    @SuppressWarnings ({ "TypeMayBeWeakened", "MethodWithTooManyParameters" })
    private void bindBoxToParent (DoubleProperty detailsVBoxWidth,
            ReadOnlyDoubleProperty boxWidth, DoubleProperty iconVBoxHeight,
            ReadOnlyDoubleProperty boxHeight, double systemDetailsBoxSize)
    {
        detailsVBoxWidth.bind(boxWidth);
        iconVBoxHeight.bind(boxHeight.multiply(systemDetailsBoxSize));
    }
    
    @SuppressWarnings ("MagicNumber")
    private void bindBoxesToParents (VBox iconVBox, ImageView icon, VBox detailsVBox)
    {
        iconVBox.prefWidthProperty().bind(widthProperty());
        bindBoxToParent(detailsVBox.prefWidthProperty(), widthProperty(),
                iconVBox.prefHeightProperty(), heightProperty(), 0.5);
        detailsVBox.prefHeightProperty().bind(heightProperty().multiply(0.5));
        icon.fitWidthProperty().bind(iconVBox.widthProperty().multiply(0.99));
        icon.fitHeightProperty().bind(iconVBox.heightProperty().multiply(0.99));
    }
    
    @SuppressWarnings ({ "TypeMayBeWeakened", "MethodWithTooManyParameters" })
    private void addStyleClasses (Label systemNameLabel, Label osLabel, Label archLabel,
            Label homeLabel)
    {
        systemNameLabel.getStyleClass().add("system-detail-label");
        osLabel.getStyleClass().add("system-detail-label");
        archLabel.getStyleClass().add("system-detail-label");
        homeLabel.getStyleClass().add("system-detail-label");
    }
    
    private void determineOperatingSystem ()
    {
        systemDetails = LinuxSystemDetails.newInstance();
    }
    
    public static SystemDetailsBox newInstance (DoubleProperty parentWidth,
            DoubleProperty parentHeight)
    {
        return new SystemDetailsBox(parentWidth, parentHeight);
    }
    
    @Override
    public String toString ()
    {
        return "SystemDetailsBox{" + "systemDetails=" + systemDetails + '}';
    }
}
