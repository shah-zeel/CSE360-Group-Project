package backend;

import java.util.Set;

/**
 * <p> Title: User Class. </p>
 * 
 * <p> Description: represent a system user with associated attributes and roles.
 * 
 * @author Zeel Tejashkumar Shah
 * 
 * @version 1.0	2024-10-08	Initial implementation
 */

public class User {
    private String username;          // Unique username for the user
    private String password;          // User's password
    private String firstName;         // User's first name
    private String middleName;        // User's middle name (optional)
    private String lastName;          // User's last name
    private String preferredName;     // User's preferred name (optional)
    private String email;             // User's email address
    private Set<Role> roles;          // Set of roles assigned to the user
    private boolean isSetupComplete;   // Flag indicating if the user's setup is complete

    /**
     * Constructor to initialize a new User object.
     * 
     * @param username the username for the new user
     * @param password the password for the new user
     * @param roles the set of roles assigned to the user
     */
    public User(String username, String password, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.isSetupComplete = false;  // New users need to finish setup
    }

    // Getters and Setters

    public String getUsername() {
        return username; // Returns the username
    }

    public void setUsername(String username) {
        this.username = username; // Sets the username
    }

    public String getPassword() {
        return password; // Returns the password
    }

    public void setPassword(String password) {
        this.password = password; // Sets the password
    }

    public String getFirstName() {
        return firstName; // Returns the first name
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName; // Sets the first name
    }

    public String getMiddleName() {
        return middleName; // Returns the middle name
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName; // Sets the middle name
    }

    public String getLastName() {
        return lastName; // Returns the last name
    }

    public void setLastName(String lastName) {
        this.lastName = lastName; // Sets the last name
    }

    public String getPreferredName() {
        return preferredName; // Returns the preferred name
    }

    public void setPreferredName(String preferredName) {
        this.preferredName = preferredName; // Sets the preferred name
    }

    public String getEmail() {
        return email; // Returns the email address
    }

    public void setEmail(String email) {
        this.email = email; // Sets the email address
    }

    public Set<Role> getRoles() {
        return roles; // Returns the set of roles assigned to the user
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles; // Sets the roles for the user
    }

    public boolean isSetupComplete() {
        return isSetupComplete; // Returns whether the setup is complete
    }

    public void setSetupComplete(boolean setupComplete) {
        isSetupComplete = setupComplete; // Sets the setup complete flag
    }
}
