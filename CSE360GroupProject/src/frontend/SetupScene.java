package frontend;

import backend.User;
import backend.UserManager;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SetupScene {
    private User user;
    private UserManager userManager;
    private Stage primaryStage;

    public SetupScene(User user, UserManager userManager, Stage primaryStage) {
        this.user = user;
        this.userManager = userManager;
        this.primaryStage = primaryStage;
    }

    public Scene createSetupScene() {
        VBox setupVBox = new VBox(10);
        setupVBox.setPrefSize(300, 300);
        
        // Input fields for user information
        Label firstNameLabel = new Label("First Name:");
        TextField firstNameField = new TextField();
        Label middleNameLabel = new Label("Middle Name:");
        TextField middleNameField = new TextField();
        Label lastNameLabel = new Label("Last Name:");
        TextField lastNameField = new TextField();
        Label preferredNameLabel = new Label("Preferred Name:");
        TextField preferredNameField = new TextField();
        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        Button completeSetupButton = new Button("Complete Setup");

        // Adding all components to the VBox
        setupVBox.getChildren().addAll(firstNameLabel, firstNameField, middleNameLabel, middleNameField,
                lastNameLabel, lastNameField, preferredNameLabel, preferredNameField, emailLabel, emailField, 
                completeSetupButton);

        // Action for the complete setup button
        completeSetupButton.setOnAction(e -> handleCompleteSetup(firstNameField.getText(), 
                middleNameField.getText(), lastNameField.getText(), 
                preferredNameField.getText(), emailField.getText()));
        primaryStage.setTitle("Account Setup");
        return new Scene(setupVBox, 300, 400); // Return the setup scene
    }

    private void handleCompleteSetup(String firstName, String middleName, String lastName, 
                                      String preferredName, String email) {
        // Call the user manager to complete account setup
        boolean success = userManager.completeAccountSetup(user, firstName, middleName, lastName, preferredName, email);

        if (success) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Setup completed successfully!");
            alert.showAndWait();
            new LoginScene(primaryStage, userManager).handleRoleSelection(user); // Go to home scene after setup
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Setup failed. Please try again.");
            alert.showAndWait();
        }
    }
}
