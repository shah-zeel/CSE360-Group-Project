package frontend;

import backend.*;
import frontend.LoginScene.LoginScene;
import javafx.animation.PauseTransition;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * <p> Title: CreateAdminScene Class. </p>
 * 
 * <p> Description: A class that provides the user interface and logic for creating an admin account. 
 * It handles input validation, password confirmation, and interacts with the AuthManager to create the first admin user. 
 * Upon successful admin creation, the user is redirected to the login screen. </p>
 * 
 * @author Zeel Tejashkumar Shah
 * 
 * @version 1.0 2024-10-09 Initial implementation
 */

public class CreateAdminScene {
    private AuthManager authManager; // Manages user authentication
    private Stage primaryStage; // Main application stage

    /**
     * Constructor for CreateAdminScene.
     *
     * @param primaryStage the main application stage
     * @param authManager  the AuthManager for user authentication
     */
    public CreateAdminScene(Stage primaryStage, AuthManager authManager) {
        this.authManager = authManager;
        this.primaryStage = primaryStage;
    }

    /**
     * Creates the admin account scene with input fields for username and password.
     *
     * @return Scene representing the admin account creation UI
     */
    public Scene createAdminScene() {
        VBox adminVBox = new VBox(10); // Vertical layout with 10px spacing
        Label usernameLabel = new Label("Username:"); // Username label
        TextField usernameField = new TextField(); // Input field for username
        Label passwordLabel = new Label("Password:"); // Password label
        PasswordField passwordField = new PasswordField(); // Input field for password
        Label confirmPasswordLabel = new Label("Confirm Password:"); // Confirm password label
        PasswordField confirmPasswordField = new PasswordField(); // Input field for confirming password
        Button createAdminButton = new Button("Create Admin Account"); // Button to create admin account

        // Add UI components to the layout
        adminVBox.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField, 
                confirmPasswordLabel, confirmPasswordField, createAdminButton);

        // Set action for the create admin button
        createAdminButton.setOnAction(e -> handleCreateAdmin(usernameField.getText(), passwordField.getText(),
                confirmPasswordField.getText(), adminVBox));

        primaryStage.setTitle("Create Admin Account"); // Set title for admin creation
        return new Scene(adminVBox, 300, 300); // Return the admin scene
    }

    /**
     * Handles the creation of an admin account.
     *
     * @param username        the username for the admin account
     * @param password        the password for the admin account
     * @param confirmPassword the password confirmation
     * @param adminVBox       the VBox containing UI elements for the admin scene
     */
    private void handleCreateAdmin(String username, String password, String confirmPassword, VBox adminVBox) {
        // Clear previous error messages
        adminVBox.getChildren().removeIf(node -> node instanceof Label && ((Label) node).getText().startsWith("Error:"));

        // Validate username
        if (username == null || username.isEmpty()) {
            showError("Error: Username cannot be empty.", adminVBox);
            return; // Exit the method if username is invalid
        }

        // Validate password
        if (password == null || password.isEmpty()) {
            showError("Error: Password cannot be empty.", adminVBox);
            return; // Exit the method if password is invalid
        }

        // Validate confirm password
        if (confirmPassword == null || confirmPassword.isEmpty()) {
            showError("Error: Confirm Password cannot be empty.", adminVBox);
            return; // Exit the method if confirm password is invalid
        }

        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            showError("Error: Passwords do not match. Please try again.", adminVBox);
            return; // Exit the method if passwords do not match
        }

        // Proceed to create the first user as an admin
        User admin = authManager.createFirstUser(username, password);

        if (admin != null) {
            // Display confirmation message on the screen
            Label confirmationLabel = new Label("Admin account created successfully. Please log in.");
            adminVBox.getChildren().add(confirmationLabel);

            // Pause for a few seconds before redirecting
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(event -> redirectToLogin(adminVBox));
            pause.play();
        }
    }

    /**
     * Redirects to the login scene after successful admin creation.
     *
     * @param adminVBox the VBox containing UI elements for the admin scene
     */
    private void redirectToLogin(VBox adminVBox) {
        // Clear previous confirmation messages
        adminVBox.getChildren().removeIf(node -> node instanceof Label
                && ((Label) node).getText().contains("Admin account created successfully"));

        // Reset the input fields for a new login attempt
        for (Node node : adminVBox.getChildren()) {
            if (node instanceof TextField) {
                ((TextField) node).clear(); // Clear text fields
            }
        }

        // Switch back to the login scene
        primaryStage.setScene(new LoginScene(primaryStage, authManager).createLoginScene());
    }

    /**
     * Displays an error message in the admin scene.
     *
     * @param message   the error message to display
     * @param adminVBox the VBox containing UI elements for the admin scene
     */
    private void showError(String message, VBox adminVBox) {
        Label errorLabel = new Label(message); // Create label for the error message
        errorLabel.setStyle("-fx-text-fill: red;"); // Set text color to red
        adminVBox.getChildren().add(errorLabel); // Add error message to the layout
    }
}
