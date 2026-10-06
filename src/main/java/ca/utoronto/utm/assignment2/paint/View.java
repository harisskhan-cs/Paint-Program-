package ca.utoronto.utm.assignment2.paint;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL; // Added for safe CSS loading
import java.util.Objects;

/**
 * The main View class for the application, which also acts as a primary controller for menu and button events.
 */
public class View implements EventHandler<ActionEvent> {

    private final PaintModel paintModel;
    private final PaintPanel paintPanel;
    private ShapeChooserPanel shapeChooserPanel;
    private Button clearButton;
    private Label thicknessIndicator;
    private final BorderPane root;
    private MenuBar menuBar;

    // Icon for Dark Mode
    Image moon = new Image(Objects.requireNonNull(getClass().getResourceAsStream(
            "/icons/moon.png")));

    /**
     * Initializes the main UI components, sets up the layout, and links the CSS stylesheet.
     * @param model The application's PaintModel.
     * @param stage The primary stage for the application.
     */
    public View(PaintModel model, Stage stage) {
        this.paintModel = model;

        this.paintPanel = new PaintPanel(this.paintModel);
        this.shapeChooserPanel = new ShapeChooserPanel(this, this.paintModel);
        this.shapeChooserPanel.setId("shape-chooser-panel"); // ID for CSS styling


        root = new BorderPane();
        root.setId("main-container"); // ID for CSS styling
        root.setTop(createTopBox());
        root.setCenter(this.paintPanel);
        root.setLeft(this.shapeChooserPanel);
        root.getStyleClass().add("dark-mode"); // Start in dark-mode by default

        Scene scene = new Scene(root);

        // Safely load the CSS stylesheet
        try {
            String cssPath = getClass().getResource("/style.css").toExternalForm();
            scene.getStylesheets().add(cssPath);
        } catch (NullPointerException e) {
            System.err.println("ERROR: Could not find 'style.css'. Make sure it's at the root of your resources folder.");
        }

        stage.setScene(scene);
        stage.setTitle("Paint");
        stage.show();
    }

    public PaintModel getPaintModel() {
        return this.paintModel;
    }

    /**
     * Passes the selected tool or style command (e.g., "Circle", "Fill") to the PaintPanel.
     * @param mode The command string from the ShapeChooserPanel.
     */
    public void setMode(String mode){
        this.paintPanel.setMode(mode);



    }
    /**
     * Updates the 'Thickness' label in the UI with the current value from the model.
     */
    public void updateIndicator(){
        thicknessIndicator.setText("Thickness: " + this.paintModel.getLineWidth());
    }

    /**
     * Private helper method to create and configure the main MenuBar.
     * @return The fully constructed MenuBar.
     */
    private MenuBar createMenuBar() {

        menuBar = new MenuBar();
        Menu menu;
        MenuItem menuItem;

        // A menu for File

        menu = new Menu("File");

        menuItem = new MenuItem("New");
        menuItem.setOnAction(this);
        menu.getItems().add(menuItem);

        menuItem = new MenuItem("Open");
        menuItem.setOnAction(this);
        menu.getItems().add(menuItem);

        menuItem = new MenuItem("Save");
        menuItem.setOnAction(this);
        menu.getItems().add(menuItem);

        menu.getItems().add(new SeparatorMenuItem());

        menuItem = new MenuItem("Exit");
        menuItem.setOnAction(this);
        menu.getItems().add(menuItem);

        menuBar.getMenus().add(menu);

        // Another menu for Edit

        menu = new Menu("Edit");

        menuItem = new MenuItem("Cut");
        menuItem.setOnAction(this);
        menu.getItems().add(menuItem);

        menuItem = new MenuItem("Copy");
        menuItem.setOnAction(this);
        menu.getItems().add(menuItem);

        menuItem = new MenuItem("Paste");
        menuItem.setOnAction(this);
        menu.getItems().add(menuItem);

        menu.getItems().add(new SeparatorMenuItem());
        menuItem = new MenuItem("Undo");
        menuItem.setOnAction(this);
        menu.getItems().add(menuItem);

        menuItem = new MenuItem("Redo");
        menuItem.setOnAction(this);
        menu.getItems().add(menuItem);

        menuBar.getMenus().add(menu);


        // menu for adding line thickness selector
        menu = new Menu("Thickness");
        menuItem = new MenuItem("1");
        menuItem.setOnAction(this);
        menu.getItems().add(menuItem);
        menuItem = new MenuItem("2");
        menuItem.setOnAction(this);
        menu.getItems().add(menuItem);
        menuItem = new MenuItem("3");
        menuItem.setOnAction(this);
        menu.getItems().add(menuItem);
        menuItem = new MenuItem("4");
        menuItem.setOnAction(this);
        menu.getItems().add(menuItem);
        menuItem = new MenuItem("5");
        menuItem.setOnAction(this);
        menu.getItems().add(menuItem);
        menuItem = new MenuItem("6");
        menuItem.setOnAction(this);
        menu.getItems().add(menuItem);
        menuItem = new MenuItem("7");
        menuItem.setOnAction(this);
        menu.getItems().add(menuItem);
        menuItem = new MenuItem("8");
        menuItem.setOnAction(this);
        menu.getItems().add(menuItem);
        menuItem = new MenuItem("9");
        menuItem.setOnAction(this);
        menu.getItems().add(menuItem);

        menuBar.getMenus().add(menu);

        // Menu for theme selection
        menu = new Menu("Theme");
        menuItem = new MenuItem("Light");
        menuItem.setOnAction(this);
        menu.getItems().add(menuItem);
        menuItem = new MenuItem("Dark");
        menuItem.setOnAction(this);
        menu.getItems().add(menuItem);

        menuBar.getMenus().add(menu);

        return menuBar;

    }

    /**
     * Private helper method to create the VBox for the top region of the UI.
     * @return The fully constructed VBox containing the menu and other controls.
     */
    private VBox createTopBox() {
        MenuBar menuBar = createMenuBar();

        clearButton = new Button("Clear");
        clearButton.setOnAction(this);
        thicknessIndicator = new Label();
        updateIndicator(); // keep it updated and set text

        VBox vBox2 = new VBox(clearButton, thicknessIndicator);
        VBox vBox = new VBox(menuBar, vBox2);
        vBox.setId("top-box"); // ID for CSS styling
        return vBox;

    }

    /**
     * Handles all ActionEvents from MenuItems and the Clear button.
     * @param event The action event.
     */
    @Override
    public void handle(ActionEvent event) {
        Object source = event.getSource();

        // Handle the clear button separately
        if (source == clearButton){
            paintModel.clearShapes();
            return;
        }

        // All other events must be from MenuItems, so it's safe to cast
        String command = ((MenuItem) event.getSource()).getText();
        System.out.println(command);

        if (command.equals("Exit")) {
            Platform.exit();
        }
        // --- NEW THEME LOGIC ---
        if (command.equals("Dark")) {
            // Add the "dark-mode" class to the root BorderPane
            if (!root.getStyleClass().contains("dark-mode")) {
                root.getStyleClass().add("dark-mode");
            }
            return; // Stop here
        }

        if (command.equals("Light")) {
            // Remove the "dark-mode" class from the root
            root.getStyleClass().remove("dark-mode");
            return; // Stop here
        }
        // --- END NEW THEME LOGIC ---

        if (command.equals("1") || command.equals("2") || command.equals("3")
                || command.equals("4") || command.equals("5")  || command.equals("6")
                || command.equals("7") || command.equals("8") || command.equals("9")) {
            this.paintModel.setLineWidth(Integer.parseInt(command));
            updateIndicator(); // update indicator as you press thickness number in menu
        }

    }

}