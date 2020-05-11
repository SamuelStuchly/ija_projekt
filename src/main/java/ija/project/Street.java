package ija.project;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import jdk.nashorn.internal.ir.ReturnNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "streetName", scope = Street.class)
public class Street implements Drawable {
    private String streetName;
    private Coordinate begin;
    private Coordinate end;
    private List<Stop> stops = new ArrayList<>();
    //private List<Coordinate> coordinates = new ArrayList<>();

    private int speed;

    private Street () {
    }

    public Street (String name, List<Stop> stops, Coordinate begin, Coordinate end) {
        this.streetName = name;
        this.begin = begin;
        this.end = end;
        this.stops.addAll(stops);
        //this.coordinates.addAll(Arrays.asList(coordinates));
    }

    public String getStreetName() {
        return streetName;
    }

    public List<Stop> getStops () {
        return Collections.unmodifiableList(stops);
    }

    // public List<Coordinate> getCoordinates() {
    //     return Collections.unmodifiableList(coordinates);
    // }

    public Coordinate begin() {
        return begin;
    }

    public Coordinate end() { 
        return end;
    }

    public int getStreetSpeed() { 
        return speed;
    }

    public boolean isCoordOnStreet(Coordinate coord){
        // y = a*x + b
        double a = (begin.getY() -  end.getY()) / (begin.getX() - end.getX());
        double b = 0 - (a * begin.getX()) - begin.getY()  ;  

        double minX = Math.min(begin.getX(), end.getX());
        double minY = Math.min(begin.getY(), end.getY());
        double maxX = Math.max(begin.getX(), end.getX());
        double maxY = Math.max(begin.getY(), end.getY());

        // coord is on the street which is a line 
        if (coord.getY() == a * coord.getX() + b ){
            if ( coord.getY() > minY && coord.getY() < maxY && coord.getX() > minX  && coord.getX()  < maxX){
                return true;
            } 
        }
        return false;
    }

   

    @JsonIgnore
    @Override
    public List<Shape> getGUI() {
        return Arrays.asList(
                new Text(this.begin().getX() + Math.abs(this.begin().diffX(this.end())) / 2,
                         this.begin().getY() + Math.abs(this.begin().diffY(this.end())) / 2, streetName),
                new Line(this.begin().getX(), this.begin().getY(), this.end().getX(), this.end().getY())
        );
    }

    @Override
    public String toString() {
        return "Street{" +
                "streetName='" + streetName + '\'' +
                ", stops=" + stops +
                '}';
    }
}