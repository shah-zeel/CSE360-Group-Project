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

public class StandardLoginScene {
    private Stage primaryStage;
    private AuthManager authManager;

    public StandardLoginScene(Stage primaryStage, AuthManager authManager) {
        this.primaryStage = primaryStage;
        this.authManager = authManager;
    }

    public Scene createLoginFields() {
        VBox loginVBox = new VBox(10);
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");
        Button invitationCodeSceneButton = new Button("First Time User? Login with Invitation Code");
        Button passwordResetSceneButton = new Button("Forgot Password? Login with OTP");

        loginButton.setOnAction(e -> handleLogin(usernameField.getText(), passwordField.getText()));
        invitationCodeSceneButton.setOnAction(e -> primaryStage.setScene(new LoginWithInvitationCodeScene(primaryStage, authManager).createInvitationCodeLoginScene()));
        passwordResetSceneButton.setOnAction(e -> primaryStage.setScene(new PasswordResetScene(primaryStage, authManager).createPasswordResetScene()));

        loginVBox.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField, loginButton, invitationCodeSceneButton, passwordResetSceneButton);
        primaryStage.setTitle("Login");

        return new Scene(loginVBox, 300, 400);
    }

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
