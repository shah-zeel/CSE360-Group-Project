package frontend.HomeScene;

import backend.AuthManager;
import frontend.LoginScene.LoginScene;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * <p> Title: StudentHomeScene Class. </p>
 * 
 * <p> Description: This class represents the home page for students after they have logged in. 
 * It provides a welcome message and a logout option to navigate back to the login scene. </p>
 * 
 * @author Zeel Tejashkumar Shah
 * 
 * @version 1.0 2024-10-09 Initial implementation
 */

public class StudentHomeScene {
    private Stage primaryStage;
    private AuthManager authManager;

    /**
     * Constructor for the StudentHomeScene class.
     *
     * @param primaryStage The main stage of the application.
     * @param authManager  The authentication manager to handle user authentication.
     */
    public StudentHomeScene(Stage primaryStage, AuthManager authManager) {
        this.primaryStage = primaryStage;
        this.authManager = authManager; // Store the authManager
    }

    /**
     * Creates and returns the student's home scene, with a welcome message 
     * and a logout button.
     *
     * @return The student's home scene.
     */
    public Scene createStudentHomeScene() {
        VBox homeVBox = new VBox(10);
        homeVBox.setPrefSize(300, 200);
        
        Label welcomeLabel = new Label("Welcome to the Student Home Page!");
        Button logoutButton = new Button("Logout");

        // Adding action for the logout button
        logoutButton.setOnAction(e -> handleLogout());

        homeVBox.getChildren().addAll(welcomeLabel, logoutButton);
        primaryStage.setTitle("Home");
        return new Scene(homeVBox, 300, 200); // Return the home scene
    }

    /**
     * Handles the logout process by navigating the user back to the login scene.
     */
    private void handleLogout() {
        // Navigate back to the login scene
        LoginScene loginScene = new LoginScene(primaryStage, authManager); // You may need to pass the authManager
        primaryStage.setScene(loginScene.createLoginScene()); // Switch back to the login scene
    }
}