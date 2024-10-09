package backend;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

// Class for managing user authentication and related operations
public class AuthManager {
	private List<User> users;
	private List<Invitation> invitations; // List to store invitations
	private List<ResetRequest> resetRequests;

	// Constructor
	public AuthManager() {
		this.users = new ArrayList<>();
		this.invitations = new ArrayList<>(); // Initialize the list of invitations
		this.resetRequests = new ArrayList<>();
	}

	// ========== User Management Methods ========== //

	// Check if any user exists (if not, create an Admin)
	public User createFirstUser(String username, String password) {
		if (users.isEmpty()) {
			User admin = new User(username, password, new HashSet<>(Arrays.asList(Role.ADMIN)));
			users.add(admin);
			return admin;
		}
		return null;
	}

	// Create a user with roles based on an invitation
	public User createUser(String username, String password, Set<Role> roles) {
		User newUser = new User(username, password, roles);
		users.add(newUser);
		return newUser;
	}

	// Login
	public User login(String username, String password) {
		for (User user : users) {
			if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
				return user;
			}
		}
		return null;
	}

	// Complete account setup
	public boolean completeAccountSetup(User user, String firstName, String middleName, String lastName,
			String preferredName, String email) {
		user.setFirstName(firstName);
		user.setMiddleName(middleName);
		user.setLastName(lastName);
		user.setPreferredName(preferredName);
		user.setEmail(email);
		user.setSetupComplete(true);
		return true;
	}

	// Find user by username
	public User findUserByUsername(String username) {
		for (User user : users) {
			if (user.getUsername().equals(username)) {
				return user;
			}
		}
		return null;
	}

	// List all users
	public void listUsers() {
		for (User user : users) {
			System.out.println("Username: " + user.getUsername());
			System.out.println("Name: " + user.getFirstName() + " " + user.getLastName());
			System.out.println("Roles: " + user.getRoles());
			System.out.println("Setup Complete: " + user.isSetupComplete());
			System.out.println();
		}
	}

	// Method to return the list of users
	public List<User> getAllUsers() {
		return users;
	}

	// ========== Role Management Methods ========== //

	// Add role to user
	public void addRole(User user, Role role) {
		user.getRoles().add(role);
	}

	// Remove role from user
	public void removeRole(User user, Role role) {
		user.getRoles().remove(role);
	}

	// Delete user
	public boolean deleteUser(User user) {
		return users.remove(user);
	}

	// ========== Invitation Management Methods ========== //

	// Invite a new user
	public String inviteUser(String username, String email, Set<Role> roles) {
		Invitation newInvitation = new Invitation(username, email, roles);
		invitations.add(newInvitation); // Add invitation to the list
		System.out.println("Invitation code for " + email + " : " + newInvitation.getInvitationCode());
		return newInvitation.getInvitationCode(); // Return the invitation code
	}

	// Check if a user is invited
	public boolean isUserInvited(String invitationCode) {
		for (Invitation invitation : invitations) {
			if (invitation.getInvitationCode().equals(invitationCode) && !invitation.isUsed()) {
				return true; // Invitation exists and is not used
			}
		}
		return false; // Invitation does not exist or has been used
	}

	// Get invitation details from an invitation code
	public Invitation getInvitationFromInvitationCode(String invitationCode) {
		for (Invitation invitation : invitations) {
			if (invitation.getInvitationCode().equals(invitationCode)) {
				return invitation; // Return the invitation object
			}
		}
		return null; // Invitation not found
	}

	// Delete an invitation
	public void deleteInvitation(String invitationCode) {
		invitations.removeIf(invitation -> invitation.getInvitationCode().equals(invitationCode));
	}

	// List all invitations
	public void listInvitations() {
		for (Invitation invitation : invitations) {
			System.out.println("Username: " + invitation.getUsername());
			System.out.println("Email: " + invitation.getEmail());
			System.out.println("Roles: " + invitation.getRoles());
			System.out.println("Invitation Code: " + invitation.getInvitationCode());
			System.out.println("Used: " + invitation.isUsed());
			System.out.println();
		}
	}

	// ========== Password Reset Methods ========== //

	// Request password reset
	public void requestPasswordReset(String email) {
		String oneTimePassword = generateOneTimePassword(); // Implement this method to generate a secure OTP
		Instant expirationTime = Instant.now().plus(3, ChronoUnit.DAYS); // OTP valid for 1 hour
		System.out.println("OTP for " + email + ": " + oneTimePassword + ", it expires at " + expirationTime);
		resetRequests.add(new ResetRequest(email, oneTimePassword, expirationTime));
	}

	// Find reset request by email
	public ResetRequest findRequestByEmail(String email) {
		for (ResetRequest request : resetRequests) {
			if (request.getEmail().equals(email)) {
				return request;
			}
		}
		return null; // No request found for the given email
	}

	public boolean updateUserPassword(String email, String newPassword) {
		// Loop through the list of users to find the user with the matching email
		for (User user : users) {
			if (user.getEmail().equals(email)) {
				// Update the user's password
				user.setPassword(newPassword);
				return true; // Exit after updating the password
			}
		}
		return false;
	}

	// Remove a reset request
	public void removeRequest(ResetRequest request) {
		resetRequests.remove(request);
	}

	// Check if a user with the given email exists
	public boolean userExistsForEmail(String email) {
	    for (User user : users) {
	        // Check if the user's email is not null and if it matches the provided email (case-insensitive)
	        if (user.getEmail() != null && user.getEmail().equalsIgnoreCase(email)) {
	            return true; // User exists
	        }
	    }
	    return false; // No user found with the given email
	}

	// ========== Helper Methods ========== //

	// Generate a secure one-time password
	private String generateOneTimePassword() {
		// Implementation for generating a secure OTP (e.g., random 6-digit number)
		return String.valueOf(new Random().nextInt(999999));
	}
}