package frontend.LoginScene;

import backend.AuthManager;
import backend.ResetRequest;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * <p> Title: PasswordResetScene Class. </p>
 * 
 * <p> Description: A class responsible for managing the password reset functionality.
 * It allows users to enter their email, one-time password (OTP), and set a new password if the OTP is valid and the reset request has not expired.
 * If the reset is successful, the user is redirected to the login scene. </p>
 * 
 * @author Zeel Tejashkumar Shah
 * 
 * @version 1.0 2024-10-09 Initial implementation
 */

public class PasswordResetScene {
	private Stage primaryStage;
    private AuthManager authManager;

    /**
     * Constructor for the PasswordResetScene class.
     *
     * @param primaryStage The primary stage where the scene will be displayed.
     * @param authManager The authentication manager handling password reset operations.
     */
    public PasswordResetScene(Stage primaryStage, AuthManager authManager) {
        this.authManager = authManager;
        this.primaryStage = primaryStage;
    }

    /**
     * Creates and returns the password reset scene.
     * Users will input their email, OTP, and new password to reset their password.
     *
     * @return The password reset scene.
     */
    public Scene createPasswordResetScene() {
        Label emailLabel = new Label("Enter your email:");
        TextField emailField = new TextField();

        Label otpLabel = new Label("Enter One-Time Password (OTP):");
        TextField otpField = new TextField();

        Label newPasswordLabel = new Label("Enter new password:");
        PasswordField newPasswordField = new PasswordField();

        Label confirmPasswordLabel = new Label("Confirm new password:");
        PasswordField confirmPasswordField = new PasswordField();

        Button submitButton = new Button("Submit");
        
        Button backToLoginButton = new Button("Back to Login");

        // Go back to the login scene
        backToLoginButton.setOnAction(e -> primaryStage.setScene(new StandardLoginScene(primaryStage, authManager).createLoginFields()));

        // Define the action when the "Submit" button is clicked
        submitButton.setOnAction(event -> {
            String email = emailField.getText();
            String otp = otpField.getText();
            String newPassword = newPasswordField.getText();
            String confirmPassword = confirmPasswordField.getText();

            ResetRequest request = authManager.findRequestByEmail(email);

            // Check if the request is valid and not expired
            if (request == null || request.isExpired()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid or expired reset request.");
                alert.showAndWait();
                return;
            }

            // Verify if the OTP is correct
            if (!otp.equals(request.getOneTimePassword())) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid OTP. Please try again.");
                alert.showAndWait();
                return;
            }

            // Ensure the new password and confirm password match
            if (!newPassword.equals(confirmPassword)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Passwords do not match. Please try again.");
                alert.showAndWait();
                return;
            }

            // If all checks pass, update the user's password and remove the reset request
            authManager.updateUserPassword(email, newPassword); // Update the password in UserManager
            authManager.removeRequest(request); // Remove the reset request after successful password reset

            // Redirect the user back to the login page
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "Password reset successfully. Please log in with your new password.");
            successAlert.showAndWait();
            primaryStage.setScene(new LoginScene(primaryStage, authManager).createLoginScene());
        });

        // Layout
        VBox layout = new VBox(10, emailLabel, emailField, otpLabel, otpField, newPasswordLabel, newPasswordField,
                confirmPasswordLabel, confirmPasswordField, submitButton, backToLoginButton);
        return new Scene(layout, 300, 600);
    }
}
