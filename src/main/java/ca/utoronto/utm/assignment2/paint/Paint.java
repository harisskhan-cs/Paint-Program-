package ca.utoronto.utm.assignment2.paint;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This is the main class for the Paint application.
 * It sets up the Model, View, and Controller.
 */
public class Paint extends Application {

    PaintModel model; // Model
    View view; // View + Controller

    /**
     * The main entry point for the application.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Initializes and starts the JavaFX application.
     * @param stage The primary stage for this application.
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {

        // Model
        this.model = new PaintModel();

        // View + Controller
        this.view = new View(model, stage);
    }
}