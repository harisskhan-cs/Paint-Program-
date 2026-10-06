package ca.utoronto.utm.assignment2.paint;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * A DrawingTool for creating Oval shapes on the PaintPanel.
 * The oval is drawn from its center to the mouse cursor's position.
 */
public class OvalTool implements DrawingTool {
    private final PaintModel model;
    private Oval tempOval; // A temporary oval for live preview
    private Rectangle tempRec; // A temporary bounding box used for calculations
    private final PaintPanel panel;

    /**
     * Constructs an OvalTool.
     * @param model The PaintModel to add shapes to.
     * @param panel The PaintPanel to get fill and rotation status from.
     */
    public OvalTool(PaintModel model, PaintPanel panel) {
        this.model = model;
        this.panel = panel;
    }

    @Override
    public String getName() {
        return "Oval";
    }

    /**
     * Handles mouse press events by creating a new Oval at the click location.
     * @param event The mouse event.
     */
    @Override
    public void onMousePressed(MouseEvent event) {
        System.out.println("Started Oval");
        // Create bounding box
        Point centre = new Point(event.getX(), event.getY());
        this.tempRec = new Rectangle(centre, centre, panel.isSetFill(), Color.TRANSPARENT);

        // Create a (temporary) oval
        this.tempOval = new Oval(centre, 0, 0, panel.isSetFill(), this.model.getColorPicked());
        //setting the width of temp shape
        this.tempOval.setLineWidth(model.getLineWidth());
        this.model.addShape(this.tempOval);
    }

    /**
     * Handles mouse drag events by updating the oval's radii and rotation.
     * @param event The mouse event.
     */
    @Override
    public void onMouseDragged(MouseEvent event) {
        // Delete previous oval and display the new oval
        if (this.tempRec != null && this.tempOval != null) {
            this.model.removeShape(this.tempOval);

            // Update the oval's color in case it was changed mid-drag
            this.tempOval.setColor(model.getColorPicked());

            // Update bounding box
            Point corner2 = new Point(event.getX(), event.getY());
            this.tempRec.setCorner2(corner2);

            // Determine new oval to be displayed
            double xCentre = this.tempRec.getCorner1().x;
            double yCentre = this.tempRec.getCorner1().y;
            Point newCentre = new Point(xCentre, yCentre);
            double xRad = Math.abs(this.tempRec.getCorner2().x - xCentre);
            double yRad = Math.abs(this.tempRec.getCorner2().y - yCentre);

            // Update the oval
            this.tempOval.setCentre(newCentre);
            this.tempOval.setXRadius(xRad);
            this.tempOval.setYRadius(yRad);

            // Calculate rotation angle based on mouse position
            double dx = event.getX() - xCentre;
            double dy = event.getY() - yCentre;
            int angleToDegrees = (int)Math.toDegrees(Math.atan2(dy, dx));
            if (angleToDegrees < 0) {
                angleToDegrees += 360;
            }

            if (panel.isUseRotation()) {
                tempOval.setRotation(angleToDegrees);
            }

            this.model.addShape(this.tempOval);
        }

    }

    /**
     * Handles mouse release events by finalizing the oval's shape.
     * @param event The mouse event.
     */
    @Override
    public void onMouseReleased(MouseEvent event) {
        // Remove the last oval, then display the final one
        if (this.tempRec != null && this.tempOval != null) {
            this.model.removeShape(this.tempOval);

            // Update bounding box
            Point corner2 = new Point(event.getX(), event.getY());
            this.tempRec.setCorner2(corner2);

            // Determine final oval to be displayed
            double xCentre = this.tempRec.getCorner1().x;
            double yCentre = this.tempRec.getCorner1().y;
            Point newCentre = new Point(xCentre, yCentre);
            double xRad = Math.abs(this.tempRec.getCorner2().x - xCentre);
            double yRad = Math.abs(this.tempRec.getCorner2().y - yCentre);

            // Update the final oval
            this.tempOval.setColor(model.getColorPicked()); // Set final color
            this.tempOval.setCentre(newCentre);
            this.tempOval.setXRadius(xRad);
            this.tempOval.setYRadius(yRad);
            this.model.addShape(this.tempOval);

            // Clear current bounding box and oval
            this.tempRec = null;
            this.tempOval = null;
            System.out.println("Added Oval");
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