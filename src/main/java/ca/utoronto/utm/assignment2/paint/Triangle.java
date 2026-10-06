package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Represents a drawable Triangle shape, defined by three corner points.
 */
public class Triangle implements Shape{
    private Point cornerOpp;
    private Point cornerMiddle;
    private Point cornerDragged;
    private final boolean setFill;
    private int lineWidth;
    private Color color;
    private double rotation;

    /**
     * Constructs a new Triangle with the specified properties.
     * @param cornerOpp The first corner point.
     * @param cornerMiddle The second corner point, used as the center for rotation.
     * @param cornerDragged The third corner point.
     * @param setFill True for a filled triangle, false for an outline.
     * @param color The color of the triangle.
     */
    public Triangle(Point cornerOpp, Point cornerMiddle, Point cornerDragged, boolean setFill, Color color) {
        this.cornerOpp = cornerOpp;
        this.cornerMiddle = cornerMiddle;
        this.cornerDragged = cornerDragged;
        this.setFill = setFill;
        this.lineWidth = 1;
        this.color = color;
        this.rotation = 0;
    }
    public Point getOppositeCorner() {
        return this.cornerOpp;
    }

    public void setOppositeCorner(Point oppositeCorner) {
        this.cornerOpp = oppositeCorner;
    }

    public Point getMiddleCorner() {
        return this.cornerMiddle;
    }

    public void setMiddleCorner(Point middleCorner) {
        this.cornerMiddle = middleCorner;
    }

    public Point getCornerDragged() {
        return this.cornerDragged;
    }

    public void setCornerDragged(Point draggedCorner){
        this.cornerDragged = draggedCorner;
    }

    /**
     * @return True if the triangle should be filled, false if outlined.
     */
    public boolean getFill(){
        return setFill;
    }

    /**
     * Sets the line thickness for the triangle's outline.
     * @param newLineWidth The new width in pixels.
     */
    public void setLineWidth(int newLineWidth){
        this.lineWidth = newLineWidth;
    }

    public int getLineWidth(){
        return lineWidth;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    @Override
    public double getRotation() {
        return rotation;
    }

    /**
     * Draws the triangle onto the specified graphics context, applying rotation.
     * @param g2d The graphics context to draw on.
     */
    @Override
    public void draw(GraphicsContext g2d){
        g2d.setFill(this.color);
        // Collect coordinates into arrays for polygon drawing
        double[] xVal = {this.getOppositeCorner().x, this.getMiddleCorner().x, this.getCornerDragged().x};
        double[] yVal = {this.getOppositeCorner().y, this.getMiddleCorner().y, this.getCornerDragged().y};

        // Use the middle corner as the pivot point for rotation
        double cx = this.getMiddleCorner().x;
        double cy = this.getMiddleCorner().y;

        g2d.save();
        g2d.translate(cx, cy);
        g2d.rotate(rotation);
        g2d.translate(-cx, -cy);

        if (this.getFill()){
            g2d.fillPolygon(xVal, yVal, 3);
        } else {
            g2d.setStroke(this.color);
            // setting the width according to thickness selected
            g2d.setLineWidth(this.getLineWidth());
            g2d.strokePolygon(xVal, yVal, 3);
        }

        g2d.restore();
    }
}