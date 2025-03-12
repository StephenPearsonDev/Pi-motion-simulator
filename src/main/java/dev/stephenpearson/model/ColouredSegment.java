package dev.stephenpearson.model;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;


//Chunk the line to make it easier for rendering the coloured parts
public class ColouredSegment {
    public Color color;
    public List<Point2D> points;
    
    public ColouredSegment(Color color) {
        this.color = color;
        this.points = new ArrayList<>();
    }
}
