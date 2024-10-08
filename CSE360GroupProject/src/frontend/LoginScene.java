package frontend;

import backend.User;
import backend.UserManager;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginScene {
    private UserManager userManager;

    public LoginScene(UserManager userManager) {
        this.userManager = userManager;
    }

    public Scene createLoginScene(Stage primaryStage) {
        VBox loginVBox = new VBox(10);

        // Check if any users exist
        if (userManager.getUsers().isEmpty()) {
            return new AdminScene(userManager, primaryStage).createAdminScene(); // Create admin fields and set scene
        } else {
            return createLoginFields(primaryStage); // Create login fields and set scene
        }
    }

    private Scene createLoginFields(Stage primaryStage) {
        VBox loginVBox = new VBox(10);
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");

        loginVBox.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField, loginButton);

        // Set action for the login button
        loginButton.setOnAction(e -> handleLogin(usernameField.getText(), passwordField.getText(), primaryStage));
        primaryStage.setTitle("Login");
        return new Scene(loginVBox, 300, 200); // Return the login scene
    }

    private void handleLogin(String username, String password, Stage primaryStage) {
        User loggedInUser = userManager.login(username, password);

        if (loggedInUser != null) {
            if (!loggedInUser.isSetupComplete()) {
                primaryStage.setScene(new SetupScene(loggedInUser, userManager, primaryStage).createSetupScene()); // Pass the logged-in user
            } else {
                primaryStage.setScene(new HomeScene(primaryStage, userManager).createHomeScene()); // Proceed to home page
            }
        } else {
            showError("Invalid username or password");
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.showAndWait();
    }
}
