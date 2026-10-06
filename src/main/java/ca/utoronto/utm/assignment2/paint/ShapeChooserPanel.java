package ca.utoronto.utm.assignment2.paint;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import java.util.Objects;

/**
 * A GridPane panel that displays all tool selection buttons and style controls.
 * It handles user interaction for selecting tools, fill, rotation, and colors.
 */
public class ShapeChooserPanel extends GridPane implements EventHandler<ActionEvent> {

    private final View view;
    private final PaintModel paintModel;
    private boolean fillMemory = false; // Memory for the 'Fill' checkbox state when switching tools
    private boolean rotationMemory = false; // Memory for the 'Rotate' checkbox state when switching tools

    /**
     * Constructs the ShapeChooserPanel.
     * @param view The main View, used for setting the mode.
     * @param paintModel The PaintModel, used for setting colors.
     */
    public ShapeChooserPanel(View view, PaintModel paintModel) {

        this.view = view;
        this.paintModel = paintModel;

        // All icons as Image objects
        Image eraser = new Image(Objects.requireNonNull(getClass().getResourceAsStream(
                "/icons/eraser.png")));
        Image nuke = new Image(Objects.requireNonNull(getClass().getResourceAsStream(
                "/icons/nuke.png")));
        Image circle = new Image(Objects.requireNonNull(getClass().getResourceAsStream(
                "/icons/circle.png")));
        Image oval = new Image(Objects.requireNonNull(getClass().getResourceAsStream(
                "/icons/oval.png")));
        Image triangle = new Image(Objects.requireNonNull(getClass().getResourceAsStream(
                "/icons/triangle.png")));
        Image rectangle = new Image(Objects.requireNonNull(getClass().getResourceAsStream(
                "/icons/rectangle.png")));
        Image square = new Image(Objects.requireNonNull(getClass().getResourceAsStream(
                "/icons/square.png")));
        Image squiggle = new Image(Objects.requireNonNull(getClass().getResourceAsStream(
                "/icons/squiggle.png")));
        Image polyline = new Image(Objects.requireNonNull(getClass().getResourceAsStream(
                "/icons/polyline.png")));

        Object[][] icons = {{eraser, "Eraser"}, {nuke, "Nuke"},{circle, "Circle"}, {oval, "Oval"}, {triangle, "Triangle"},
                {rectangle, "Rectangle"}, {square, "Square"}, {squiggle, "Squiggle"}, {polyline, "Polyline"}};

        int row = 0;
        // Create toggle group, this will be used for deselection, and other
        // button-specific features
        ToggleGroup group = new ToggleGroup();
        // Map Used to associate each button to a checkbox
        for (Object[] icon : icons) {
            Image image = (Image) icon[0];
            String label = (String) icon[1];

            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(30);
            imageView.setFitHeight(30);

            // Changed from standard button to ToggleButton, this shows selection.
            ToggleButton button = new ToggleButton(label, imageView);
            button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            button.setMinWidth(100);
            button.getStyleClass().add("shape-button");
            this.add(button, 0, row);
            row++;
            button.setOnAction(this);
            button.setToggleGroup(group);

            // Hover Text
            Tooltip tooltip = new Tooltip(label);
            button.setTooltip(tooltip);
        }
        // Style Selectors
        Label selectorsLabel = new Label("Style Selectors:");
        this.add(selectorsLabel, 0, row);
        row++;

        // Fill / Outline
        CheckBox fillBox =  new CheckBox("Fill");
        fillBox.setDisable(true);
        this.add(fillBox, 0, row);
        fillBox.setOnAction(this);

        row++;
        // for rotation button
        CheckBox rotationCheck =  new CheckBox("Rotate");
        rotationCheck.setDisable(true);
        this.add(rotationCheck, 0, row);
        rotationCheck.setOnAction(this);

        // original color picker for shapes and scribbles
        Label colorsLabel = new Label("Paint Color:");
        row++;
        this.add(colorsLabel, 0, row);
        ColorPicker colorPicker = new ColorPicker(Color.BLACK);
        row++;
        this.add(colorPicker, 0, row);
        colorPicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Color color = colorPicker.getValue();
                paintModel.setColorPicked(color);
            }
        });

        // extra color picker for Background of canvas
        ColorPicker bgColorPicker = new ColorPicker(Color.web("#f2f2f2"));
        row++;
        Label BGtag = new Label("Background:");
        this.add(BGtag, 0, row);
        row++;
        this.add(bgColorPicker, 0, row);

        bgColorPicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Color color = bgColorPicker.getValue();
                paintModel.setBGColorPicked(color);

            }
        });

        // ToggleButton-dependant selector logic
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle>
                                        observable, Toggle oldTool, Toggle newTool) {

                // --- This block prevents the user from deselecting a tool ---
                if (newTool == null && oldTool != null) {
                    javafx.application.Platform.runLater(() -> {
                        group.selectToggle(oldTool);
                    });
                    return; // Stop processing
                }

                ToggleButton tool = (ToggleButton) newTool;
                // Get list of all buttons from the group
                ObservableList<Toggle> allToggles = group.getToggles();

                for (Toggle toggle : allToggles) {
                    ToggleButton tb = (ToggleButton) toggle;

                    // Case: A tool is selected
                    if (toggle == newTool) {
                        // Guaranteeing the selected tool isn't red
                        tb.getStyleClass().remove("unselected-tool");
                    } else {
                        // All other buttons become red
                        if (!tb.getStyleClass().contains("unselected-tool")) {
                            tb.getStyleClass().add("unselected-tool");
                        }
                    }
                }

                // Making sure button selected is one of the shapes
                if (!(tool.getText().equals("Squiggle") || tool.getText().equals("Eraser")
                        || tool.getText().equals("Polyline") || tool.getText().equals("Nuke"))){
                    //Enabling toggling
                    fillBox.setDisable(false);
                    rotationCheck.setDisable(false);

                    // Check if it was enabled before switching to non-shape:
                    if (fillMemory){
                        fillBox.setSelected(true);
                    }
                    if (rotationMemory){
                        rotationCheck.setSelected(true);
                    }

                } else {
                    // Rotation Box
                    rotationCheck.setSelected(false);
                    rotationCheck.setDisable(true);


                    // Fill Box
                    fillBox.setSelected(false);
                    fillBox.setDisable(true);
                }
            }
        });
    }

    /**
     * Handles events from the tool buttons and checkboxes.
     * @param event The action event.
     */
    @Override
    public void handle(ActionEvent event) {
        Object source = event.getSource();
        // Tool selector case
        if (source instanceof ToggleButton) {
            String command = ((ToggleButton) event.getSource()).getText();
            view.setMode(command);
        }
        // Style selector case
        else if (source instanceof CheckBox){
            // Update the memory for fill/rotation
            if (((CheckBox) source).getText().equals("Fill")){
                fillMemory = !fillMemory;
            } else if (((CheckBox) source).getText().equals("Rotate")){
                rotationMemory = !rotationMemory;
            }
            String command = ((CheckBox) event.getSource()).getText();
            view.setMode(command);
        }

    }
}