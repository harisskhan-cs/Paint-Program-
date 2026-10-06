package ca.utoronto.utm.assignment2.paint;

import javafx.scene.input.MouseEvent;

/**
 * A DrawingTool for creating Square shapes on the PaintPanel.
 * This tool constrains a Rectangle to have equal width and height.
 */
public class SquareTool implements DrawingTool {
    private final PaintModel model;
    private Rectangle rectangle; // Uses a Rectangle object to draw the square
    private PaintPanel panel;

    /**
     * Constructs a SquareTool.
     * @param model The PaintModel to add shapes to.
     * @param panel The PaintPanel to get fill and rotation status from.
     */
    public SquareTool(PaintModel model, PaintPanel panel) {
        this.model = model;
        this.panel = panel;
    }

    @Override
    public String getName() {
        return "Square";
    }

    /**
     * Handles mouse press events by creating a new, zero-size square at the click location.
     * @param event The mouse event.
     */
    @Override
    public void onMousePressed(MouseEvent event) {
        System.out.println("Started Square");

        Point corner1 = new Point(event.getX(), event.getY());
        Point corner2 = new Point(event.getX(), event.getY());
        this.rectangle = new Rectangle(corner1, corner2, panel.isSetFill(), this.model.getColorPicked());
        // adding a temp rectangle(same as rectangle)
        //setting the width of temp shape
        this.rectangle.setLineWidth(model.getLineWidth());
        this.model.addShape(this.rectangle);
    }

    /**
     * Handles mouse drag events by updating the square's size and rotation, ensuring equal sides.
     * @param mouseEvent The mouse event.
     */
    @Override
    public void onMouseDragged(MouseEvent mouseEvent) {
        if (this.rectangle != null) {
            //  removing the temp rectangle(same as rectangle)
            //  making sure the sides are equal no matter what direction mouse moves
            this.model.removeShape(this.rectangle);

            // Update the color in case it changed mid-drag
            this.rectangle.setColor(model.getColorPicked());

            Point currCorner = new Point(mouseEvent.getX(), mouseEvent.getY());
            double sideX = mouseEvent.getX() - this.rectangle.getCorner1().x;
            double sideY = mouseEvent.getY() - this.rectangle.getCorner1().y;
            double maxSide;
            // Constrain the rectangle to a square by using the larger of dx or dy
            if (Math.abs(sideX) >= Math.abs(sideY)){
                maxSide = Math.abs(sideX);
            }
            else { maxSide = Math.abs(sideY); }

            // creating second corner for square, preserving the quadrant
            double c2x;
            if (sideX >= 0) {
                c2x = this.rectangle.getCorner1().x + maxSide;
            }
            else {c2x = this.rectangle.getCorner1().x - maxSide;}

            double c2y;
            if (sideY >= 0) {
                c2y = this.rectangle.getCorner1().y + maxSide;
            }
            else {c2y = this.rectangle.getCorner1().y - maxSide;}

            this.rectangle.setCorner2(new Point(c2x, c2y));

            // angle computations
            Point c1 = rectangle.getCorner1();
            double dx = mouseEvent.getX() - c1.x;
            double dy = mouseEvent.getY() - c1.y;

            int angleToDegrees = (int) Math.toDegrees(Math.atan2(dy, dx));

            if (angleToDegrees < 0) {
                angleToDegrees += 360;
            }

            if (panel.isUseRotation()) {
                rectangle.setRotation(angleToDegrees);
            }

            this.model.addShape(this.rectangle);

        }
    }

    /**
     * Handles mouse release events by finalizing the square's position.
     * @param mouseEvent The mouse event.
     */
    @Override
    public void onMouseReleased(MouseEvent mouseEvent) {
        if (this.rectangle!=null){
            // removing the temp rectangle(same as rectangle)
            this.model.removeShape(this.rectangle);

            // Set the final color
            this.rectangle.setColor(model.getColorPicked());

            Point currCorner = new Point(mouseEvent.getX(), mouseEvent.getY());
            double sideX = mouseEvent.getX() - this.rectangle.getCorner1().x;
            double sideY = mouseEvent.getY() - this.rectangle.getCorner1().y;
            double maxSide;
            if (Math.abs(sideX) >= Math.abs(sideY)){
                maxSide = Math.abs(sideX);
            }
            else { maxSide = Math.abs(sideY); }
            // creating second corner for square
            // making sure the sides are equal no matter what direction mouse moves
            double c2x;
            if (sideX >= 0) {
                c2x = this.rectangle.getCorner1().x + maxSide;
            }
            else {c2x = this.rectangle.getCorner1().x - maxSide;}

            double c2y;
            if (sideY >= 0) {
                c2y = this.rectangle.getCorner1().y + maxSide;
            }
            else {c2y = this.rectangle.getCorner1().y - maxSide;}

            this.rectangle.setCorner2(new Point(c2x, c2y));
            this.model.addShape(this.rectangle);;
            System.out.println("Added Square");
            this.rectangle = null; // Finalize the shape

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