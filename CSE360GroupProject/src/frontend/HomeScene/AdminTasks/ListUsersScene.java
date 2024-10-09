package frontend.HomeScene.AdminTasks;

import backend.AuthManager;
import backend.User;
import frontend.HomeScene.AdminHomeScene;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox; // Import HBox
import javafx.stage.Stage;

import java.util.List;

public class ListUsersScene {
    private Stage primaryStage;
    private AuthManager authManager;
    private int currentPage;
    private static final int USERS_PER_PAGE = 5;
    private Button leftArrowButton;
    private Button rightArrowButton;
    private Button backButton;
    private VBox vbox; // VBox for the main layout
    private HBox buttonBox; // HBox for the buttons

    public ListUsersScene(Stage primaryStage, AuthManager authManager) {
        this.primaryStage = primaryStage;
        this.authManager = authManager;
        this.currentPage = 0; // Start at the first page
        this.vbox = new VBox(10); // Initialize VBox
        this.buttonBox = new HBox(10); // Initialize HBox for buttons
        initializeButtons(); // Initialize buttons once
    }

    private void initializeButtons() {
        // Initialize buttons only once
        leftArrowButton = new Button("<");
        rightArrowButton = new Button(">");
        backButton = new Button("Back");

        // Set actions for buttons
        leftArrowButton.setOnAction(e -> {
            if (currentPage > 0) {
                currentPage--;
                updateUserList();
            }
        });

        rightArrowButton.setOnAction(e -> {
            if ((currentPage + 1) * USERS_PER_PAGE < authManager.getAllUsers().size()) {
                currentPage++;
                updateUserList();
            }
        });

        backButton.setOnAction(e -> primaryStage.setScene(new AdminHomeScene(primaryStage, authManager).createAdminHomeScene()));
        
        // Add buttons to the buttonBox (HBox)
        buttonBox.getChildren().addAll(leftArrowButton, rightArrowButton, backButton);
        
        // Add the buttonBox to the vbox (this will be added last)
        vbox.getChildren().add(buttonBox);
    }

    public Scene createListUsersScene() {
        updateUserList(); // Load the first page of users
        return new Scene(vbox, 300, 400);
    }

    // Method to update the user list based on the current page
    private void updateUserList() {
        // Clear previous user entries
        vbox.getChildren().removeIf(node -> node instanceof Label && node != buttonBox);

        // Get the list of all users
        List<User> allUsers = authManager.getAllUsers();

        // Calculate the start and end index for the current page
        int startIndex = currentPage * USERS_PER_PAGE;
        int endIndex = Math.min(startIndex + USERS_PER_PAGE, allUsers.size());

        // Add users for the current page
        for (int i = startIndex; i < endIndex; i++) {
            User user = allUsers.get(i);
            Label userInfo = new Label("Username: " + user.getUsername() + ", Name: "
                    + user.getFirstName() + " " + user.getLastName()
                    + ", Roles: " + user.getRoles().toString());
            vbox.getChildren().add(userInfo); // Add user info to the VBox
        }

        // Update button visibility based on current page
        leftArrowButton.setDisable(currentPage == 0);
        rightArrowButton.setDisable((currentPage + 1) * USERS_PER_PAGE >= allUsers.size());
    }
}
