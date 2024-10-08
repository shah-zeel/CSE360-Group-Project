package frontend;

import backend.User;
import frontend.LoginScene.LoginScene;
import backend.AuthManager;
import javafx.animation.PauseTransition;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CreateAdminScene {
    private AuthManager authManager;
    private Stage primaryStage;

    public CreateAdminScene(Stage primaryStage, AuthManager authManager) {
        this.authManager = authManager;
        this.primaryStage = primaryStage;
    }

    public Scene createAdminScene() {
        VBox adminVBox = new VBox(10);
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        Label confirmPasswordLabel = new Label("Confirm Password:");
        PasswordField confirmPasswordField = new PasswordField();
        Button createAdminButton = new Button("Create Admin Account");

        adminVBox.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField, confirmPasswordLabel,
                confirmPasswordField, createAdminButton);

        createAdminButton.setOnAction(e -> handleCreateAdmin(usernameField.getText(), passwordField.getText(),
                confirmPasswordField.getText(), adminVBox));

        primaryStage.setTitle("Create Admin Account"); // Set title for admin creation
        return new Scene(adminVBox, 300, 300); // Return the admin scene
    }

    private void handleCreateAdmin(String username, String password, String confirmPassword, VBox adminVBox) {
        // Clear previous error messages
        adminVBox.getChildren().removeIf(node -> node instanceof Label && ((Label) node).getText().startsWith("Error:"));

        // Check for null or empty username and passwords
        if (username == null || username.isEmpty()) {
            showError("Error: Username cannot be empty.", adminVBox);
            return; // Exit the method if username is invalid
        }

        if (password == null || password.isEmpty()) {
            showError("Error: Password cannot be empty.", adminVBox);
            return; // Exit the method if password is invalid
        }

        if (confirmPassword == null || confirmPassword.isEmpty()) {
            showError("Error: Confirm Password cannot be empty.", adminVBox);
            return; // Exit the method if confirm password is invalid
        }

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

    private void redirectToLogin(VBox adminVBox) {
        // Clear previous confirmation messages
        adminVBox.getChildren().removeIf(node -> node instanceof Label
                && ((Label) node).getText().contains("Admin account created successfully"));

        // Reset the input fields for a new login attempt
        for (Node node : adminVBox.getChildren()) {
            if (node instanceof TextField) {
                ((TextField) node).clear();
            }
        }

        // Switch back to the login scene
        primaryStage.setScene(new LoginScene(primaryStage, authManager).createLoginScene());
    }

    private void showError(String message, VBox adminVBox) {
        Label errorLabel = new Label(message);
        errorLabel.setStyle("-fx-text-fill: red;");
        adminVBox.getChildren().add(errorLabel);
    }
}
