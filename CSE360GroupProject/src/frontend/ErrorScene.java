package frontend;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class ErrorScene {
    private Stage primaryStage;

    public ErrorScene(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
