package frontend.HomeScene.AdminTasks;

import backend.AuthManager;
import backend.User;
import frontend.HomeScene.AdminHomeScene;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DeleteUserScene {
    private Stage primaryStage;
    private AuthManager authManager;

    public DeleteUserScene(Stage primaryStage, AuthManager authManager) {
        this.primaryStage = primaryStage;
        this.authManager = authManager;
    }

    public Scene createDeleteUserScene() {
        VBox vbox = new VBox(10);
        Label instructionLabel = new Label("Enter the username of the user you want to delete:");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        Button deleteButton = new Button("Delete");
        Label statusLabel = new Label();

        // Delete action: Check if the user exists and prompt for confirmation
        deleteButton.setOnAction(e -> {
            String username = usernameField.getText();
            User user = authManager.findUserByUsername(username);

            if (user != null) {
                // Ask for confirmation before deleting the user
                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationAlert.setTitle("Confirm Deletion");
                confirmationAlert.setHeaderText("Are you sure?");
                confirmationAlert.setContentText("Do you really want to delete the user: " + user.getUsername() + "?");

                confirmationAlert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        authManager.deleteUser(user);
                        statusLabel.setText("User '" + user.getUsername() + "' deleted successfully.");
                        usernameField.clear();
                    } else {
                        statusLabel.setText("Deletion canceled.");
                    }
                });
            } else {
                statusLabel.setText("Error: User not found.");
            }
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> primaryStage.setScene(new AdminHomeScene(primaryStage, authManager).createAdminHomeScene()));

        vbox.getChildren().addAll(instructionLabel, usernameField, deleteButton, statusLabel, backButton);
        return new Scene(vbox, 300, 200);
    }
}
