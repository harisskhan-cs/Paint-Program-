package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.Canvas;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
// Imported for absolute value function, and minimum function.


/**
 * The main drawing canvas for the paint application.
 * This class handles all mouse events and observes the PaintModel to know when to repaint.
 */
public class PaintPanel extends Canvas implements EventHandler<MouseEvent>, Observer {
    private String mode = "Eraser";
    private final PaintModel model;
    private boolean setFill = false; // Set to outline by default
    private boolean useRotation = false;
    private Color bgColor; // check if proper class was imported, and also check if it should be "final"

    private HashMap<String, DrawingTool> tools = new HashMap<String, DrawingTool>();
    private DrawingTool currentTool; // new thing as a result of refactor efforts

    /**
     * Constructs a new PaintPanel, registers all drawing tools, and sets up event handlers.
     * @param model The PaintModel to observe and update.
     */
    public PaintPanel(PaintModel model) {
        super(1300, 1300); // Sets a large, fixed size for the canvas
        this.model=model;
        this.model.addObserver(this);

        // Adding all the tools to the Panel
        registerTool(new CircleTool(this.model, this));
        registerTool(new OvalTool(this.model, this));
        registerTool(new TriangleTool(this.model, this));
        registerTool(new RectangleTool(this.model, this));
        registerTool(new SquareTool(this.model, this));
        registerTool(new NukeTool(this.model, this));
        registerTool(new SquiggleTool(this.model));
        registerTool(new EraserTool(this.model));
        registerTool(new PolylineTool(this.model));

        this.addEventHandler(MouseEvent.MOUSE_PRESSED, this);
        this.addEventHandler(MouseEvent.MOUSE_RELEASED, this);
        this.addEventHandler(MouseEvent.MOUSE_MOVED, this);
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, this);
        this.addEventHandler(MouseEvent.MOUSE_DRAGGED, this);
    }
    /**
     * Controller method to set the active drawing mode or style.
     * @param mode The command string from the View (e.g., "Circle", "Fill").
     */
    public void setMode(String mode){
        if (mode.equals("Fill")){
            setFill = !setFill;
        }
        else if (mode.equals("Rotate")) {
            useRotation = !useRotation;
        }else{
            this.mode=mode;
            // Removed returning back to Fill
            //setFill = true;
            setTool(mode);
        }
        // Not sure if we need this print statement since a new one was added in setTool.
        // System.out.println(this.mode);
    }

    public void setBgColor(Color bgColor){
        this.bgColor = bgColor;

        // there was supposed to be more here to mess with the background itself


    }

    /**
     * @return True if the current tool should draw filled shapes, false otherwise.
     */
    public boolean isSetFill(){
        return this.setFill;
    }

    /**
     * @return True if the current tool should apply rotation, false otherwise.
     */
    public boolean isUseRotation(){return this.useRotation;}

    /**
     * Adds a new DrawingTool to the panel's collection of available tools.
     * @param tool The tool to register.
     */
    private void registerTool(DrawingTool tool){

        tools.put(tool.getName(), tool);

    }

    /**
     * Sets the active DrawingTool based on its name.
     * @param name The name of the tool to set as current.
     */
    public void setTool(String name) {

        DrawingTool tool = tools.get(name);

        if (tool != null) {
            this.currentTool = tool;
            System.out.println("Tool set to: " + name);

        } else {
            System.out.println("Tool not found: " + name);
        }

    }

    /**
     * Main mouse event handler for the canvas.
     * Delegates the event to the currently active DrawingTool.
     * @param event The mouse event (e.g., PRESSED, DRAGGED, RELEASED).
     */
    public void handle(MouseEvent event) {

        // edge case
        // made the default tool EraserTool
        if (this.currentTool == null) {
            setTool("Eraser");
        }

        EventType<MouseEvent> eventType = (EventType<MouseEvent>) event.getEventType();

        // Forward the event to the current tool
        if (eventType == MouseEvent.MOUSE_PRESSED) {
            currentTool.onMousePressed(event);
        }
        else if (eventType == MouseEvent.MOUSE_RELEASED) {
            currentTool.onMouseReleased(event);
        } else if (eventType == MouseEvent.MOUSE_DRAGGED) {
            currentTool.onMouseDragged(event);
        } else if (eventType == MouseEvent.MOUSE_MOVED){
            currentTool.onMouseMoved(event);
        }
    }

    /**
     * Called by the Observable PaintModel whenever the model changes.
     * This method redraws the entire canvas.
     * @param o The observable object (PaintModel).
     * @param arg Arguments passed by the notifyObservers() call (not used).
     */
    @Override
    public void update(Observable o, Object arg) {

        GraphicsContext g2d = this.getGraphicsContext2D();

        // Clear the canvas with the current background color
        g2d.setFill(model.getBGColorPicked());
        g2d.fillRect(0,0,this.getWidth(),this.getHeight());

        // Draw Everything
        ArrayList<Shape> art = model.getShapes();
        for (Shape s: art){
            s.draw(g2d);
        }
    }
}