package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;


/**
 * An interface that defines the behavior for all drawable shapes in the application.
 * Ensures that every shape can be drawn and rotated.
 * @author Adam Laazizi
 */
public interface Shape {

    /**
     * Sets the rotation angle of the shape.
     * @param rotation The angle in degrees.
     */
    void setRotation(double rotation);

    /**
     * @return The current rotation angle of the shape in degrees.
     */
    double getRotation();

    /**
     * Draws the shape onto the specified graphics context.
     * @param g2d The graphics context to draw on.
     */
    void draw(GraphicsContext g2d);


}