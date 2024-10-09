package frontend;

import backend.User;
import backend.AuthManager;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SetupScene {
    private User user;
    private AuthManager authManager;
    private Stage primaryStage;

    public SetupScene(User user, AuthManager authManager, Stage primaryStage) {
        this.user = user;
        this.authManager = authManager;
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
        // Perform validations
        String validationMessage = validateInputs(firstName, middleName, lastName, preferredName, email);
        if (!validationMessage.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, validationMessage);
            alert.showAndWait();
            return; // Exit if validation fails
        }

        // Call the user manager to complete account setup
        boolean success = authManager.completeAccountSetup(user, firstName, middleName, lastName, preferredName, email);

        if (success) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Setup completed successfully!");
            alert.showAndWait();
            new RoleSelectionScene(primaryStage, authManager).handleRoleSelection(user); // Go to home scene after setup
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Setup failed. Please try again.");
            alert.showAndWait();
        }
    }

    private String validateInputs(String firstName, String middleName, String lastName, 
                                   String preferredName, String email) {
        StringBuilder errors = new StringBuilder();

        // Check required fields
        if (firstName.isEmpty()) {
            errors.append("First Name is required.\n");
        }
        if (lastName.isEmpty()) {
            errors.append("Last Name is required.\n");
        }
        if (email.isEmpty()) {
            errors.append("Email is required.\n");
        } else if (!isValidEmail(email)) {
            errors.append("Email format is invalid.\n");
        }

        // Check optional fields for invalid characters
        if (!middleName.isEmpty() && !isValidName(middleName)) {
            errors.append("Middle Name should not contain numbers or special characters.\n");
        }
        if (!preferredName.isEmpty() && !isValidName(preferredName)) {
            errors.append("Preferred Name should not contain numbers or special characters.\n");
        }

        return errors.toString();
    }

    private boolean isValidEmail(String email) {
        // Simple regex for email validation
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidName(String name) {
        // Check if the name contains only letters and spaces
        return name.matches("[a-zA-Z\\s]+");
    }
}
