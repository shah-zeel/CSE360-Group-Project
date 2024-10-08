package backend;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class Invitations {
    // Store invitations with a unique code
    private Map<String, Invitation> invitations = new HashMap<>();

    public String inviteUser(String username, String email, Set<Role> roles) {
        // Generate a unique one-time invitation code
        String invitationCode = UUID.randomUUID().toString();
        // Create and store the invitation
        Invitation invitation = new Invitation(username, email, roles, Instant.now().plus(3, ChronoUnit.DAYS));
        invitations.put(invitationCode, invitation);

        // In a real application, you would send the email here

        return invitationCode; // Return the invitation code
    }

    public User getUserFromInvitationCode(String invitationCode) {
        Invitation invitation = invitations.get(invitationCode);
        if (invitation != null) {
            // Create a new User object based on the invitation
            User user = new User(invitation.username, invitation.email, invitation.roles);
            return user; // Return the user associated with the invitation
        }
        return null; // Invalid invitation code
    }

    // Method to check if the invitation is still valid
    public boolean inviteiIsValid(String invitationCode) {
        return Instant.now().isBefore(invitations.get(invitationCode).expirationTime);
    }
    
    // Inner class to represent an invitation
    private class Invitation {
        String username;
        String email;
        Set<Role> roles;
        Instant expirationTime;

        Invitation(String username, String email, Set<Role> roles, Instant expirationTime) {
            this.username = username;
            this.email = email;
            this.roles = roles;
            this.expirationTime = expirationTime; 
        }
    }
}
