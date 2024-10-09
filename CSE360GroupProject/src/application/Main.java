package application;

import backend.AuthManager;
import frontend.LoginScene.LoginScene;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * <p> Title: Main Class for the Application </p>
 * 
 * <p> Description: This is the main entry point of the application that initializes and launches the JavaFX interface.</p>
 * 
 * @author Zeel Tejashkumar Shah
 * 
 * @version 1.0 2404-10-08 Initial version with basic application setup.
 */
public class Main extends Application {
    
    /**
     * <p> This method is called when the application is started. It sets up the primary stage and initializes the login scene. </p>
     * 
     * @param primaryStage The primary stage for this application, onto which the application scene can be set.
     */
    @Override
    public void start(Stage primaryStage) {
        AuthManager authManager = new AuthManager(); // Create a user manager
        
        // Create the login scene
        LoginScene loginScene = new LoginScene(primaryStage, authManager);
        Scene scene = loginScene.createLoginScene(); // Create the login scene

        primaryStage.setScene(scene); // Set the scene for the primary stage
        primaryStage.show(); // Display the primary stage
    }

    /**
     * <p> The main method that serves as the entry point for the JavaFX application. </p>
     * 
     * @param args Command line arguments passed to the application.
     */
    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application
    }
}
