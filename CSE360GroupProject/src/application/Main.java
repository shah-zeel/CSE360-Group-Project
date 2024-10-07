package application;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    private UserManager userManager = new UserManager(); // Instance of UserManager to manage users

    @Override
    public void start(Stage primaryStage) {
        Scene scene = createLoginScene(primaryStage);
        primaryStage.setTitle("Login Page");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Scene createLoginScene(Stage primaryStage) {
        VBox loginVBox = new VBox(10);

        // Check if any users exist
        if (userManager.getUsers().isEmpty()) {
            // Create fields for creating admin account
            createAdminFields(loginVBox, primaryStage);
        } else {
            // Create fields for user login
            createLoginFields(loginVBox, primaryStage);
        }

        return new Scene(loginVBox, 300, 200);
    }

    private void createLoginFields(VBox loginVBox, Stage primaryStage) {
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");

        loginVBox.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField, loginButton);

        // Set action for the login button
        loginButton.setOnAction(e -> handleLogin(usernameField.getText(), passwordField.getText(), primaryStage));
    }

    private void createAdminFields(VBox loginVBox, Stage primaryStage) {
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        Button createAdminButton = new Button("Create Admin Account");

        loginVBox.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField, createAdminButton);

        // Set action for creating the first user as admin
        createAdminButton.setOnAction(e -> handleCreateAdmin(usernameField.getText(), passwordField.getText(), loginVBox));
    }

    private void handleLogin(String username, String password, Stage primaryStage) {
        // Attempt to log in
        User loggedInUser = userManager.login(username, password);

        if (loggedInUser != null) {
            // If login is successful, change the scene to the home page
            primaryStage.setScene(createHomeScene(primaryStage));
        } else {
            // Show an alert if login fails
            showAlert("Invalid username or password", Alert.AlertType.ERROR);
        }
    }

    private Scene createHomeScene(Stage primaryStage) {
        Label welcomeLabel = new Label("Welcome to the Home Page!");
        Button logoutButton = new Button("Logout");

        // Set action for the logout button (return to login page)
        logoutButton.setOnAction(e -> primaryStage.setScene(createLoginScene(primaryStage)));

        VBox homeVBox = new VBox(10, welcomeLabel, logoutButton);
        return new Scene(homeVBox, 300, 200);
    }

    private void handleCreateAdmin(String username, String password, VBox loginVBox) {
        if (userManager.getUsers().isEmpty()) {
            // Create the first user as an admin
            User admin = userManager.createFirstUser(username, password);

            if (admin != null) {
                // Display confirmation message on the screen
                Label confirmationLabel = new Label("Admin account created successfully. Please log in.");
                loginVBox.getChildren().add(confirmationLabel);

                // Pause for a few seconds before redirecting
                PauseTransition pause = new PauseTransition(Duration.seconds(3));
                pause.setOnFinished(event -> redirectToLogin(loginVBox));
                pause.play();
            }
        }
    }

    private void redirectToLogin(VBox loginVBox) {
        // Clear previous confirmation messages
        loginVBox.getChildren().removeIf(node -> node instanceof Label && ((Label) node).getText().contains("Admin account created successfully"));

        // Reset the input fields for a new login attempt
        for (Node node : loginVBox.getChildren()) {
            if (node instanceof TextField) {
                ((TextField) node).clear();
            }
        }

        // Recreate the login scene
        loginVBox.getChildren().clear();
        createLoginFields(loginVBox, (Stage) loginVBox.getScene().getWindow());
    }

    private void showAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType, message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
