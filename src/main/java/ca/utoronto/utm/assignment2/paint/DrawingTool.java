package ca.utoronto.utm.assignment2.paint;

import javafx.scene.input.MouseEvent;

/**
 * Defines the contract for all drawing tools (e.g., Circle, Squiggle).
 * This interface allows the PaintPanel to treat all tools polymorphically.
 */
public interface DrawingTool {

    // a new class to define each tools and the events
    // associated with it .
    // this is to fix BUG2.003 where we have to refactor to remove if-statementsw

    /**
     * @return The simple name of the tool (e.g., "Circle", "Eraser").
     */
    String getName(); // "circle", "rectagnle", etc


    /**
     * Called by the panel when the mouse button is pressed.
     * @param event The mouse event.
     */
    void onMousePressed(MouseEvent event);

    /**
     * Called by the panel when the mouse is dragged.
     * @param event The mouse event.
     */
    void onMouseDragged(MouseEvent event);

    /**
     * Called by the panel when the mouse button is released.
     * @param event The mouse event.
     */
    void onMouseReleased(MouseEvent event);

    // I see this event in an if-statement but there's no code assigned to it for any tool.
    // I'm keeping this here just in case
    /**
     * Called by the panel when the mouse is moved (not dragged).
     * @param event The mouse event.
     */
    void onMouseMoved(MouseEvent event);



}