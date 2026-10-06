package ca.utoronto.utm.assignment2.paint;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Observable;

/**
 * The Model for the paint application, storing all shapes and style attributes.
 * This class is observable and notifies the View of any changes.
 */
public class PaintModel extends Observable {
    private final ArrayList<Shape> shapes = new ArrayList<Shape>();
    private int lineWidth = 1;
    private Color color = Color.BLACK; // Current drawing color
    private Color bgColor = Color.web("#f2f2f2"); // Current canvas background color
    private final Squiggle squiggle = new Squiggle(this.lineWidth, this.color); // added squiggle object
                                                                                // to help in clear.

    //General Shape Array Editors:

    /**
     * Adds a shape to the canvas and notifies observers.
     * @param s The shape to add.
     */
    public void addShape(Shape s){
        this.shapes.add(s);
        this.setChanged();
        this.notifyObservers();
    }

    /**
     * Removes a shape from the canvas and notifies observers.
     * @param s The shape to remove.
     */
    public void removeShape(Shape s){
        this.shapes.remove(s);
        this.setChanged();
        this.notifyObservers();
    }

    /**
     * @return The list of all shapes currently on the canvas.
     */
    public ArrayList<Shape> getShapes(){
        return shapes;
    }

    // Width Editors

    /**
     * Sets the current line thickness for drawing.
     * @param width The new line width.
     */
    public void setLineWidth(int width){
        lineWidth = width;
        this.setChanged();
        this.notifyObservers();
    }

    public int getLineWidth(){
        return lineWidth;
    }

    /**
     * Sets the primary color for drawing shapes.
     * @param colorPicked The color selected from the color picker.
     */
    public void setColorPicked(Color colorPicked) {
        this.color = colorPicked;
        this.setChanged();
        this.notifyObservers();
    }

    /**
     * Sets the background color for the canvas.
     * @param colorPicked The color selected from the background color picker.
     */
    public void setBGColorPicked(Color colorPicked) {
        bgColor = colorPicked;
        this.setChanged();
        this.notifyObservers();

    }


    public Color getBGColorPicked() {
        return bgColor;
    }

    public Color getColorPicked() {
        return color;
    }

    /**
     * Removes all shapes from the canvas and clears the current squiggle.
     */
    public void clearShapes(){
        this.shapes.clear();
        squiggle.clearSquiggle();
        this.setChanged();
        this.notifyObservers();
    }
}