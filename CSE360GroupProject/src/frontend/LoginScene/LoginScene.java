package frontend.LoginScene;

import backend.AuthManager;
import frontend.CreateAdminScene;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * <p> Title: LoginScene Class. </p>
 * 
 * <p> Description: A class responsible for creating and displaying the login scene. It checks whether any users exist, 
 * and redirects to the admin creation scene if no users are found, or the standard login scene otherwise. </p>
 * 
 * @author Zeel Tejashkumar Shah
 * 
 * @version 1.0 2024-10-09 Initial implementation
 */

public class LoginScene {
    private Stage primaryStage;
    private AuthManager authManager;

    /**
     * Constructor for the LoginScene class.
     *
     * @param primaryStage The primary stage where the scene will be displayed.
     * @param authManager The authentication manager handling user data and authentication.
     */
    public LoginScene(Stage primaryStage, AuthManager authManager) {
        this.primaryStage = primaryStage;
        this.authManager = authManager;
    }

    /**
     * Creates and returns the appropriate login scene. If there are no users in the system,
     * it redirects to the admin creation scene. Otherwise, it returns the standard login scene.
     *
     * @return The login scene based on the user data.
     */
    public Scene createLoginScene() {
        if (authManager.getAllUsers().isEmpty()) {
            return new CreateAdminScene(primaryStage, authManager).createAdminScene();
        } else {
            return new StandardLoginScene(primaryStage, authManager).createLoginFields();
        }
    }
}
