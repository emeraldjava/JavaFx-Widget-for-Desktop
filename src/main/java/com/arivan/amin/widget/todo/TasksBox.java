package com.arivan.amin.widget.todo;

import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Collections;
import java.util.logging.Logger;

public class TasksBox extends VBox
{
    private static final String TODO_FILE = "todo.txt";
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final TextField textField;
    
    private TasksBox ()
    {
        super();
        setPadding(new Insets(10, 0, 0, 0));
        setSpacing(10);
        textField = new TextField();
        textField.setOnAction(e ->
        {
            try
            {
                Path path = Paths.get(TODO_FILE);
                Files.write(path, textField.getText().trim().getBytes(StandardCharsets.UTF_8),
                        StandardOpenOption.APPEND);
                textField.setText("");
                createItems();
            }
            catch (IOException ex)
            {
                logger.warning(ex.getMessage());
            }
        });
        createItems();
    }
    
    @NotNull
    public static TasksBox newInstance ()
    {
        return new TasksBox();
    }
    
    private Iterable<String> getItemsFromFile ()
    {
        try
        {
            return Files.readAllLines(Paths.get(TODO_FILE));
        }
        catch (IOException e)
        {
            logger.warning(e.getMessage());
            return Collections.emptyList();
        }
    }
    
    private void createItems ()
    {
        Iterable<String> list = getItemsFromFile();
        getChildren().clear();
        getChildren().add(textField);
        list.forEach(x ->
        {
            CheckBox checkBox = new CheckBox(x);
            checkBox.setOnAction(e ->
            {
                if (checkBox.isSelected())
                {
                    JOptionPane.showMessageDialog(null, "clicked " + x);
                }
            });
            getChildren().add(checkBox);
        });
    }
}
