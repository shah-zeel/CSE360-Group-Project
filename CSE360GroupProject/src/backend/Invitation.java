package backend;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class Invitation {
    private String invitationCode;
    private String username;
    private String email;
    private Set<Role> roles;
    private boolean isUsed;

    // Static list to store all invitations
    private static List<Invitation> invitations = new ArrayList<>();

    // Constructor
    public Invitation(String username, String email, Set<Role> roles) {
        this.invitationCode = UUID.randomUUID().toString(); // Generate unique code
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.isUsed = false; // Invitation is initially not used
    }

    // Getters
    public String getInvitationCode() {
        return invitationCode;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public boolean isUsed() {
        return isUsed;
    }

    // Mark the invitation as used
    public void markAsUsed() {
        this.isUsed = true;
    }

    // Method to create and store an invitation
    public static String inviteUser(String username, String email, Set<Role> roles) {
        Invitation invitation = new Invitation(username, email, roles);
        invitations.add(invitation);
        // In a real application, you would send the email here
        return invitation.getInvitationCode(); // Return the invitation code
    }

    // Method to retrieve an invitation by code
    public static Invitation getInvitationByCode(String invitationCode) {
        for (Invitation invite : invitations) {
            if (invite.getInvitationCode().equals(invitationCode) && !invite.isUsed()) {
                return invite; // Return the invitation if it is valid and not used
            }
        }
        return null; // Invitation not found or already used
    }

    // Method to delete an invitation
    public static boolean deleteInvitation(String invitationCode) {
        return invitations.removeIf(invite -> invite.getInvitationCode().equals(invitationCode));
    }

    // List all invitations (for debugging or admin purposes)
    public static void listInvitations() {
        for (Invitation invite : invitations) {
            System.out.println("Invitation Code: " + invite.getInvitationCode());
            System.out.println("Username: " + invite.getUsername());
            System.out.println("Email: " + invite.getEmail());
            System.out.println("Roles: " + invite.getRoles());
            System.out.println("Used: " + invite.isUsed());
            System.out.println();
        }
    }
}
