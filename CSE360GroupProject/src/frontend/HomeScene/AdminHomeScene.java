package frontend.HomeScene;

import backend.UserManager;
import frontend.CreateInviteScene; // Import the AdminScene class
import frontend.LoginScene.LoginScene;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdminHomeScene {
    private Stage primaryStage;
    private UserManager userManager;

    public AdminHomeScene(Stage primaryStage, UserManager userManager) {
        this.primaryStage = primaryStage;
        this.userManager = userManager; // Store the userManager
    }

    public Scene createAdminHomeScene() {
        VBox homeVBox = new VBox(10);
        homeVBox.setPrefSize(300, 200);
        
        Label welcomeLabel = new Label("Welcome to the Admin Home Page!");
        Button inviteButton = new Button("Invite User"); // Button to invite user
        Button logoutButton = new Button("Logout");

        // Adding action for the invite button
        inviteButton.setOnAction(e -> handleInviteUser());

        // Adding action for the logout button
        logoutButton.setOnAction(e -> handleLogout());

        homeVBox.getChildren().addAll(welcomeLabel, inviteButton, logoutButton); // Add invite button to the layout
        primaryStage.setTitle("Home");
        return new Scene(homeVBox, 300, 200); // Return the home scene
    }

    private void handleInviteUser() {
        // Create and set the AdminScene for inviting users
    	CreateInviteScene adminScene = new CreateInviteScene(userManager, primaryStage);
        primaryStage.setScene(adminScene.createAdminScene()); // Switch to the AdminScene for inviting users
    }

    private void handleLogout() {
        // Navigate back to the login scene
        LoginScene loginScene = new LoginScene(primaryStage, userManager); // You may need to pass the userManager
        primaryStage.setScene(loginScene.createLoginScene()); // Switch back to the login scene
    }
}
