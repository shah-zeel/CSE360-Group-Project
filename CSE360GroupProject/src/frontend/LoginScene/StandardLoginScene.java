package frontend.LoginScene;

import backend.AuthManager;
import frontend.ErrorScene;
import frontend.RoleSelectionScene;
import frontend.SetupScene;
import backend.User;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * <p> Title: StandardLoginScene Class. </p>
 * 
 * <p> Description: A class that provides the standard login interface for users. 
 * It allows users to input their credentials, provides options for first-time users and password resets, 
 * and manages the login process, including redirection based on user setup completion. </p>
 * 
 * @author Zeel Tejashkumar Shah
 * 
 * @version 1.0 2024-10-09 Initial implementation
 */

public class StandardLoginScene {
    private Stage primaryStage;
    private AuthManager authManager;

    /**
     * Constructor for the StandardLoginScene class.
     *
     * @param primaryStage The primary stage where the scene will be displayed.
     * @param authManager The authentication manager handling user login.
     */
    public StandardLoginScene(Stage primaryStage, AuthManager authManager) {
        this.primaryStage = primaryStage;
        this.authManager = authManager;
    }

    /**
     * Creates and returns the standard login fields scene.
     * Users will input their username and password, and have options for invitation code login and password reset.
     *
     * @return The scene containing login fields.
     */
    public Scene createLoginFields() {
        VBox loginVBox = new VBox(10);
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");
        Button invitationCodeSceneButton = new Button("First Time User? Login with Invitation Code");
        Button passwordResetSceneButton = new Button("Forgot Password? Login with OTP");

        // Define the action when the "Login" button is clicked
        loginButton.setOnAction(e -> handleLogin(usernameField.getText(), passwordField.getText()));
        invitationCodeSceneButton.setOnAction(e -> primaryStage.setScene(new LoginWithInvitationCodeScene(primaryStage, authManager).createInvitationCodeLoginScene()));
        passwordResetSceneButton.setOnAction(e -> primaryStage.setScene(new PasswordResetScene(primaryStage, authManager).createPasswordResetScene()));

        loginVBox.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField, loginButton, invitationCodeSceneButton, passwordResetSceneButton);
        primaryStage.setTitle("Login");

        return new Scene(loginVBox, 300, 400);
    }

    /**
     * Handles the login process when the user attempts to log in with their credentials.
     * It checks the validity of the credentials and redirects the user based on their setup completion status.
     *
     * @param username The username input by the user.
     * @param password The password input by the user.
     */
    private void handleLogin(String username, String password) {
        User user = authManager.login(username, password);
        if (user != null) {
            if (!user.isSetupComplete()) {
                primaryStage.setScene(new SetupScene(user, authManager, primaryStage).createSetupScene());
            } else {
                new RoleSelectionScene(primaryStage, authManager).handleRoleSelection(user);
            }
        } else {
            new ErrorScene().showError("Invalid credentials. Please try again.");
        }
    }
}
