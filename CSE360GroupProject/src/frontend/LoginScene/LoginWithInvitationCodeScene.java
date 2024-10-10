package frontend.LoginScene;

import backend.AuthManager;
import frontend.ErrorScene;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * <p> Title: LoginWithInvitationCodeScene Class. </p>
 * 
 * <p> Description: A class responsible for creating and displaying the login scene for users using an invitation code.
 * It allows users to enter their invitation code to log in and redirects them to set up a password if the code is valid.
 * If the code is invalid, an error message is displayed. </p>
 * 
 * @author Zeel Tejashkumar Shah
 * 
 * @version 1.0 2024-10-09 Initial implementation
 */

public class LoginWithInvitationCodeScene {
    private Stage primaryStage;
    private AuthManager authManager;

    /**
     * Constructor for the LoginWithInvitationCodeScene class.
     *
     * @param primaryStage The primary stage where the scene will be displayed.
     * @param authManager The authentication manager handling user data and invitation validation.
     */
    public LoginWithInvitationCodeScene(Stage primaryStage, AuthManager authManager) {
        this.primaryStage = primaryStage;
        this.authManager = authManager;
    }

    /**
     * Creates and returns the login scene where users enter an invitation code to log in.
     * If the invitation code is valid, the user is redirected to set up their password.
     * If the invitation code is invalid, an error is displayed.
     *
     * @return The login scene for users with an invitation code.
     */
    public Scene createInvitationCodeLoginScene() {
        VBox invitationVBox = new VBox(10);
        Label invitationCodeLabel = new Label("Invitation Code:");
        TextField invitationCodeField = new TextField();
        Button invitationCodeLoginButton = new Button("Login");
        Button backToLoginButton = new Button("Back to Login");

        // Handle login with invitation code
        invitationCodeLoginButton.setOnAction(e -> handleInvitationCodeLogin(invitationCodeField.getText()));

        // Return to the standard login scene
        backToLoginButton.setOnAction(e -> primaryStage.setScene(new StandardLoginScene(primaryStage, authManager).createLoginFields()));

        invitationVBox.getChildren().addAll(invitationCodeLabel, invitationCodeField, invitationCodeLoginButton, backToLoginButton);
        primaryStage.setTitle("Login with Invitation Code");

        return new Scene(invitationVBox, 300, 200);
    }

    /**
     * Handles the login process when an invitation code is entered.
     * If the code is valid, the user is redirected to set up their password.
     * Otherwise, an error message is displayed.
     *
     * @param invitationCode The invitation code entered by the user.
     */
    private void handleInvitationCodeLogin(String invitationCode) {
        if (authManager.isUserInvited(invitationCode)) {
                primaryStage.setScene(new SetupPasswordWithInvitationCodeScene(invitationCode, authManager, primaryStage).createPasswordSetupScene());
        } else {
            new ErrorScene().showError("Invalid invitation code.");
        }
    }
}
