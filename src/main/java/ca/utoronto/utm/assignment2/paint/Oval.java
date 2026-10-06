package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Represents a drawable Oval shape, defined by a center, two radii, and rotation.
 */
public class Oval implements Shape{
    private Point centre;
    private double xRadius;
    private double yRadius;
    private boolean setFill;
    private int lineWidth;
    private Color color;
    private double rotation;

    /**
     * Constructs a new Oval with the specified properties.
     * @param centre The center point of the oval.
     * @param xRadius The horizontal radius.
     * @param yRadius The vertical radius.
     * @param setFill True for a filled oval, false for an outline.
     * @param color The color of the oval.
     */
    public Oval(Point centre, int xRadius, int yRadius, boolean setFill, Color color) {
        this.centre = centre;
        this.xRadius = xRadius;
        this.yRadius = yRadius;
        this.setFill = setFill;
        this.lineWidth = 1;
        this.color = color;
        rotation = 0;
    }

    public Point getCentre() {
        return centre;
    }

    public void setCentre(Point centre) {
        this.centre = centre;
    }

    public double getXRadius() {
        return xRadius;
    }

    public double getYRadius() {
        return yRadius;
    }

    public void setXRadius(double xRadius) {
        this.xRadius = xRadius;
    }

    public void setYRadius(double yRadius) {
        this.yRadius = yRadius;
    }

    /**
     * @return True if the oval should be filled, false if outlined.
     */
    public boolean getFill(){
        return setFill;
    }

    /**
     * Sets the line thickness for the oval's outline.
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
     * Draws the oval onto the specified graphics context, applying rotation.
     * @param g2d The graphics context to draw on.
     */
    @Override
    public void draw(GraphicsContext g2d){
        g2d.setFill(this.color);
        double x = this.getCentre().x;
        double y = this.getCentre().y;
        double xRadius = this.getXRadius();
        double yRadius = this.getYRadius();

        double cx = x;
        double cy = y;

        // Save the current graphics state
        g2d.save();
        // Apply rotation around the oval's center
        g2d.translate(cx, cy);
        g2d.rotate(this.rotation); // already in degrees
        g2d.translate(-cx, -cy);


        // check for fill status
        if (this.getFill()){
            g2d.fillOval(x - xRadius, y - yRadius, xRadius*2, yRadius*2);
        }
        else {
            g2d.setStroke(this.color);
            // setting the width according to thickness selected
            g2d.setLineWidth(this.getLineWidth());
            g2d.strokeOval(x - xRadius, y - yRadius, xRadius*2, yRadius*2);
        }

        // Restore the graphics state to remove the rotation for subsequent shapes
        g2d.restore();

    }
}