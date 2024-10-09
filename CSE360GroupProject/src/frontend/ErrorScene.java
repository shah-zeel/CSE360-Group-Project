package frontend;

import javafx.scene.control.Alert;

/**
 * <p> Title: ErrorScene Class. </p>
 * 
 * <p> Description: A class responsible for displaying error messages to the user through a pop-up alert dialog. 
 * It uses JavaFX's Alert system to notify users of errors. </p>
 * 
 * @author Zeel Tejashkumar Shah
 * 
 * @version 1.0 2024-10-09 Initial implementation
 */

public class ErrorScene {

    /**
     * Displays an error message in an alert dialog.
     *
     * @param message The error message to be displayed.
     */
    public void showError(String message) {
        // Create a new Alert dialog of type ERROR
        Alert alert = new Alert(Alert.AlertType.ERROR);
        
        // Set the alert title to "Error"
        alert.setTitle("Error");
        
        // Do not display any header text
        alert.setHeaderText(null);
        
        // Set the content of the alert to the provided message
        alert.setContentText(message);
        
        // Show the alert and wait for the user to close it
        alert.showAndWait();
    }
}
