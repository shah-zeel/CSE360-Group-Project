package application;

import java.util.Set;
import java.util.HashSet;
import backend.Role;
import backend.User;
import backend.UserManager;
import frontend.LoginScene;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		UserManager userManager = new UserManager(); // Create a user manager

		// Create the login scene
		LoginScene loginScene = new LoginScene(primaryStage, userManager);
		Scene scene = loginScene.createLoginScene();

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args); // Launch the JavaFX application
	}
}
