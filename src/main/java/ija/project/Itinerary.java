package ija.project;

import com.fasterxml.jackson.databind.util.StdConverter;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Itinerary implements Drawable, TimeUpdate {

    private Vehicle vehicle;

    private Coordinate startingPosition;
    private Coordinate position;

    private double pathDistance;

    private Shape lineGui;
    private Shape vehicleGui;
    private List<Shape> stopsGui;
    private List<Shape> stopTextsGui;

    private List<Shape> gui;

    public Itinerary (Vehicle vehicle) {

        this.vehicle = vehicle;
        this.startingPosition = Coordinate.create(50,30);
        this.position = startingPosition;

        this.pathDistance = vehicle.getPath().getPathLength();
        setGui();
    }

    @Override
    public List<Shape> getGUI() {
        return gui;
    }

    public void setGui() {
        this.gui = new ArrayList<Shape>();
        setStopsGui();
        this.lineGui = new Line(50,30,850,30);
        this.vehicleGui = new Circle(position.getX(),position.getY(), 6, vehicle.getLine().getLineColor());

        this.gui.add(vehicleGui);
        this.gui.add(lineGui);
        this.gui.addAll(stopsGui);
        this.gui.addAll(stopTextsGui);
    }

    private void moveGui (double value) {

        this.gui.get(0).setTranslateX(value);
    }

    private Double getTranslateValue (double distance) {

        return (distance/pathDistance)*800;
    }

    public void setStopsGui () {

        this.stopsGui = new ArrayList<Shape>();
        this.stopTextsGui = new ArrayList<Shape>();
        String stopId;
        LocalTime stopTime;
        String stopTimeString;
        double stopDistance;
        double translateX;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.US);
        Text t;

        for (int i = 0; i < vehicle.getStopDistances().size(); i++) {

            stopId = vehicle.getStoplist().get(i).getStopId();
            stopTime = vehicle.getTimeslist().get(i);
            stopTimeString = formatter.format(stopTime);

            stopDistance = vehicle.getStopDistances().get(i);
            translateX = getTranslateValue(stopDistance);

            t = new Text(50 + translateX - 20,50,stopId);
            t.setFont(Font.font("Verdana", 10));
            stopTextsGui.add(t);

            t = new Text(50 + translateX - 30,70,stopTimeString);
            t.setFont(Font.font("Verdana", 10));
            stopTextsGui.add(t);

            stopsGui.add(new Circle(50 + translateX,30,8, Color.RED));
        }
    }

    @Override
    public void update (LocalTime time) {

        moveGui(getTranslateValue(vehicle.getDistance()));

    }

    public void newTime (LocalTime time) {
        return;
    }

}
