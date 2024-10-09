package frontend.LoginScene;

import backend.AuthManager;
import frontend.ErrorScene;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginWithInvitationCodeScene {
    private Stage primaryStage;
    private AuthManager authManager;

    public LoginWithInvitationCodeScene(Stage primaryStage, AuthManager authManager) {
        this.primaryStage = primaryStage;
        this.authManager = authManager;
    }

    public Scene createInvitationCodeLoginScene() {
        VBox invitationVBox = new VBox(10);
        Label invitationCodeLabel = new Label("Invitation Code:");
        TextField invitationCodeField = new TextField();
        Button invitationCodeLoginButton = new Button("Login");
        Button backToLoginButton = new Button("Back to Login");

        invitationCodeLoginButton.setOnAction(e -> handleInvitationCodeLogin(invitationCodeField.getText()));
        backToLoginButton.setOnAction(e -> primaryStage.setScene(new StandardLoginScene(primaryStage, authManager).createLoginFields()));

        invitationVBox.getChildren().addAll(invitationCodeLabel, invitationCodeField, invitationCodeLoginButton, backToLoginButton);
        primaryStage.setTitle("Login with Invitation Code");

        return new Scene(invitationVBox, 300, 200);
    }

    private void handleInvitationCodeLogin(String invitationCode) {
        if (authManager.isUserInvited(invitationCode)) {
                primaryStage.setScene(new SetupPasswordWithInvitationCodeScene(invitationCode, authManager, primaryStage).createPasswordSetupScene());
        } else {
            new ErrorScene().showError("Invalid invitation code.");
        }
    }
}
