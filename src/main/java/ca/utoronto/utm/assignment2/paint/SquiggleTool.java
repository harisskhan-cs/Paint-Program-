package ca.utoronto.utm.assignment2.paint;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * A DrawingTool for creating free-form Squiggle shapes on the PaintPanel.
 */
public class SquiggleTool implements DrawingTool {
    protected PaintModel model;
    Squiggle squiggle; // The current squiggle shape being drawn
    protected Color color;


    /**
     * Constructs a SquiggleTool.
     * @param model The PaintModel to add shapes to.
     */
    public SquiggleTool(PaintModel model) {
        this.model = model;
    }

    /**
     * Sets the tool's current color from the model.
     */
    protected void setColor(){
        color = this.model.getColorPicked();
    }

    @Override
    public String getName() {
        return "Squiggle";
    }

    /**
     * Handles mouse press events by starting a new Squiggle.
     * @param event The mouse event.
     */
    @Override
    public void onMousePressed(MouseEvent event) {

        setColor();
        squiggle = new Squiggle(model.getLineWidth(), color);
        squiggle.addPoint(null); // Add a null point to prevent drawing from (0,0)
        squiggle.addPoint(new Point(event.getX(), event.getY()));
        this.model.addShape(squiggle);
    }

    /**
     * Handles mouse drag events by adding new points to the current squiggle.
     * @param event The mouse event.
     */
    @Override
    public void onMouseDragged(MouseEvent event) {
        // Update color in case it changed mid-drag
        squiggle.setColor(color);
        // Update line width in case it changed mid-drag
        squiggle.setLineWidth(model.getLineWidth());

        squiggle.addPoint(new Point(event.getX(), event.getY()));
        this.model.addShape(squiggle); // Re-add to trigger a repaint
    }

    /**
     * Handles mouse release events, finalizing the squiggle.
     * @param event The mouse event.
     */
    @Override
    public void onMouseReleased(MouseEvent event) {
        // Set the final color
        if(squiggle != null) {
            squiggle.setColor(color);
            squiggle = null; // Finalize the shape
        }
    }

    /**
     * Not used by this tool.
     * @param event The mouse event.
     */
    @Override
    public void onMouseMoved(MouseEvent event) {
        // no code for thus hahahaha
    }
}