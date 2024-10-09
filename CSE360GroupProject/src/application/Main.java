package application;

import backend.AuthManager;
import frontend.LoginScene.LoginScene;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Set;
import java.util.HashSet;
import backend.Role;
import backend.User;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        AuthManager authManager = new AuthManager(); // Create a user manager
        
        /*
        // Create an admin user for testing purposes
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(Role.ADMIN);
        User adminUser = authManager.createUser("zs", "z", adminRoles);

        // Complete account setup for the admin user
        authManager.completeAccountSetup(adminUser, "Admin", "", "User", "Admin", "admin@example.com");
        // Invite the admin user (you can specify roles or modify as needed)
        Set<Role> studentRoles = new HashSet<>();
        studentRoles.add(Role.STUDENT);
        String invitationCode = authManager.inviteUser("z", "z@example.com", studentRoles);
        System.out.println("Invitation Code for studentRoles: " + invitationCode); // Print the invitation code for reference
        */
        
        // Create the login scene
        LoginScene loginScene = new LoginScene(primaryStage, authManager);
        Scene scene = loginScene.createLoginScene();

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application
    }
}
