package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Represents a drawable Circle shape with a center, radius, color, and fill style.
 */
public class Circle implements Shape{
    private Point centre;
    private double radius;
    private final boolean setFill;
    private int lineWidth;
    private Color color;

    /**
     * Constructs a new Circle with the specified properties.
     * @param centre The center point of the circle.
     * @param radius The radius of the circle.
     * @param setFill True for a filled circle, false for an outline.
     * @param color The color of the circle.
     */
    public Circle(Point centre, int radius, boolean setFill, Color color){
        this.centre = centre;
        this.radius = radius;
        this.setFill = setFill;
        this.lineWidth = 1;
        this.color = color;
    }

    public Point getCentre() {
        return centre;
    }

    public void setCentre(Point centre) {
        this.centre = centre;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    /**
     * @return True if the circle should be filled, false if outlined.
     */
    public boolean getFill(){
        return setFill;
    }

    /**
     * Sets the line thickness for the circle's outline.
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

    /**
     * Not implemented for Circle.
     */
    @Override
    public void setRotation(double rotation) {

    }

    /**
     * Not implemented for Circle.
     * @return Always 0.
     */
    @Override
    public double getRotation() {
        return 0;
    }

    /**
     * Draws the circle onto the specified graphics context.
     * @param g2d The graphics context to draw on.
     */
    @Override
    public void draw(GraphicsContext g2d){

        g2d.setFill(this.color);
        double x = this.getCentre().x;
        double y = this.getCentre().y;
        double radius = this.getRadius();
        // check for fill status
        if (this.getFill()){
            g2d.fillOval(x - radius, y - radius, radius * 2, radius * 2);
        } else{
            g2d.setStroke(this.color);
            // setting the width according to thickness selected
            g2d.setLineWidth(this.getLineWidth());
            g2d.strokeOval(x - radius, y - radius, radius * 2, radius * 2);
        }

    }

}