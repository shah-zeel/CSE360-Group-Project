package backend;

import java.time.Instant;

/**
 * <p> Title: ResetRequest Class. </p>
 * 
 * <p> Description: class to manage password reset requests, containing the user's email, a one-time password, and expiration time.
 * 
 * @author Zeel Tejashkumar Shah
 * 
 * @version 1.0	2024-10-08	Initial implementation
 */

public class ResetRequest {
    private String email; // User's email associated with the reset request
    private String oneTimePassword; // One-time password for authentication
    private Instant expirationTime; // Expiration time of the reset request

    /**
     * Constructor to create a new reset request.
     * 
     * @param email The email of the user requesting the password reset
     * @param oneTimePassword The one-time password for the reset request
     * @param expirationTime The time when the reset request expires
     */
    public ResetRequest(String email, String oneTimePassword, Instant expirationTime) {
        this.email = email;
        this.oneTimePassword = oneTimePassword;
        this.expirationTime = expirationTime;
    }

    // Getters

    /**
     * Get the email associated with the reset request.
     * 
     * @return The user's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Get the one-time password for the reset request.
     * 
     * @return The one-time password
     */
    public String getOneTimePassword() {
        return oneTimePassword;
    }

    /**
     * Get the expiration time of the reset request.
     * 
     * @return The expiration time as an Instant
     */
    public Instant getExpirationTime() {
        return expirationTime;
    }

    /**
     * Check if the reset request has expired.
     * 
     * @return True if the request is expired, false otherwise
     */
    public boolean isExpired() {
        return Instant.now().isAfter(expirationTime); // Compare current time with expiration time
    }
}
