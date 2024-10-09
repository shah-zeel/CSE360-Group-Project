package frontend.HomeScene;

import backend.AuthManager;
import frontend.HomeScene.AdminTasks.*;
import frontend.LoginScene.LoginScene;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * <p> Title: AdminHomeScene Class. </p>
 * 
 * <p> This class represents the home screen for admin users. It provides
 * options for inviting users, creating password reset requests, deleting users,
 * listing users, modifying roles, and logging out. 
 * It interacts with the AuthManager for backend functionality and switches
 * between various scenes for administrative tasks. </p>
 * 
 * @author Zeel Tejashkumar Shah
 * 
 * @version 1.0 2024-10-09 Initial implementation
 */

public class AdminHomeScene {
    private Stage primaryStage;
    private AuthManager authManager;

    /**
     * Constructor for AdminHomeScene.
     *
     * @param primaryStage The primary stage where scenes are set.
     * @param authManager  The authentication manager for handling user-related actions.
     */
    public AdminHomeScene(Stage primaryStage, AuthManager authManager) {
        this.primaryStage = primaryStage;
        this.authManager = authManager; // Store the authManager
    }

    /**
     * Creates the admin home scene with various admin task options.
     *
     * @return The admin home scene to be displayed.
     */
    public Scene createAdminHomeScene() {
        VBox homeVBox = new VBox(10);
        homeVBox.setPrefSize(300, 200);
        
        // Create UI components
        Label welcomeLabel = new Label("Welcome to the Admin Home Page!");
        Button inviteButton = new Button("Invite User"); // Button to invite user
        Button resetPasswordButton = new Button("Create Password Reset Request"); // Button to reset password
        Button deleteUserButton = new Button("Delete User");
        Button listUsersButton = new Button("List Users");
        Button modifyRoleButton = new Button("Add/Remove Role");
        Button logoutButton = new Button("Logout");

        // Add action for the invite button
        inviteButton.setOnAction(e -> handleInviteUser());

        // Add action for the password reset request button
        resetPasswordButton.setOnAction(e -> handlePasswordResetRequest());

        deleteUserButton.setOnAction(e -> handleDeleteUser());
        listUsersButton.setOnAction(e -> handleListUsers());
        modifyRoleButton.setOnAction(e -> handleModifyRole());

        // Add action for the logout button
        logoutButton.setOnAction(e -> handleLogout());

        // Add components to the layout
        homeVBox.getChildren().addAll(welcomeLabel, inviteButton, 
        		resetPasswordButton, deleteUserButton, listUsersButton, modifyRoleButton, logoutButton);
        
        primaryStage.setTitle("Home");
        return new Scene(homeVBox, 300, 400);
    }

    /**
     * Handles the action of inviting a user by switching to the invite user scene.
     */
    private void handleInviteUser() {
        CreateUserInvitationScene inviteScene = new CreateUserInvitationScene(authManager, primaryStage);
        primaryStage.setScene(inviteScene.createAdminScene()); // Switch to invite user scene
    }

    /**
     * Handles the action of creating a password reset request by switching to the reset scene.
     */
    private void handlePasswordResetRequest() {
        CreatePasswordResetScene resetScene = new CreatePasswordResetScene(primaryStage, authManager);
        primaryStage.setScene(resetScene.createResetRequestScene()); // Switch to reset password scene
    }

    /**
     * Handles the action of deleting a user by switching to the delete user scene.
     */
    private void handleDeleteUser() {
        DeleteUserScene deleteUserScene = new DeleteUserScene(primaryStage, authManager);
        primaryStage.setScene(deleteUserScene.createDeleteUserScene()); // Switch to delete user scene
    }

    /**
     * Handles the action of listing users by switching to the list users scene.
     */
    private void handleListUsers() {
        ListUsersScene listUsersScene = new ListUsersScene(primaryStage, authManager);
        primaryStage.setScene(listUsersScene.createListUsersScene()); // Switch to list users scene
    }

    /**
     * Handles the action of modifying roles by switching to the modify role scene.
     */
    private void handleModifyRole() {
    	ModifyRoleScene modifyRoleScene = new ModifyRoleScene(primaryStage, authManager);
        primaryStage.setScene(modifyRoleScene.createModifyRoleScene()); // Switch to modify role scene
    }

    /**
     * Handles the logout action by switching back to the login scene.
     */
    private void handleLogout() {
        LoginScene loginScene = new LoginScene(primaryStage, authManager); // Initialize login scene
        primaryStage.setScene(loginScene.createLoginScene()); // Switch back to the login scene
    }
}
