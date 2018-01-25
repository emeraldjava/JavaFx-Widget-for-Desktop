package com.arivan.amin.widget.network;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.logging.Logger;

public class NetworkBox extends VBox
{
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final Label downloadLabel;
    private final Label uploadLabel;
    
    private NetworkBox ()
    {
        
        downloadLabel = new Label("download");
        uploadLabel = new Label("upload");
    }
    
    public static NetworkBox newInstance ()
    {
        return new NetworkBox();
    }
}
