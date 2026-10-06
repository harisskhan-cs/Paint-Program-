package ca.utoronto.utm.assignment2.paint;

import javafx.scene.input.MouseEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;

/**
 * A DrawingTool for creating Polylines (a series of connected straight lines).
 * Each left-click adds a new point, and a right-click finalizes the shape.
 * @author Adam Laazizi
 */
public class PolylineTool implements DrawingTool{
    private final PaintModel model;
    Squiggle squiggle; // Uses a Squiggle object to store the points of the polyline
    protected Color colour;
    private boolean started = false; // Tracks if the polyline has been started


    public PolylineTool(PaintModel model) {
        this.model = model;
    }

    /**
     * Sets the tool's current color from the model.
     */
    protected void setColor(){
        colour = this.model.getColorPicked();
    }

    @Override
    public String getName() {
        return "Polyline";
    }

    /**
     * Handles mouse press events. A left-click adds a new point, and a right-click finalizes the polyline.
     * @param event The mouse event.
     */
    @Override
    public void onMousePressed(MouseEvent event) {
        MouseButton input = event.getButton();

        if (!started){
            // This is the first click, so start a new polyline
            started = true;
            setColor();
            squiggle = new Squiggle(model.getLineWidth(), colour);
            squiggle.addPoint(null); // Add a null point to be replaced by onMouseMoved
        }

        // Add the clicked point
        squiggle.addPoint(new Point(event.getX(), event.getY()));
        // Temporary empty point that will be instantly deleted and replaced upon moving.
        squiggle.addPoint(null);

        this.model.addShape(squiggle);

        if (input == MouseButton.SECONDARY){
            // Right-click finishes the shape
            started = false;
        }
    }

    /**
     * Not used by this tool.
     * @param event The mouse event.
     */
    @Override
    public void onMouseDragged(MouseEvent event) {
        return;
    }

    /**
     * Not used by this tool.
     * @param event The mouse event.
     */
    @Override
    public void onMouseReleased(MouseEvent event) {
        return;
    }

    /**
     * Handles mouse move events to show a live preview of the next line segment.
     * @param event The mouse event.
     */
    @Override
    public void onMouseMoved(MouseEvent event) {
        if (started){
            this.model.removeShape(squiggle);
            squiggle.pop(); // Remove the temporary null/preview point
            // Add the new preview point based on the cursor's current location
            squiggle.addPoint(new Point(event.getX(), event.getY()));
            model.addShape(squiggle);
        }

    }
}