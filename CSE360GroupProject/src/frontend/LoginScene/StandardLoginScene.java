package frontend.LoginScene;

import backend.UserManager;
import frontend.ErrorScene;
import frontend.RoleSelectionScene;
import frontend.SetupScene;
import frontend.LoginScene.InvitationCodeLoginScene;
import backend.User;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StandardLoginScene {
    private Stage primaryStage;
    private UserManager userManager;

    public StandardLoginScene(Stage primaryStage, UserManager userManager) {
        this.primaryStage = primaryStage;
        this.userManager = userManager;
    }

    public Scene createLoginFields() {
        VBox loginVBox = new VBox(10);
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");
        Button invitationCodeSceneButton = new Button("Login with Invitation Code");

        loginButton.setOnAction(e -> handleLogin(usernameField.getText(), passwordField.getText()));
        invitationCodeSceneButton.setOnAction(e -> primaryStage.setScene(new InvitationCodeLoginScene(primaryStage, userManager).createInvitationCodeLoginScene()));

        loginVBox.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField, loginButton, invitationCodeSceneButton);
        primaryStage.setTitle("Login");

        return new Scene(loginVBox, 300, 200);
    }

    private void handleLogin(String username, String password) {
        User user = userManager.login(username, password);
        if (user != null) {
            if (!user.isSetupComplete()) {
                primaryStage.setScene(new SetupScene(user, userManager, primaryStage).createSetupScene());
            } else {
                new RoleSelectionScene(primaryStage, userManager).handleRoleSelection(user);
            }
        } else {
            new ErrorScene(primaryStage).showError("Invalid credentials. Please try again.");
        }
    }
}
