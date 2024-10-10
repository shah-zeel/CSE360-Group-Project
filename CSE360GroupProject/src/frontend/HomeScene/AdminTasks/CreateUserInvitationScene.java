package frontend.HomeScene.AdminTasks;

import backend.Role;
import backend.AuthManager;
import frontend.HomeScene.AdminHomeScene;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.HashSet;
import java.util.Set;

/**
 * <p> Title: CreateUserInvitationScene Class. </p>
 * 
 * <p> Description: This class provides a scene where an admin can invite a new user by entering 
 * the username, email, and selecting roles (Admin, Student, Instructor). The invitation code is 
 * generated and displayed upon successful invitation. </p>
 * 
 * @author Zeel Tejashkumar Shah
 * 
 * @version 1.0 2024-10-09 Initial implementation
 */

public class CreateUserInvitationScene {
    private AuthManager authManager;
    private Stage primaryStage;

    /**
     * Constructor to initialize the CreateUserInvitationScene.
     *
     * @param authManager  The authentication manager to handle user invitations.
     * @param primaryStage The main stage of the application.
     */
    public CreateUserInvitationScene(AuthManager authManager, Stage primaryStage) {
        this.authManager = authManager;
        this.primaryStage = primaryStage;
    }

    /**
     * Creates the admin scene where the admin can enter user details and send an invitation.
     *
     * @return The scene for creating user invitations.
     */
    public Scene createAdminScene() {
        VBox adminVBox = new VBox(10);
        adminVBox.setPrefSize(300, 300);

        // UI Components
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        Label rolesLabel = new Label("Select Role(s):");

        // Checkboxes for roles
        CheckBox adminRoleCheckBox = new CheckBox("Admin");
        CheckBox studentRoleCheckBox = new CheckBox("Student");
        CheckBox instructorRoleCheckBox = new CheckBox("Instructor");

        Button inviteButton = new Button("Send Invitation");
        
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> primaryStage.setScene(new AdminHomeScene(primaryStage, authManager).createAdminHomeScene()));

        // Adding all components to the VBox
        adminVBox.getChildren().addAll(usernameLabel, usernameField, emailLabel, emailField, rolesLabel,
                adminRoleCheckBox, studentRoleCheckBox, instructorRoleCheckBox, inviteButton, backButton);

        // Handle invite button click
        inviteButton.setOnAction(e -> handleInviteUser(usernameField.getText(), emailField.getText(),
                adminRoleCheckBox.isSelected(), studentRoleCheckBox.isSelected(), instructorRoleCheckBox.isSelected()));

        primaryStage.setTitle("Admin Panel");
        return new Scene(adminVBox, 300, 400); // Return the admin scene
    }

    /**
     * Handles the user invitation logic by validating inputs and sending the invitation.
     *
     * @param username     The username entered by the admin.
     * @param email        The email entered by the admin.
     * @param isAdmin      Whether the Admin role is selected.
     * @param isStudent    Whether the Student role is selected.
     * @param isInstructor Whether the Instructor role is selected.
     */
    private void handleInviteUser(String username, String email, boolean isAdmin, boolean isStudent, boolean isInstructor) {
        // Validate input
        if (username.isEmpty() || email.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Username and Email are required.");
            alert.showAndWait();
            return;
        }

        // Create a set of roles based on checkbox selections
        Set<Role> roles = new HashSet<>();
        if (isAdmin) {
            roles.add(Role.ADMIN);
        }
        if (isStudent) {
            roles.add(Role.STUDENT);
        }
        if (isInstructor) {
            roles.add(Role.INSTRUCTOR);
        }

        // Ensure at least one role is selected
        if (roles.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "At least one role must be selected.");
            alert.showAndWait();
            return;
        }

        // Call Invitations to invite the user
        String invitationCode = authManager.inviteUser(username, email, roles);

        // Show invitation code to the admin
        if (invitationCode != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Invitation sent! Invitation code: " + invitationCode);
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to send invitation. Please try again.");
            alert.showAndWait();
        }

        // Navigate back to Admin Home Scene
        primaryStage.setScene(new AdminHomeScene(primaryStage, authManager).createAdminHomeScene());
    }
}
