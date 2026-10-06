package ca.utoronto.utm.assignment2.paint;

import javafx.scene.input.MouseEvent;

/**
 * A DrawingTool for creating Circle shapes on the PaintPanel.
 */
public class CircleTool implements DrawingTool {
    private final PaintModel model;
    private Circle temp; // A temporary circle used for live preview during dragging
    private final PaintPanel panel;

    /**
     * Constructs a CircleTool.
     * @param model The PaintModel to add shapes to.
     * @param panel The PaintPanel to get fill status from.
     */
    public CircleTool(PaintModel model, PaintPanel panel) {
        this.model = model;
        this.panel = panel;
    }

    @Override
    public String getName() {
        return "Circle";
    }

    /**
     * Handles mouse press events by creating a new Circle at the click location.
     * @param event The mouse event.
     */
    @Override
    public void onMousePressed(MouseEvent event) {
        System.out.println("Started Circle");
        Point center = new Point(event.getX(), event.getY());
        // Create a new circle with zero radius, but correct color and fill
        this.temp = new Circle(center, 0, panel.isSetFill(), model.getColorPicked());
        //setting the width of temp shape
        this.temp.setLineWidth(model.getLineWidth());
        this.model.addShape(this.temp);
    }

    /**
     * Handles mouse drag events by continuously updating the radius of the temporary circle.
     * @param mouseEvent The mouse event.
     */
    @Override
    public void onMouseDragged(MouseEvent mouseEvent) {
        // remove the previous circle before adding a new one
        this.model.removeShape(this.temp);

        // Update the circle's color in case it was changed mid-drag
        this.temp.setColor(model.getColorPicked());

        double radiusY = mouseEvent.getY() - this.temp.getCentre().y;
        double radiusX = mouseEvent.getX() - this.temp.getCentre().x;
        double actualRadius = Math.sqrt((radiusX * radiusX) + (radiusY * radiusY));
        this.temp.setRadius(actualRadius);

        // adding another temp circle while mouse is dragged
        this.model.addShape(this.temp);
    }

    /**
     * Handles mouse release events by finalizing the circle's size and color.
     * @param event The mouse event.
     */
    @Override
    public void onMouseReleased(MouseEvent event) {
        if (this.temp != null) {
            this.model.removeShape(this.temp);

            // Set the final color and radius
            this.temp.setColor(model.getColorPicked());
            double dx = event.getX() - this.temp.getCentre().x;
            double dy = event.getY() - this.temp.getCentre().y;
            double r = Math.sqrt(dx * dx + dy * dy);
            this.temp.setRadius(r);

            this.model.addShape(this.temp);
            System.out.println("Added Circle");
            this.temp = null; // Finalize the shape
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