package frontend.HomeScene.AdminTasks;

import backend.AuthManager;
import frontend.HomeScene.AdminHomeScene;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * <p> Title: CreatePasswordResetScene Class. </p>
 * 
 * <p> Description: This class provides a scene where an admin can request a password reset 
 * for a user by entering the user's email. The system checks if the user exists before processing 
 * the reset request. Upon successful submission, the system navigates back to the admin home scene. </p>
 * 
 * @author Zeel Tejashkumar Shah
 * 
 * @version 1.0 2024-10-09 Initial implementation
 */

public class CreatePasswordResetScene {
    private Stage primaryStage;
    private AuthManager authManager;

    /**
     * Constructor for the CreatePasswordResetScene class.
     *
     * @param primaryStage The main stage of the application.
     * @param authManager  The authentication manager for managing user authentication.
     */
    public CreatePasswordResetScene(Stage primaryStage, AuthManager authManager) {
        this.primaryStage = primaryStage;
        this.authManager = authManager;
    }

    /**
     * Creates and returns a scene for password reset requests. Admins can enter a user's email
     * to request a password reset. If the email is valid, a request is sent.
     *
     * @return The password reset request scene.
     */
    public Scene createResetRequestScene() {
        VBox layout = new VBox(10);

        Label emailLabel = new Label("Enter user email for password reset:");
        TextField emailField = new TextField();

        Button submitButton = new Button("Submit Request");
        Button backButton = new Button("Back");

        // Action for the submit button
        submitButton.setOnAction(e -> {
            String email = emailField.getText();
            
            if (email.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Email cannot be empty.");
                alert.showAndWait();
            } else if (!authManager.userExistsForEmail(email)) { // Check if the user exists
                Alert alert = new Alert(Alert.AlertType.ERROR, "No user found with the provided email.");
                alert.showAndWait();
            } else {
                // Request a password reset for the entered email
                authManager.requestPasswordReset(email);
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "Password reset request created successfully.");
                successAlert.showAndWait();

                // Clear the email field after successful request
                emailField.clear();
            }
        });

        // Action for the back button
        backButton.setOnAction(e -> {
            // Navigate back to the admin home scene
            AdminHomeScene adminHome = new AdminHomeScene(primaryStage, authManager);
            primaryStage.setScene(adminHome.createAdminHomeScene());
        });

        layout.getChildren().addAll(emailLabel, emailField, submitButton, backButton);
        return new Scene(layout, 300, 200);
    }
}
