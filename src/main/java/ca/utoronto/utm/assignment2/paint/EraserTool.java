package ca.utoronto.utm.assignment2.paint;

import javafx.scene.paint.Color;


/**
 * A tool for erasing, which functions as a SquiggleTool that paints using the canvas's background color.
 * @author Adam Laazizi
 */
public class EraserTool extends SquiggleTool{
    private boolean done = false;
    private Color pastColor;


    public EraserTool(PaintModel model) {
        super(model);
    }

    /**
     * Toggles the squiggle color between the canvas background color and the previously selected color.
     */
    @Override
    protected void setColor(){
        if (done){
            color = pastColor;
        } else {
            pastColor = color;
            color = model.getBGColorPicked();
        }
        done = !done;
    }

    @Override
    public String getName() {
        return "Eraser";
    }
}