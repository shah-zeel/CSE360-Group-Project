package application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginPage extends Application {
    private UserManager userManager = new UserManager();  // Handle user-related operations

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Login Page");

        // GridPane for form layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        // Create the Username label and field
        Label usernameLabel = new Label("Username:");
        grid.add(usernameLabel, 0, 1);
        TextField usernameField = new TextField();
        grid.add(usernameField, 1, 1);

        // Create the Password label and field
        Label passwordLabel = new Label("Password:");
        grid.add(passwordLabel, 0, 2);
        PasswordField passwordField = new PasswordField();
        grid.add(passwordField, 1, 2);

        // One-time invitation code label and field (for invited users)
        Label inviteCodeLabel = new Label("Invitation Code:");
        grid.add(inviteCodeLabel, 0, 3);
        TextField inviteCodeField = new TextField();
        grid.add(inviteCodeField, 1, 3);

        // Create Login and Create Account buttons
        Button loginButton = new Button("Login");
        grid.add(loginButton, 1, 4);

        // Check if it's the first user (admin)
        if (userManager.getUsers().isEmpty()) {
            Label firstUserLabel = new Label("First User: Create Admin Account");
            grid.add(firstUserLabel, 1, 0);

            // Button to create admin
            Button createAdminButton = new Button("Create Admin");
            grid.add(createAdminButton, 1, 5);

            // Handle Create Admin button click
            createAdminButton.setOnAction(event -> {
                String username = usernameField.getText();
                String password = passwordField.getText();

                if (!username.isEmpty() && !password.isEmpty()) {
                    User admin = userManager.createFirstUser(username, password);
                    showAlert(Alert.AlertType.INFORMATION, primaryStage, "Admin Created", "Admin account created successfully!");
                    resetFields(usernameField, passwordField, inviteCodeField);
                } else {
                    showAlert(Alert.AlertType.ERROR, primaryStage, "Error", "Username and password must not be empty.");
                }
            });
        }

        // Handle login button click
        loginButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (userManager.getUsers().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, primaryStage, "No Users", "No users found. Please create an admin.");
            } else {
                User loggedInUser = userManager.login(username, password);
                if (loggedInUser != null) {
                    showAlert(Alert.AlertType.INFORMATION, primaryStage, "Login Successful", "Welcome, " + loggedInUser.getFirstName());
                } else {
                    showAlert(Alert.AlertType.ERROR, primaryStage, "Login Failed", "Invalid username or password.");
                }
            }
        });

        // Create the scene and show the stage
        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Helper method to show alerts
    private void showAlert(Alert.AlertType alertType, Stage owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    // Helper method to reset fields after actions
    private void resetFields(TextField usernameField, PasswordField passwordField, TextField inviteCodeField) {
        usernameField.clear();
        passwordField.clear();
        inviteCodeField.clear();
    }

    public static void main(String[] args) {
        launch(args);
    }
}