package frontend.AdminScene;

import backend.AuthManager;
import frontend.HomeScene.AdminHomeScene;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CreatePasswordResetRequestScene {
    private Stage primaryStage;
    private AuthManager authManager;

    public CreatePasswordResetRequestScene(Stage primaryStage, AuthManager authManager) {
        this.primaryStage = primaryStage;
        this.authManager = authManager;
    }

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
