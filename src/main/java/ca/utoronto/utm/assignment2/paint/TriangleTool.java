package ca.utoronto.utm.assignment2.paint;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * A DrawingTool for creating isosceles Triangle shapes on the PaintPanel.
 */
public class TriangleTool implements DrawingTool {
    private final PaintModel model;
    private Triangle tempTri; // A temporary triangle for live preview
    private Rectangle tempRec; // A temporary bounding box for calculations
    private final PaintPanel panel;

    /**
     * Constructs a TriangleTool.
     * @param model The PaintModel to add shapes to.
     * @param panel The PaintPanel to get fill and rotation status from.
     */
    public TriangleTool(PaintModel model, PaintPanel panel) {
        this.model = model;
        this.panel = panel;
    }

    @Override
    public String getName() {
        return "Triangle";
    }

    /**
     * Handles mouse press events by creating a new, zero-size Triangle at the click location.
     * @param event The mouse event.
     */
    @Override
    public void onMousePressed(MouseEvent event) {
        System.out.println("Started Triangle");
        // Create bounding-box (to fit half triangle)
        Point startPoint = new Point(event.getX(), event.getY());
        this.tempRec = new Rectangle(startPoint, startPoint, panel.isSetFill(), Color.TRANSPARENT);

        // Create a (temporary) triangle
        this.tempTri = new Triangle(startPoint, startPoint, startPoint, panel.isSetFill(), this.model.getColorPicked());
        //setting the width of temp shape
        this.tempTri.setLineWidth(model.getLineWidth());
        this.model.addShape(this.tempTri);
    }

    /**
     * Handles mouse drag events by updating the triangle's corners and rotation.
     * @param event The mouse event.
     */
    @Override
    public void onMouseDragged(MouseEvent event) {
        // Remove previous triangle to add a new one
        if (this.tempRec != null && this.tempTri != null) {
            this.model.removeShape(this.tempTri);

            // Update color in case it changed mid-drag
            this.tempTri.setColor(model.getColorPicked());

            // Update bounding box
            Point corner2 = new Point(event.getX(), event.getY());
            this.tempRec.setCorner2(corner2);

            // Determine new triangle
            double middleCornerX = this.tempRec.getCorner1().x;
            double middleCornerY = this.tempRec.getCorner1().y;
            double cornerDraggedX = this.tempRec.getCorner2().x;
            double cornerDraggedY = this.tempRec.getCorner2().y;
            // Calculate the third point to form an isosceles triangle
            double distanceToCenterX = this.tempRec.getCorner2().x - this.tempRec.getCorner1().x;
            double cornerOppX = this.tempRec.getCorner2().x - 2*distanceToCenterX;
            double cornerOppY = this.tempRec.getCorner2().y;

            // Update triangle
            this.tempTri.setMiddleCorner(new Point(middleCornerX, middleCornerY));
            this.tempTri.setCornerDragged(new Point(cornerDraggedX, cornerDraggedY));
            this.tempTri.setOppositeCorner(new Point(cornerOppX, cornerOppY));

            // Calculate rotation angle
            double dx = cornerDraggedX - middleCornerX;
            double dy = cornerDraggedY - middleCornerY;
            double angleToDegree = Math.toDegrees(Math.atan2(dy, dx));

            if (angleToDegree < 0) {
                angleToDegree += 360;
            }

            if (panel.isUseRotation()) {
                this.tempTri.setRotation(angleToDegree);
            }
            this.model.addShape(this.tempTri);



        }

    }

    /**
     * Handles mouse release events by finalizing the triangle's shape.
     * @param event The mouse event.
     */
    @Override
    public void onMouseReleased(MouseEvent event) {
        // Remove last triangle and add the final triangle
        if (this.tempRec != null && this.tempTri != null) {
            this.model.removeShape(this.tempTri);

            // Set the final color
            this.tempTri.setColor(model.getColorPicked());

            // Update bounding box
            Point corner2 = new Point(event.getX(), event.getY());
            this.tempRec.setCorner2(corner2);

            // Determine new triangle
            double middleCornerX = this.tempRec.getCorner1().x;
            double middleCornerY = this.tempRec.getCorner1().y;
            double cornerDraggedX = this.tempRec.getCorner2().x;
            double cornerDraggedY = this.tempRec.getCorner2().y;
            double distanceToCenterX = this.tempRec.getCorner2().x - this.tempRec.getCorner1().x;
            double cornerOppX = this.tempRec.getCorner2().x - 2 * distanceToCenterX;
            double cornerOppY = this.tempRec.getCorner2().y;

            // Update final triangle to display
            this.tempTri.setMiddleCorner(new Point(middleCornerX, middleCornerY));
            this.tempTri.setCornerDragged(new Point(cornerDraggedX, cornerDraggedY));
            this.tempTri.setOppositeCorner(new Point(cornerOppX, cornerOppY));
            this.model.addShape(this.tempTri);

            // Reset current bounding box and triangle
            this.tempRec = null;
            this.tempTri = null;
            System.out.println("Added Triangle");
        }
    }

    /**
     * Not used by this tool.
     * @param event The mouse event.
     */
    @Override
    public void onMouseMoved(MouseEvent event) {}

}