package frontend;

import backend.Role;
import backend.User;
import backend.UserManager;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Set;

public class LoginScene {
	private Stage primaryStage;
	private UserManager userManager;

	public LoginScene(Stage primaryStage, UserManager userManager) {
		this.primaryStage = primaryStage;
		this.userManager = userManager;
	}

	public Scene createLoginScene() {
		// Check if any users exist
		if (userManager.getUsers().isEmpty()) {
			return new CreateAdminScene(userManager, primaryStage).createAdminScene(); // Create admin fields and set scene
		} else {
			return createLoginFields(); // Create login fields and set scene
		}
	}

	private Scene createLoginFields() {
		VBox loginVBox = new VBox(10);
		Label usernameLabel = new Label("Username:");
		TextField usernameField = new TextField();
		Label passwordLabel = new Label("Password:");
		PasswordField passwordField = new PasswordField();
		Button loginButton = new Button("Login");

		loginVBox.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField, loginButton);

		// Set action for the login button
		loginButton.setOnAction(e -> handleLogin(usernameField.getText(), passwordField.getText()));
		primaryStage.setTitle("Login");
		return new Scene(loginVBox, 300, 200); // Return the login scene
	}

	private void handleLogin(String username, String password) {
		User user = userManager.login(username, password); // Method to authenticate user

		if (user != null) {
			if (!user.isSetupComplete()) {
				primaryStage.setScene(new SetupScene(user, userManager, primaryStage).createSetupScene()); // Pass the
			} else {
				// Call the handleRoleSelection method
				handleRoleSelection(user);
			}
		} else {
			// Handle login failure (e.g., show an error message)
			showError("Invalid credentials. Please try again.");
		}
	}

	protected void handleRoleSelection(User user) {
		Set<Role> roles = user.getRoles();

		if (roles.size() > 1) {

			// Create and set the role selection scene
			Scene roleSelectionScene = createRoleSelectionScene(user);
			primaryStage.setScene(roleSelectionScene);

		} else if (roles.size() == 1) {
			// Navigate to the home page for the single role
			navigateToRoleHome(user, roles.iterator().next());
		}
	}

	private Scene createRoleSelectionScene(User user) {
		VBox roleSelectionVBox = new VBox(10);
		roleSelectionVBox.setPrefSize(300, 200);

		Label label = new Label("Select your role for this session:");
		roleSelectionVBox.getChildren().add(label);

		Set<Role> roles = user.getRoles();

		// Create buttons for each role
		for (Role role : roles) {
			Button roleButton = new Button(role.toString());
			roleButton.setOnAction(event -> {
				navigateToRoleHome(user, role); // Navigate to the home page for the selected role
			});
			roleSelectionVBox.getChildren().add(roleButton);
		}

		return new Scene(roleSelectionVBox, 300, 200); // Return the role selection scene
	}

	private void navigateToRoleHome(User user, Role role) {
		switch (role) {
		case ADMIN:
			// Navigate to Admin Home
			primaryStage.setScene(new AdminHomeScene(primaryStage, userManager).createAdminHomeScene());
			break;
		case STUDENT:
			// Navigate to Student Home
			primaryStage.setScene(new StudentHomeScene(primaryStage, userManager).createStudentHomeScene());
			break;
		case INSTRUCTOR:
			// Navigate to Instructor Home
			primaryStage.setScene(new InstructorHomeScene(primaryStage, userManager).createInstructorHomeScene());
			break;
		default:
			// Handle unknown role
			showError("Unknown role.");
		}
	}

	private void showError(String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
}
