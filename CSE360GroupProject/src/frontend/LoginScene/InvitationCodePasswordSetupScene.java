package frontend.LoginScene;

import backend.Invitation;
import backend.AuthManager;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class InvitationCodePasswordSetupScene {
    private String invitationCode;
    private AuthManager userManager;
    private Stage primaryStage;

    public InvitationCodePasswordSetupScene(String invitationCode, AuthManager userManager, Stage primaryStage) {
        this.invitationCode = invitationCode;
        this.userManager = userManager;
        this.primaryStage = primaryStage;
    }

    public Scene createPasswordSetupScene() {
        Label passwordLabel = new Label("Enter new password:");
        PasswordField passwordField = new PasswordField();

        Label confirmPasswordLabel = new Label("Confirm password:");
        PasswordField confirmPasswordField = new PasswordField();

        Button submitButton = new Button("Submit");
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

        VBox layout = new VBox(10, passwordLabel, passwordField, confirmPasswordLabel, confirmPasswordField, submitButton);
        return new Scene(layout, 300, 200);
    }
}
