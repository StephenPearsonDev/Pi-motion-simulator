package dev.stephenpearson.model;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import dev.stephenpearson.utils.color.ColorUtils;


public class ColoredSegment {
    private final Color color;
    private final List<Point2D> points;
    
    public ColoredSegment() {
        this(ColorUtils.randomFluorescentColor());
    }
    
    public ColoredSegment(Color color) {
        this.color = color;
        this.points = new ArrayList<>();
    }
    
    public Color getColor() {
        return color;
    }
    
    public List<Point2D> getPoints() {
        return points;
    }
    
    public void addPoint(Point2D point) {
        points.add(point);
    }
}