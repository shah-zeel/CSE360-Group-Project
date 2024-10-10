package frontend.LoginScene;

import backend.Invitation;
import backend.AuthManager;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * <p> Title: SetupPasswordWithInvitationCodeScene Class. </p>
 * 
 * <p> Description: A class that allows users to set up a new password using an invitation code. 
 * This scene validates the user's input, creates a new user based on the invitation, and redirects to the login page upon successful password setup. </p>
 * 
 * @author Zeel Tejashkumar Shah
 * 
 * @version 1.0 2024-10-09 Initial implementation
 */

public class SetupPasswordWithInvitationCodeScene {
    private String invitationCode;
    private AuthManager userManager;
    private Stage primaryStage;

    /**
     * Constructor for the SetupPasswordWithInvitationCodeScene class.
     *
     * @param invitationCode The invitation code provided by the user.
     * @param userManager The authentication manager handling the user creation.
     * @param primaryStage The primary stage where the scene will be displayed.
     */
    public SetupPasswordWithInvitationCodeScene(String invitationCode, AuthManager userManager, Stage primaryStage) {
        this.invitationCode = invitationCode;
        this.userManager = userManager;
        this.primaryStage = primaryStage;
    }

    /**
     * Creates and returns the password setup scene.
     * Users will enter a new password and confirm it to complete the registration process using the invitation code.
     *
     * @return The password setup scene.
     */
    public Scene createPasswordSetupScene() {
        Label passwordLabel = new Label("Enter new password:");
        PasswordField passwordField = new PasswordField();

        Label confirmPasswordLabel = new Label("Confirm password:");
        PasswordField confirmPasswordField = new PasswordField();

        Button submitButton = new Button("Submit");

        // Define the action when the "Submit" button is clicked
        submitButton.setOnAction(event -> {
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();

            if (password.equals(confirmPassword)) {
                Invitation invitation = userManager.getInvitationFromInvitationCode(invitationCode);
                // Save the user to the UserManager
                userManager.createUser(invitation.getUsername(), password, invitation.getRoles());
                userManager.deleteInvitation(invitationCode);
                
                // Redirect to the login page
                primaryStage.setScene(new LoginScene(primaryStage, userManager).createLoginScene());
            } else {
                // Show error if passwords do not match
                System.out.println("Passwords do not match. Please try again.");
            }
        });

        // Layout
        VBox layout = new VBox(10, passwordLabel, passwordField, confirmPasswordLabel, confirmPasswordField, submitButton);
        return new Scene(layout, 300, 200);
    }
}
