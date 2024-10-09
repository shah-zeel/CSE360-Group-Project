package frontend.HomeScene.AdminTasks;

import backend.*;
import frontend.HomeScene.AdminHomeScene;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.*;

public class ModifyRoleScene {
    private Stage primaryStage;
    private AuthManager authManager;
    private Button applyButton; // Declare applyButton at the class level
    
    public ModifyRoleScene(Stage primaryStage, AuthManager authManager) {
        this.primaryStage = primaryStage;
        this.authManager = authManager;
        this.applyButton = new Button("Apply Changes"); // Initialize applyButton in the constructor
        applyButton.setVisible(false); // Initially hidden
    }

    public Scene createModifyRoleScene() {
        VBox vbox = new VBox(10);

        // Label and TextField for username input
        Label userLabel = new Label("Enter username to modify roles:");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        // Display current roles label
        Label rolesLabel = new Label("Current roles: "); // Start with "Current roles"
        rolesLabel.setVisible(false); // Initially hidden

        // Checkboxes for role selection
        Map<Role, CheckBox> roleCheckBoxes = new HashMap<>();
        for (Role role : Role.values()) {
            CheckBox checkBox = new CheckBox(role.toString());
            checkBox.setVisible(false); // Initially hidden
            checkBox.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
                rolesLabel.setText("New Roles: "); // Change label to "New Roles"
                applyButton.setVisible(true); // Show the apply button
            });
            roleCheckBoxes.put(role, checkBox);
        }

        // VBox for roles
        VBox rolesVBox = new VBox(10);
        rolesVBox.setVisible(false); // Initially hidden

        // Buttons to fetch roles and apply changes
        Button fetchRolesButton = new Button("Fetch Current Roles");
        Button backButton = new Button("Back");

        // Fetch current roles action
        fetchRolesButton.setOnAction(e -> {
            String username = usernameField.getText();
            User user = authManager.findUserByUsername(username);
            if (user != null) {
                // Set checkboxes based on current roles
                for (Map.Entry<Role, CheckBox> entry : roleCheckBoxes.entrySet()) {
                    CheckBox checkBox = entry.getValue();
                    checkBox.setSelected(user.getRoles().contains(entry.getKey()));
                    checkBox.setVisible(true); // Make checkbox visible
                }
                rolesLabel.setVisible(true); // Show the roles label
                rolesVBox.setVisible(true); // Show the roles section
                applyButton.setVisible(false); // Hide apply button initially
            } else {
                showAlert("User not found.");
                clearCheckBoxes(roleCheckBoxes);
                rolesLabel.setVisible(false); // Hide the roles label
                rolesVBox.setVisible(false); // Hide the roles section
            }
        });

        // Apply changes action
        applyButton.setOnAction(e -> {
            String username = usernameField.getText();
            User user = authManager.findUserByUsername(username);
            if (user != null) {
                for (Map.Entry<Role, CheckBox> entry : roleCheckBoxes.entrySet()) {
                    Role role = entry.getKey();
                    CheckBox checkBox = entry.getValue();
                    if (checkBox.isSelected() && !user.getRoles().contains(role)) {
                        // Add role if selected and not already present
                        authManager.addRole(user, role);
                    } else if (!checkBox.isSelected() && user.getRoles().contains(role)) {
                        // Remove role if not selected and already present
                        authManager.removeRole(user, role);
                    }
                }
                showAlert("Roles updated successfully.");
                rolesLabel.setText("Current roles: "); // Reset label back to "Current roles"
                applyButton.setVisible(false); // Hide the apply button
            } else {
                showAlert("User not found.");
            }
        });

        // Back button action
        backButton.setOnAction(e -> primaryStage.setScene(new AdminHomeScene(primaryStage, authManager).createAdminHomeScene()));

        // Add components to VBox
        vbox.getChildren().addAll(userLabel, usernameField, fetchRolesButton, rolesLabel, rolesVBox, applyButton, backButton);
        
        // Add checkboxes to roles VBox after the initial setup
        for (CheckBox checkBox : roleCheckBoxes.values()) {
            rolesVBox.getChildren().add(checkBox); // Add each checkbox to rolesVBox
        }

        return new Scene(vbox, 300, 400);
    }

    // Method to show alert messages
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Method to clear all checkboxes
    private void clearCheckBoxes(Map<Role, CheckBox> roleCheckBoxes) {
        for (CheckBox checkBox : roleCheckBoxes.values()) {
            checkBox.setSelected(false);
        }
    }
}
