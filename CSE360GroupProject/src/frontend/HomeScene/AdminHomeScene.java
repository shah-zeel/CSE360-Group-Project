package frontend.HomeScene;

import backend.AuthManager;
import frontend.HomeScene.AdminTasks.*;
import frontend.LoginScene.LoginScene;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdminHomeScene {
    private Stage primaryStage;
    private AuthManager authManager;

    public AdminHomeScene(Stage primaryStage, AuthManager authManager) {
        this.primaryStage = primaryStage;
        this.authManager = authManager; // Store the authManager
    }

    public Scene createAdminHomeScene() {
        VBox homeVBox = new VBox(10);
        homeVBox.setPrefSize(300, 200);
        
        Label welcomeLabel = new Label("Welcome to the Admin Home Page!");
        Button inviteButton = new Button("Invite User"); // Button to invite user
        Button resetPasswordButton = new Button("Create Password Reset Request"); // Button to reset password
        Button deleteUserButton = new Button("Delete User");
        Button listUsersButton = new Button("List Users");
        Button modifyRoleButton = new Button("Add/Remove Role");
        Button logoutButton = new Button("Logout");

        // Adding action for the invite button
        inviteButton.setOnAction(e -> handleInviteUser());

        // Adding action for the password reset request button
        resetPasswordButton.setOnAction(e -> handlePasswordResetRequest());

        deleteUserButton.setOnAction(e -> handleDeleteUser());
        listUsersButton.setOnAction(e -> handleListUsers());
        modifyRoleButton.setOnAction(e -> handleModifyRole());

        // Adding action for the logout button
        logoutButton.setOnAction(e -> handleLogout());

        homeVBox.getChildren().addAll(welcomeLabel, inviteButton, 
        		resetPasswordButton, deleteUserButton, listUsersButton, modifyRoleButton, logoutButton); // Add invite and reset buttons to the layout
        
        primaryStage.setTitle("Home");
        return new Scene(homeVBox, 300, 400);
    }

    private void handleInviteUser() {
        // Create and set the AdminScene for inviting users
        CreateInviteScene inviteScene = new CreateInviteScene(authManager, primaryStage);
        primaryStage.setScene(inviteScene.createAdminScene()); // Switch to the AdminScene for inviting users
    }

    private void handlePasswordResetRequest() {
        // Create and set the AdminScene for creating password reset requests
        CreatePasswordResetRequestScene resetScene = new CreatePasswordResetRequestScene(primaryStage, authManager);
        primaryStage.setScene(resetScene.createResetRequestScene()); // Switch to the Password Reset Request scene
    }
    
    private void handleDeleteUser() {
            DeleteUserScene deleteUserScene = new DeleteUserScene(primaryStage, authManager);
            primaryStage.setScene(deleteUserScene.createDeleteUserScene());
    }

    private void handleListUsers() {
        ListUsersScene listUsersScene = new ListUsersScene(primaryStage, authManager);
        primaryStage.setScene(listUsersScene.createListUsersScene());
    }
    
    private void handleModifyRole() {
    	ModifyRoleScene modifyRoleScene = new ModifyRoleScene(primaryStage, authManager);
        primaryStage.setScene(modifyRoleScene.createModifyRoleScene());
    }

    private void handleLogout() {
        // Navigate back to the login scene
        LoginScene loginScene = new LoginScene(primaryStage, authManager); // You may need to pass the authManager
        primaryStage.setScene(loginScene.createLoginScene()); // Switch back to the login scene
    }
}
