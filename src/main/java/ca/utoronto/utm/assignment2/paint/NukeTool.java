package ca.utoronto.utm.assignment2.paint;

import javafx.scene.input.MouseEvent;

/**
 * A DrawingTool that creates a large circle of the background color wherever the mouse is pressed or dragged.
 * @author Adam Laazizi
 */
public class NukeTool implements DrawingTool{
    PaintModel model;
    PaintPanel panel;

    /**
     * Constructs a NukeTool.
     * @param model The PaintModel to add shapes to.
     * @param panel The PaintPanel (not used by this tool but required by convention).
     */
    public NukeTool(PaintModel model, PaintPanel panel) {
        this.model = model;
        this.panel = panel;
    }

    @Override
    public String getName() {
        return "Nuke";
    }

    /**
     * Handles mouse press events by creating a large, background-colored circle at the event location.
     * @param event The mouse event.
     */
    @Override
    public void onMousePressed(MouseEvent event) {
        System.out.println("Started Circle");
        Point center = new Point(event.getX(), event.getY());
        // Creates a filled circle using the background color
        Circle temp = new Circle(center, 0, true, model.getBGColorPicked());
        //setting the width of temp shape
        temp.setLineWidth(model.getLineWidth());
        // Set a large radius based on the line width
        temp.setRadius(model.getLineWidth() * 10);
        model.addShape(temp);
    }

    /**
     * Handles mouse drag events by continuously calling onMousePressed to create a stream of circles.
     * @param mouseEvent The mouse event.
     */
    @Override
    public void onMouseDragged(MouseEvent mouseEvent) {
        onMousePressed(mouseEvent);
    }

    /**
     * Not used by this tool.
     * @param event The mouse event.
     */
    @Override
    public void onMouseReleased(MouseEvent event) {
    }

    /**
     * Not used by this tool.
     * @param event The mouse event.
     */
    @Override
    public void onMouseMoved(MouseEvent event) {
    }
}