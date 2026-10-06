package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Represents a free-form shape composed of a series of connected points (a polyline).
 */
public class Squiggle implements Shape{
    private final ArrayList<Point> points=new ArrayList<Point>();
    private final ArrayList<Integer> pointWidths = new ArrayList<Integer>();
    private int lineWidth = 1;
    private Color color;

    /**
     * Constructs a new Squiggle with an initial line width and color.
     * @param lineWidth The default line width for segments.
     * @param color The color of the squiggle.
     */
    public Squiggle(int lineWidth, Color color){
        this.lineWidth = lineWidth;
        this.color = color;
    }

    /**
     * Not implemented for Squiggle.
     */
    @Override
    public void setRotation(double rotation) {

    }

    /**
     * Not implemented for Squiggle.
     * @return Always 0.
     */
    @Override
    public double getRotation() {
        return 0;
    }

    /**
     * Draws the squiggle onto the specified graphics context by connecting its points.
     * @param g2d The graphics context to draw on.
     */
    @Override
    public void draw(GraphicsContext g2d) {
        g2d.setLineWidth(lineWidth);
        // Draw Lines
        ArrayList<Point> points = this.getPoints();
        ArrayList<Integer> pointWidths = this.getWidthOfPoints();
        g2d.setStroke(this.color);
        for(int i=0;i<points.size()-1; i++) {
            Point p1 = points.get(i);
            Point p2 = points.get(i + 1);
            // Skip segments where one point is null (used for polyline preview)
            if (p2 == null || p1 == null) {
                continue;
            }
            g2d.setLineWidth(pointWidths.get(i));
            g2d.strokeLine(p1.x, p1.y, p2.x, p2.y);
        }
    }

    /**
     * Adds a new point to the end of the squiggle.
     * @param p The point to add.
     */
    public void addPoint(Point p){
        this.points.add(p);
        this.pointWidths.add(lineWidth);
    }

    /**
     * Removes the last point from the squiggle.
     */
    public void pop(){
        this.points.removeLast();
    }

    public ArrayList<Point> getPoints(){
        return points;
    }

    public ArrayList<Integer> getWidthOfPoints(){
        return pointWidths;
    }


    /**
     * Sets the current line width to be stored with new points.
     * @param width The new line width.
     */
    public void setLineWidth(int width){
        lineWidth = width;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    /**
     * Clears all points from the squiggle.
     */
    public void clearSquiggle(){
        // clear squiggle points and point widths
        this.points.clear();
        this.pointWidths.clear();
    }
}