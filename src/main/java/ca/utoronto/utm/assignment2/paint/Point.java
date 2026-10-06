package ca.utoronto.utm.assignment2.paint;

/**
 * A simple class to represent a 2D point with x and y coordinates.
 * @author Harris Khan
 */
public class Point {
    /** The x-coordinate of the point. */
    double x;
    /** The y-coordinate of the point. */
    double y; // Available to our package

    /**
     * Constructs a new Point with the specified coordinates.
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     */
    Point(double x, double y){
        this.x=x; this.y=y;
    }
}