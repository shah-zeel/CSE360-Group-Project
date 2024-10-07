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
        primaryStage.setTitle("Login Page"); // Set initial title
        primaryStage.setScene(createLoginScene(primaryStage));
        primaryStage.show();
    }

    // Scene Creation Methods
    private Scene createLoginScene(Stage primaryStage) {
        VBox loginVBox = new VBox(10);

        // Check if any users exist
        if (userManager.getUsers().isEmpty()) {
            return createAdminFields(primaryStage); // Create admin fields and set scene
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

        return new Scene(loginVBox, 300, 200); // Return the login scene
    }

    private Scene createAdminFields(Stage primaryStage) {
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

        // Set action for creating the first user as admin
        createAdminButton.setOnAction(e -> handleCreateAdmin(usernameField.getText(), passwordField.getText(),
                confirmPasswordField.getText(), adminVBox, primaryStage));

        primaryStage.setTitle("Create Admin Account"); // Set title for admin creation
        return new Scene(adminVBox, 300, 300); // Return the admin scene
    }

    private Scene createSetupAccountScene(User user, Stage primaryStage) {
        VBox setupVBox = new VBox(10);
        setupVBox.setStyle("-fx-padding: 20;");

        // Create fields for user details
        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        Label firstNameLabel = new Label("First Name:");
        TextField firstNameField = new TextField();
        Label middleNameLabel = new Label("Middle Name (optional):");
        TextField middleNameField = new TextField();
        Label lastNameLabel = new Label("Last Name:");
        TextField lastNameField = new TextField();
        Label preferredNameLabel = new Label("Preferred First Name (optional):");
        TextField preferredNameField = new TextField();
        Button finishSetupButton = new Button("Finish Setup");

        setupVBox.getChildren().addAll(emailLabel, emailField, firstNameLabel, firstNameField, 
                                         middleNameLabel, middleNameField, lastNameLabel, lastNameField, 
                                         preferredNameLabel, preferredNameField, finishSetupButton);

        // Set action for the finish setup button
        finishSetupButton.setOnAction(e -> {
            String email = emailField.getText();
            String firstName = firstNameField.getText();
            String middleName = middleNameField.getText();
            String lastName = lastNameField.getText();
            String preferredName = preferredNameField.getText();

            // Call the handleFinishSetup method with the currently logged-in user
            handleFinishSetup(email, firstName, middleName, lastName, preferredName, user, primaryStage);
        });

        return new Scene(setupVBox, 400, 500);
    }

    private Scene createHomeScene(Stage primaryStage) {
        VBox homeVBox = new VBox(10);
        Label welcomeLabel = new Label("Welcome to the Home Page!");
        Button logoutButton = new Button("Logout");

        // Set action for the logout button (return to login page)
        logoutButton.setOnAction(e -> {
            primaryStage.setScene(createLoginScene(primaryStage));
            primaryStage.setTitle("Login Page"); // Set title for login page
        });

        homeVBox.getChildren().addAll(welcomeLabel, logoutButton);
        return new Scene(homeVBox, 300, 200); // Return the home scene
    }

    // Event Handling Methods
    private void handleLogin(String username, String password, Stage primaryStage) {
        // Attempt to log in
        User loggedInUser = userManager.login(username, password);

        if (loggedInUser != null) {
            if (!loggedInUser.isSetupComplete()) {
                primaryStage.setScene(createSetupAccountScene(loggedInUser, primaryStage)); // Pass the logged-in user
            } else {
                primaryStage.setScene(createHomeScene(primaryStage)); // Proceed to home page
            }
        } else {
            showError("Invalid username or password");
        }
    }

    private void handleFinishSetup(String email, String firstName, String middleName, String lastName,
                                    String preferredName, User user, Stage primaryStage) {
        // Validate user input
        if (email.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
            showError("Email, First Name, and Last Name are required.");
            return;
        }

        // Call the completeAccountSetup method
        boolean isCompleted = userManager.completeAccountSetup(user, firstName, middleName, lastName, preferredName, email);

        if (isCompleted) {
            // Redirect to home page after setup is complete
            primaryStage.setScene(createHomeScene(primaryStage));
        } else {
            showError("Failed to complete account setup. Please try again.");
        }
    }

    private void handleCreateAdmin(String username, String password, String confirmPassword, VBox adminVBox,
                                    Stage primaryStage) {
        // Clear previous error messages
        adminVBox.getChildren().removeIf(node -> node instanceof Label && ((Label) node).getText().startsWith("Error:"));

        // Check for null or empty username and passwords
        if (username == null || username.isEmpty()) {
            Label errorLabel = new Label("Error: Username cannot be empty.");
            adminVBox.getChildren().add(errorLabel);
            return; // Exit the method if username is invalid
        }

        if (password == null || password.isEmpty()) {
            Label errorLabel = new Label("Error: Password cannot be empty.");
            adminVBox.getChildren().add(errorLabel);
            return; // Exit the method if password is invalid
        }

        if (confirmPassword == null || confirmPassword.isEmpty()) {
            Label errorLabel = new Label("Error: Confirm Password cannot be empty.");
            adminVBox.getChildren().add(errorLabel);
            return; // Exit the method if confirm password is invalid
        }

        if (!password.equals(confirmPassword)) {
            Label errorLabel = new Label("Error: Passwords do not match. Please try again.");
            adminVBox.getChildren().add(errorLabel);
            return; // Exit the method if passwords do not match
        }

        // Proceed to create the first user as an admin
        User admin = userManager.createFirstUser(username, password);

        if (admin != null) {
            // Display confirmation message on the screen
            Label confirmationLabel = new Label("Admin account created successfully. Please log in.");
            adminVBox.getChildren().add(confirmationLabel);

            // Pause for a few seconds before redirecting
            PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
            pause.setOnFinished(event -> redirectToLogin(adminVBox, primaryStage));
            pause.play();
        }
    }

    private void redirectToLogin(VBox adminVBox, Stage primaryStage) {
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
        primaryStage.setScene(createLoginScene(primaryStage));
        primaryStage.setTitle("Login Page"); // Set title for login page
    }

    // Utility Methods
    private void showError(String message) {
        Label errorLabel = new Label("Error: " + message);
        errorLabel.setStyle("-fx-text-fill: red;");
        // You can show this error label in the setup scene as needed
    }

    private void showAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType, message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}