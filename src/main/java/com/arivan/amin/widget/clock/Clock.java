package com.arivan.amin.widget.clock;

import javafx.animation.*;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.time.LocalTime;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class Clock extends Pane
{
    private final Logger logger = Logger.getLogger(getClass().getName());
    private static final double PADDING_RATE = 0.95;
    private static final double SECOND_LENGTH_RATE = 0.80;
    private static final double MINUTE_LENGTH_RATE = 0.75;
    private static final double HOUR_LENGTH_RATE = 0.60;
    private static final double TICK_LENGTH = 0.96;
    private static final int MINOR_TICKS_RANGE = 5;
    private static final int NUMBER_OF_HOURS = 12;
    private static final double NUMBER_OF_MINUTES = 60.0;
    private int hour;
    private int minute;
    private int second;
    private double radius;
    private final DoubleProperty centerX;
    private final DoubleProperty centerY;
    private final DoubleProperty clockWidth;
    private final DoubleProperty clockHeight;
    private final Line secondLine;
    private final Line minuteLine;
    private final Line hourLine;
    private double secondLength;
    private double minuteLength;
    private double hourLength;
    private Pane container;
    
    private Clock (@NotNull Pane pane)
    {
        super();
        container = pane;
        clockWidth = new SimpleDoubleProperty(container.widthProperty().get());
        clockHeight = new SimpleDoubleProperty(container.heightProperty().get());
        clockWidth.bind(container.widthProperty());
        clockHeight.bind(container.heightProperty());
        centerX = new SimpleDoubleProperty();
        centerY = new SimpleDoubleProperty();
        centerXProperty().bind(clockWidth.divide(2));
        centerYProperty().bind(clockHeight.divide(2));
        attachLoggerHandler();
        secondLine = new Line();
        minuteLine = new Line();
        hourLine = new Line();
        secondLine.getStyleClass().add("second-pointer");
        minuteLine.getStyleClass().add("minute-pointer");
        hourLine.getStyleClass().add("hour-pointer");
        secondLine.setStrokeWidth(3);
        minuteLine.setStrokeWidth(3);
        hourLine.setStrokeWidth(7);
        getStylesheets().add(getClass().getResource("/MainStyle.css").toString());
        getStyleClass().add("pane");
        setChangeListeners();
        drawClockAndTicks();
        setTime();
    }
    
    @NotNull
    public static Clock newInstance (Pane pane)
    {
        return new Clock(pane);
    }
    
    private void setChangeListeners ()
    {
        clockWidth.addListener(e ->
        {
            drawClockAndTicks();
        });
        clockHeight.addListener(e ->
        {
            drawClockAndTicks();
        });
    }
    
    private void attachLoggerHandler ()
    {
        try
        {
            logger.addHandler(new FileHandler(logger.getName()));
        }
        catch (IOException e)
        {
            logger.warning(e.getMessage());
        }
    }
    
    private void setTime ()
    {
        LocalTime time = LocalTime.now();
        hour = time.getHour();
        minute = time.getMinute();
        second = time.getSecond();
        reDrawClock();
    }
    
    private void drawClockAndTicks ()
    {
        initializeLengths();
        createBaseCircle();
        createMajorTicks();
        createMinorTicks();
        Circle hourBaseCircle = new Circle(getCenterX(), getCenterY(), getHourBaseRadius());
        hourBaseCircle.getStyleClass().add("hour-base");
        Circle minuteBaseCircle = new Circle(getCenterX(), getCenterY(), getMinuteBaseRadius());
        minuteBaseCircle.getStyleClass().add("minute-base");
        setDeveloperName();
        getChildren().addAll(hourBaseCircle, hourLine);
        getChildren().addAll(minuteLine, minuteBaseCircle);
        getChildren().addAll(secondLine);
        setStartCenterPoints();
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e ->
        {
            setTime();
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    
    private void createBaseCircle ()
    {
        Circle baseCircle = new Circle(getCenterX(), getCenterY(), radius);
        baseCircle.getStyleClass().add("circle");
        getChildren().add(baseCircle);
    }
    
    @Contract (pure = true)
    private double getMinuteBaseRadius ()
    {
        return radius * 0.045;
    }
    
    @Contract (pure = true)
    private double getHourBaseRadius ()
    {
        return radius * 0.08;
    }
    
    private void setDeveloperName ()
    {
        Text nameText = new Text(getCenterX() - (radius * 0.25), getCenterY() + (radius * 0.5),
                "Arivan Amin");
        nameText.getStyleClass().add("dev-name");
        getChildren().add(nameText);
    }
    
    private void setStartCenterPoints ()
    {
        secondLine.setStartX(getCenterX());
        secondLine.setStartY(getCenterY());
        minuteLine.setStartX(getCenterX());
        minuteLine.setStartY(getCenterY());
        hourLine.setStartX(getCenterX());
        hourLine.setStartY(getCenterY());
    }
    
    private void createMinorTicks ()
    {
        IntStream.rangeClosed(1, (int) NUMBER_OF_MINUTES).forEach(e ->
        {
            Line line = new Line(getTickFirstX(e), getTickFirstY(e), getTickSecondX(e),
                    getTickSecondY(e));
            line.getStyleClass().add("tick");
            if (isMinorTick(e))
            {
                line.setStrokeWidth(3);
            }
            getChildren().add(line);
        });
    }
    
    private void createMajorTicks ()
    {
        Text text12 = new Text(getCenterX() - 15, (getCenterY() - radius) + 35, "12");
        text12.getStyleClass().addAll("tick-number");
        Text text9 = new Text((getCenterX() - radius) + 13, getCenterY() + 10, "9");
        text9.getStyleClass().addAll("tick-number");
        Text text3 = new Text((getCenterX() + radius) - 30, getCenterY() + 10, "3");
        text3.getStyleClass().addAll("tick-number");
        Text text6 = new Text(getCenterX() - 10, (getCenterY() + radius) - 20, "6");
        text6.getStyleClass().addAll("tick-number");
        getChildren().addAll(text12, text3, text6, text9);
    }
    
    private void initializeLengths ()
    {
        getChildren().clear();
        radius = computeRadius();
        secondLength = radius * SECOND_LENGTH_RATE;
        minuteLength = radius * MINUTE_LENGTH_RATE;
        hourLength = radius * HOUR_LENGTH_RATE;
    }
    
    private double computeRadius ()
    {
        return Math.min(clockWidthProperty().get(), clockHeightProperty().get()) * PADDING_RATE *
                0.5;
    }
    
    @Contract (pure = true)
    private static boolean isMinorTick (int e)
    {
        return (e % MINOR_TICKS_RANGE) == 0;
    }
    
    private double getTickFirstX (int tick)
    {
        return getCenterX() + (radius * TICK_LENGTH * getTickSin(tick));
    }
    
    private double getTickFirstY (int tick)
    {
        return getCenterY() - (radius * TICK_LENGTH * getTickCos(tick));
    }
    
    private double getTickSecondX (int tick)
    {
        return getCenterX() + (radius * getTickSin(tick));
    }
    
    private double getTickSecondY (int tick)
    {
        return getCenterY() - (radius * getTickCos(tick));
    }
    
    private static double getTickSin (int tick)
    {
        return Math.sin(getTickAngle(tick));
    }
    
    private static double getTickCos (int tick)
    {
        return Math.cos(getTickAngle(tick));
    }
    
    @Contract (pure = true)
    private static double getTickAngle (int tick)
    {
        return tick * ((2 * Math.PI) / NUMBER_OF_MINUTES);
    }
    
    private void reDrawClock ()
    {
        secondLine.setEndX(getSecondX());
        secondLine.setEndY(getSecondY());
        minuteLine.setEndX(getMinuteX());
        minuteLine.setEndY(getMinuteY());
        hourLine.setEndX(getHourX());
        hourLine.setEndY(getHourY());
    }
    
    private double getSecondX ()
    {
        return getCenterX() + (secondLength * getTickSin(second));
    }
    
    private double getSecondY ()
    {
        return getCenterY() - (secondLength * getTickCos(second));
    }
    
    private double getMinuteX ()
    {
        return getCenterX() + (minuteLength * getTickSin(minute));
    }
    
    private double getMinuteY ()
    {
        return getCenterY() - (minuteLength * getTickCos(minute));
    }
    
    private double getHourX ()
    {
        return getCenterX() + (hourLength * Math.sin(computeHourAngle()));
    }
    
    private double getHourY ()
    {
        return getCenterY() - (hourLength * Math.cos(computeHourAngle()));
    }
    
    @Contract (pure = true)
    private double computeHourAngle ()
    {
        return (convertTo12HourFormat() + (minute / NUMBER_OF_MINUTES)) *
                ((2 * Math.PI) / NUMBER_OF_HOURS);
    }
    
    @Contract (pure = true)
    private int convertTo12HourFormat ()
    {
        return hour % NUMBER_OF_HOURS;
    }
    
    public double getClockWidth ()
    {
        return clockWidth.get();
    }
    
    public void setClockWidth (double value)
    {
        clockWidth.setValue(value);
        drawClockAndTicks();
    }
    
    public DoubleProperty clockWidthProperty ()
    {
        return clockWidth;
    }
    
    public double getClockHeight ()
    {
        return clockHeight.get();
    }
    
    public void setClockHeight (double value)
    {
        clockHeight.setValue(value);
        drawClockAndTicks();
    }
    
    @Contract (pure = true)
    private DoubleProperty clockHeightProperty ()
    {
        return clockHeight;
    }
    
    private double getCenterX ()
    {
        return centerX.get();
    }
    
    public void setCenterX (double value)
    {
        centerX.setValue(value);
        drawClockAndTicks();
    }
    
    @Contract (pure = true)
    private DoubleProperty centerXProperty ()
    {
        return centerX;
    }
    
    private double getCenterY ()
    {
        return centerY.get();
    }
    
    public void setCenterY (double value)
    {
        centerY.setValue(value);
        drawClockAndTicks();
    }
    
    private DoubleProperty centerYProperty ()
    {
        return centerY;
    }
    
    public int getHour ()
    {
        return hour;
    }
    
    public void setHour (int hour)
    {
        this.hour = hour;
        reDrawClock();
    }
    
    public int getMinute ()
    {
        return minute;
    }
    
    public void setMinute (int minute)
    {
        this.minute = minute;
        reDrawClock();
    }
    
    public int getSecond ()
    {
        return second;
    }
    
    public void setSecond (int second)
    {
        this.second = second;
        reDrawClock();
    }
    
    @Override
    public String toString ()
    {
        return "Clock{" + "hour=" + hour + ", minute=" + minute + ", second=" + second +
                ", radius=" + radius + ", centerX=" + centerX + ", centerY=" + centerY +
                ", clockWidth=" + clockWidth + ", clockHeight=" + clockHeight + ", secondLine=" +
                secondLine + ", minuteLine=" + minuteLine + ", hourLine=" + hourLine +
                ", secondLength=" + secondLength + ", minuteLength=" + minuteLength +
                ", hourLength=" + hourLength + '}';
    }
}
