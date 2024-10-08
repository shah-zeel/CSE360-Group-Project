package frontend;

import backend.Role;
import backend.User;
import backend.UserManager;
import frontend.HomeScene.AdminHomeScene;
import frontend.HomeScene.InstructorHomeScene;
import frontend.HomeScene.StudentHomeScene;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Set;

public class RoleSelectionScene {
    private Stage primaryStage;
    private UserManager userManager;

    public RoleSelectionScene(Stage primaryStage, UserManager userManager) {
        this.primaryStage = primaryStage;
        this.userManager = userManager;
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
			new ErrorScene(primaryStage).showError("Unknown role.");
		}
	}
}
