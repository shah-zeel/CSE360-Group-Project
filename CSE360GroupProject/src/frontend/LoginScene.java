package frontend;

import backend.UserManager;
import frontend.HomeScene.AdminHomeScene;
import frontend.HomeScene.InstructorHomeScene;
import frontend.HomeScene.StudentHomeScene;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Set;
import backend.Role;
import backend.User;

public class LoginScene {
    private Stage primaryStage;
    private UserManager userManager;

    public LoginScene(Stage primaryStage, UserManager userManager) {
        this.primaryStage = primaryStage;
        this.userManager = userManager;
    }

    public Scene createLoginScene() {
        if (userManager.getUsers().isEmpty()) {
            return new CreateAdminScene(userManager, primaryStage).createAdminScene();
        } else {
            return new StandardLoginScene(primaryStage, userManager).createLoginFields();
        }
    }
}