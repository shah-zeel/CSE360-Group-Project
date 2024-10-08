package frontend;

import backend.UserManager;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class InstructorHomeScene {
    private Stage primaryStage;
    private UserManager userManager;

    public InstructorHomeScene(Stage primaryStage, UserManager userManager) {
        this.primaryStage = primaryStage;
        this.userManager = userManager; // Store the userManager
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
        LoginScene loginScene = new LoginScene(primaryStage, userManager); // You may need to pass the userManager
        primaryStage.setScene(loginScene.createLoginScene()); // Switch back to the login scene
    }
}
