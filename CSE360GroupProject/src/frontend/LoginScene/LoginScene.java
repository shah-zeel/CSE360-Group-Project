package frontend.LoginScene;

import backend.AuthManager;
import frontend.CreateAdminScene;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginScene {
    private Stage primaryStage;
    private AuthManager authManager;

    public LoginScene(Stage primaryStage, AuthManager authManager) {
        this.primaryStage = primaryStage;
        this.authManager = authManager;
    }

    public Scene createLoginScene() {
        if (authManager.getAllUsers().isEmpty()) {
            return new CreateAdminScene(primaryStage, authManager).createAdminScene();
        } else {
            return new StandardLoginScene(primaryStage, authManager).createLoginFields();
        }
    }
}