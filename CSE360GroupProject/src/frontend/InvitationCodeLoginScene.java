package frontend;

import backend.User;
import backend.UserManager;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class InvitationCodeLoginScene {
    private Stage primaryStage;
    private UserManager userManager;

    public InvitationCodeLoginScene(Stage primaryStage, UserManager userManager) {
        this.primaryStage = primaryStage;
        this.userManager = userManager;
    }

    public Scene createInvitationCodeLoginScene() {
        VBox invitationVBox = new VBox(10);
        Label invitationCodeLabel = new Label("Invitation Code:");
        TextField invitationCodeField = new TextField();
        Button invitationCodeLoginButton = new Button("Login");
        Button backToLoginButton = new Button("Login with Password");

        invitationCodeLoginButton.setOnAction(e -> handleInvitationCodeLogin(invitationCodeField.getText()));
        backToLoginButton.setOnAction(e -> primaryStage.setScene(new StandardLoginScene(primaryStage, userManager).createLoginFields()));

        invitationVBox.getChildren().addAll(invitationCodeLabel, invitationCodeField, invitationCodeLoginButton, backToLoginButton);
        primaryStage.setTitle("Login with Invitation Code");

        return new Scene(invitationVBox, 300, 200);
    }

    private void handleInvitationCodeLogin(String invitationCode) {
        User user = userManager.getUserFromInvitationCode(invitationCode);
        if (user != null) {
            if (!user.isSetupComplete()) {
                primaryStage.setScene(new SetupScene(user, userManager, primaryStage).createSetupScene());
            } else {
                new ErrorScene(primaryStage).showError("Account setup has already been completed.");
            }
        } else {
            new ErrorScene(primaryStage).showError("Invalid invitation code.");
        }
    }
}
