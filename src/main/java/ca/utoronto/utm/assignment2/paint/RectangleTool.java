package ca.utoronto.utm.assignment2.paint;

import javafx.scene.input.MouseEvent;

/**
 * A DrawingTool for creating Rectangle shapes on the PaintPanel.
 */
public class RectangleTool implements DrawingTool {
    private final PaintModel model;
    private Rectangle temp; // A temporary rectangle used for live preview
    private final PaintPanel panel;

    /**
     * Constructs a RectangleTool.
     * @param model The PaintModel to add shapes to.
     * @param panel The PaintPanel to get fill and rotation status from.
     */
    public RectangleTool(PaintModel model, PaintPanel panel) {
        this.model = model;
        this.panel = panel;
    }

    @Override
    public String getName() {
        return "Rectangle";
    }

    /**
     * Handles mouse press events by creating a new, zero-size Rectangle at the click location.
     * @param event The mouse event.
     */
    @Override
    public void onMousePressed(MouseEvent event) {
        System.out.println("Started Rectangle");
        Point c1 = new Point(event.getX(), event.getY());
        Point c2 = new Point(event.getX(), event.getY());
        this.temp = new Rectangle(c1, c2, panel.isSetFill(), this.model.getColorPicked());
        //setting the width of temp shape
        this.temp.setLineWidth(model.getLineWidth());
        this.model.addShape(this.temp);
    }

    /**
     * Handles mouse drag events by updating the rectangle's second corner and rotation.
     * @param event The mouse event.
     */
    @Override
    public void onMouseDragged(MouseEvent event) {
        if (this.temp != null) {
            this.model.removeShape(this.temp);

            // Update the color in case it changed mid-drag
            this.temp.setColor(model.getColorPicked());

            this.temp.setCorner2(new Point(event.getX(), event.getY()));

            // angle computations
            Point c1 = temp.getCorner1();
            double dx = event.getX() - c1.x;
            double dy = event.getY() - c1.y;

            int angleToDegrees = (int) Math.toDegrees(Math.atan2(dy, dx));

            if (angleToDegrees < 0) {
                angleToDegrees += 360;
            }

            if (panel.isUseRotation()) {
                temp.setRotation(angleToDegrees);
            }



            this.model.addShape(this.temp);
        }
    }

    /**
     * Handles mouse release events by finalizing the rectangle's position.
     * @param event The mouse event.
     */
    @Override
    public void onMouseReleased(MouseEvent event) {
        if (this.temp != null) {
            this.model.removeShape(this.temp);

            // Set the final color and corner
            this.temp.setColor(model.getColorPicked());
            this.temp.setCorner2(new Point(event.getX(), event.getY()));

            this.model.addShape(this.temp);
            System.out.println("Added Rectangle");
            this.temp = null; // Finalize the shape

        }
    }

    /**
     * Not used by this tool.
     * @param event The mouse event.
     */
    @Override
    public void onMouseMoved(MouseEvent event) {
        // no code for this yet
    }
}