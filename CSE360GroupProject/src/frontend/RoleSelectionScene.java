package frontend;

import backend.*;
import frontend.HomeScene.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.Set;

/**
 * <p> Title: RoleSelectionScene Class. </p>
 * 
 * <p> Description: This class manages the role selection process for a user with multiple roles. It displays a 
 * role selection scene if the user has more than one role and navigates to the appropriate home page based on 
 * the selected role. </p>
 * 
 * @author Zeel Tejashkumar Shah
 * 
 * @version 1.0 2024-10-09 Initial implementation
 */

public class RoleSelectionScene {
    private Stage primaryStage;
    private AuthManager authManager;

    /**
     * Constructor for the RoleSelectionScene class.
     *
     * @param primaryStage The main stage of the application.
     * @param authManager  The authentication manager that handles user data.
     */
    public RoleSelectionScene(Stage primaryStage, AuthManager authManager) {
        this.primaryStage = primaryStage;
        this.authManager = authManager;
    }

    /**
     * Handles the role selection process for the user. If the user has more than
     * one role, a role selection scene is shown. Otherwise, the user is directly
     * navigated to the appropriate home page.
     *
     * @param user The user whose roles are to be handled.
     */
    public void handleRoleSelection(User user) {
        Set<Role> roles = user.getRoles();

        if (roles.size() > 1) {
            // Create and set the role selection scene if the user has multiple roles
            Scene roleSelectionScene = createRoleSelectionScene(user);
            primaryStage.setScene(roleSelectionScene);
        } else if (roles.size() == 1) {
            // Navigate directly to the home page for the single role
            navigateToRoleHome(user, roles.iterator().next());
        }
    }

    /**
     * Creates the role selection scene, where the user can choose one of their
     * assigned roles to proceed.
     *
     * @param user The user selecting a role.
     * @return The scene where the user selects their role.
     */
    private Scene createRoleSelectionScene(User user) {
        VBox roleSelectionVBox = new VBox(10);
        roleSelectionVBox.setPrefSize(300, 200);

        // Add label to prompt the user to select a role
        Label label = new Label("Select your role for this session:");
        roleSelectionVBox.getChildren().add(label);

        Set<Role> roles = user.getRoles();

        // Create buttons for each available role
        for (Role role : roles) {
            Button roleButton = new Button(role.toString());
            roleButton.setOnAction(event -> {
                // Navigate to the home page for the selected role
                navigateToRoleHome(user, role);
            });
            roleSelectionVBox.getChildren().add(roleButton);
        }

        // Return the role selection scene
        return new Scene(roleSelectionVBox, 300, 200);
    }

    /**
     * Navigates the user to the home page based on the selected role.
     *
     * @param user The user who is being navigated.
     * @param role The selected role of the user.
     */
    private void navigateToRoleHome(User user, Role role) {
        switch (role) {
        case ADMIN:
            // Navigate to the Admin Home scene
            primaryStage.setScene(new AdminHomeScene(primaryStage, authManager).createAdminHomeScene());
            break;
        case STUDENT:
            // Navigate to the Student Home scene
            primaryStage.setScene(new StudentHomeScene(primaryStage, authManager).createStudentHomeScene());
            break;
        case INSTRUCTOR:
            // Navigate to the Instructor Home scene
            primaryStage.setScene(new InstructorHomeScene(primaryStage, authManager).createInstructorHomeScene());
            break;
        default:
            // Show an error if the role is unknown
            new ErrorScene().showError("Unknown role.");
        }
    }
}
