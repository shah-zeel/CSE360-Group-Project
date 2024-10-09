package backend;

import java.util.*;

/**
 * <p> Title: Invitation Class. </p>
 * 
 * <p> Description: A class to manage user invitations with unique codes, associated usernames, emails, roles, and usage status. </p>
 * 
 * @author Zeel Tejashkumar Shah
 * 
 * @version 1.0	2024-10-08	Initial implementation
 */

public class Invitation {
    private String invitationCode; // Unique code for the invitation
    private String username; // Username of the invited user
    private String email; // Email of the invited user
    private Set<Role> roles; // Roles assigned to the invited user
    private boolean isUsed; // Status indicating if the invitation has been used

    // Static list to store all invitations
    private static List<Invitation> invitations = new ArrayList<>();

    /**
     * Constructor to create a new invitation.
     * 
     * @param username The username of the invited user
     * @param email The email of the invited user
     * @param roles The roles to assign to the invited user
     */
    public Invitation(String username, String email, Set<Role> roles) {
        this.invitationCode = UUID.randomUUID().toString(); // Generate unique code
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.isUsed = false; // Invitation is initially not used
    }

    // Getters

    /**
     * Get the invitation code.
     * 
     * @return The unique invitation code
     */
    public String getInvitationCode() {
        return invitationCode;
    }

    /**
     * Get the username of the invited user.
     * 
     * @return The username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get the email of the invited user.
     * 
     * @return The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Get the roles assigned to the invited user.
     * 
     * @return The set of roles
     */
    public Set<Role> getRoles() {
        return roles;
    }

    /**
     * Check if the invitation has been used.
     * 
     * @return True if used, false otherwise
     */
    public boolean isUsed() {
        return isUsed;
    }

    // Mark the invitation as used
    /**
     * Mark the invitation as used.
     */
    public void markAsUsed() {
        this.isUsed = true;
    }

    // Method to create and store an invitation
    /**
     * Create and store a new invitation.
     * 
     * @param username The username of the invited user
     * @param email The email of the invited user
     * @param roles The roles to assign to the invited user
     * @return The unique invitation code
     */
    public static String inviteUser(String username, String email, Set<Role> roles) {
        Invitation invitation = new Invitation(username, email, roles);
        invitations.add(invitation);
        // In a real application, you would send the email here
        return invitation.getInvitationCode(); // Return the invitation code
    }

    // Method to retrieve an invitation by code
    /**
     * Retrieve an invitation by its unique code.
     * 
     * @param invitationCode The code of the invitation to retrieve
     * @return The Invitation object if found and not used, otherwise null
     */
    public static Invitation getInvitationByCode(String invitationCode) {
        for (Invitation invite : invitations) {
            if (invite.getInvitationCode().equals(invitationCode) && !invite.isUsed()) {
                return invite; // Return the invitation if it is valid and not used
            }
        }
        return null; // Invitation not found or already used
    }

    // Method to delete an invitation
    /**
     * Delete an invitation by its code.
     * 
     * @param invitationCode The code of the invitation to delete
     * @return True if the invitation was successfully deleted, false otherwise
     */
    public static boolean deleteInvitation(String invitationCode) {
        return invitations.removeIf(invite -> invite.getInvitationCode().equals(invitationCode));
    }

    // List all invitations (for debugging or admin purposes)
    /**
     * List all invitations for debugging or administrative purposes.
     */
    public static void listInvitations() {
        for (Invitation invite : invitations) {
            System.out.println("Invitation Code: " + invite.getInvitationCode());
            System.out.println("Username: " + invite.getUsername());
            System.out.println("Email: " + invite.getEmail());
            System.out.println("Roles: " + invite.getRoles());
            System.out.println("Used: " + invite.isUsed());
            System.out.println(); // Print a newline for better readability
        }
    }
}
