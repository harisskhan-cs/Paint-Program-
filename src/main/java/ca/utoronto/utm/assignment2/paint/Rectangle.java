package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Represents a drawable Rectangle shape, defined by two opposite corners.
 * @author Adam Laazizi
 */
public class Rectangle implements Shape{
    private final Point corner1;
    private Point corner2;
    private final boolean setFill;
    private int lineWidth;
    private Color color;
    private double rotation; // goes from 0-359 degrees

    /**
     * Constructs a new Rectangle with the specified properties.
     * @param corner1 The first corner point (often the start of the drag).
     * @param corner2 The second corner point (often the end of the drag).
     * @param setFill True for a filled rectangle, false for an outline.
     * @param color The color of the rectangle.
     */
    public Rectangle(Point corner1, Point corner2, boolean setFill, Color color){
        this.corner1 = corner1;
        this.corner2 = corner2;
        this.setFill = setFill;
        this.lineWidth = 1;
        this.color = color;
        this.rotation = 0; // as a test
    }

    @Override
    public void setRotation(double rotation){
        this.rotation = rotation;
    }

    @Override
    public double getRotation(){
        return rotation;
    }

    public Point getCorner1() {
        return corner1;
    }

    public Point getCorner2() {
        return corner2;
    }

    public void setCorner2(Point new_corner){
        this.corner2 = new_corner;
    }

    /**
     * @return True if the rectangle should be filled, false if outlined.
     */
    public boolean getFill(){
        return setFill;
    }

    /**
     * Sets the line thickness for the rectangle's outline.
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
     * Draws the rectangle onto the specified graphics context, applying rotation.
     * @param g2d The graphics context to draw on.
     */
    @Override
    public void draw(GraphicsContext g2d){
        g2d.setFill(this.color);
        double x1 = this.getCorner1().x;
        double y1 = this.getCorner1().y;
        double x2 = this.getCorner2().x;
        double y2 = this.getCorner2().y;
        // Changed to absolute value in case of left or up drag
        double w = Math.abs(x1 - x2);
        double h = Math.abs(y2 - y1);
        // Take the smaller value of coordinate as starting coordinate
        // (Took me much longer than anticipated to fix 🫩)

        double cx = x1;
        double cy = y1;

        double x = Math.min(x1, x2);
        double y = Math.min(y1, y2);

        // for rotation
//        double cx = x + w / 2.0;
//        double cy = y + h / 2.0;

        // Save the current graphics state
        g2d.save();
        // Apply rotation around the first corner (cx, cy)
        g2d.translate(cx, cy);
        g2d.rotate(rotation); // JavaFX .rotate() uses degrees
        g2d.translate(-cx, -cy);


        // check for fill status
        if (this.getFill()){
            g2d.fillRect(x, y, w, h);
        }
        else {
            g2d.setStroke(this.color);
            // setting the width according to thickness selected
            g2d.setLineWidth(this.getLineWidth());
            g2d.strokeRect(x, y, w, h);
        }

        // Restore the graphics state to remove the rotation
        g2d.restore();


    }
}