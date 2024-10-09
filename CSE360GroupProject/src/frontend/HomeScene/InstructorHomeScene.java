package frontend.HomeScene;

import backend.AuthManager;
import frontend.LoginScene.LoginScene;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class InstructorHomeScene {
    private Stage primaryStage;
    private AuthManager authManager;

    public InstructorHomeScene(Stage primaryStage, AuthManager authManager) {
        this.primaryStage = primaryStage;
        this.authManager = authManager; // Store the authManager
    }

    public Scene createInstructorHomeScene() {
        VBox homeVBox = new VBox(10);
        homeVBox.setPrefSize(300, 200);
        
        Label welcomeLabel = new Label("Welcome to the Instructor Home Page!");
        Button logoutButton = new Button("Logout");

        // Adding action for the logout button
        logoutButton.setOnAction(e -> handleLogout());

        homeVBox.getChildren().addAll(welcomeLabel, logoutButton);
        primaryStage.setTitle("Home");
        return new Scene(homeVBox, 300, 200); // Return the home scene
    }

    private void handleLogout() {
        // Navigate back to the login scene
        LoginScene loginScene = new LoginScene(primaryStage, authManager); // You may need to pass the authManager
        primaryStage.setScene(loginScene.createLoginScene()); // Switch back to the login scene
    }
}
