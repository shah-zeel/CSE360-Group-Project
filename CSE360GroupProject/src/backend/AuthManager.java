package backend;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*; 

/**
 * <p> Title: AuthManager Class. </p>
 * 
 * <p> Description: A class for managing user authentication and related operations, including user management, role management, invitation management, and password reset functionalities. </p>
 * 
 * @author Zeel Tejashkumar Shah
 * 
 * @version 1.0	2024-10-08	Initial implementation
 */

public class AuthManager {
	private List<User> users; // List to store registered users
	private List<Invitation> invitations; // List to store invitations
	private List<ResetRequest> resetRequests; // List to store password reset requests

	// Constructor
	public AuthManager() {
		this.users = new ArrayList<>(); // Initialize the list of users
		this.invitations = new ArrayList<>(); // Initialize the list of invitations
		this.resetRequests = new ArrayList<>(); // Initialize the list of reset requests
	}

	// ========== User Management Methods ========== //

	/**
	 * Check if any user exists (if not, create an Admin)
	 * 
	 * @param username The username for the first user
	 * @param password The password for the first user
	 * @return The created admin user or null if an admin already exists
	 */
	public User createFirstUser(String username, String password) {
		if (users.isEmpty()) { // Check if the user list is empty
			User admin = new User(username, password, new HashSet<>(Arrays.asList(Role.ADMIN))); // Create admin user
			users.add(admin); // Add admin to the user list
			return admin; // Return the created admin user
		}
		return null; // Return null if admin already exists
	}

	/**
	 * Create a user with roles based on an invitation
	 * 
	 * @param username The username for the new user
	 * @param password The password for the new user
	 * @param roles The set of roles assigned to the new user
	 * @return The newly created user
	 */
	public User createUser(String username, String password, Set<Role> roles) {
		User newUser = new User(username, password, roles); // Create new user
		users.add(newUser); // Add new user to the list
		return newUser; // Return the created user
	}

	/**
	 * Login
	 * 
	 * @param username The username of the user attempting to login
	 * @param password The password of the user attempting to login
	 * @return The logged-in user or null if login fails
	 */
	public User login(String username, String password) {
		for (User user : users) { // Iterate through the list of users
			if (user.getUsername().equals(username) && user.getPassword().equals(password)) { // Check for matching credentials
				return user; // Return the logged-in user
			}
		}
		return null; // Return null if login fails
	}

	/**
	 * Complete account setup
	 * 
	 * @param user The user for whom the account setup is being completed
	 * @param firstName The first name of the user
	 * @param middleName The middle name of the user
	 * @param lastName The last name of the user
	 * @param preferredName The preferred name of the user
	 * @param email The email of the user
	 * @return true if account setup is completed successfully
	 */
	public boolean completeAccountSetup(User user, String firstName, String middleName, String lastName,
			String preferredName, String email) {
		user.setFirstName(firstName); // Set the user's first name
		user.setMiddleName(middleName); // Set the user's middle name
		user.setLastName(lastName); // Set the user's last name
		user.setPreferredName(preferredName); // Set the user's preferred name
		user.setEmail(email); // Set the user's email
		user.setSetupComplete(true); // Mark the account setup as complete
		return true; // Return true to indicate success
	}

	/**
	 * Find user by username
	 * 
	 * @param username The username to search for
	 * @return The found user or null if not found
	 */
	public User findUserByUsername(String username) {
		for (User user : users) { // Iterate through the list of users
			if (user.getUsername().equals(username)) { // Check for matching username
				return user; // Return the found user
			}
		}
		return null; // Return null if user not found
	}

	/**
	 * Check if user exists for email
	 * 
	 * @param email The email to search for
	 * @return true if found email or false if not found
	 */
	public boolean userExistsForEmail(String email) {
	    for (User user : users) {
	        // Check if the user's email is not null and if it matches the provided email (case-insensitive)
	        if (user.getEmail() != null && user.getEmail().equalsIgnoreCase(email)) {
	            return true; // User exists
	        }
	    }
	    return false; // No user found with the given email
	}
	
	/**
	 * List all users
	 * 
	 * This method prints the details of all users in the system.
	 */
	public void listUsers() {
		for (User user : users) { // Iterate through the list of users
			System.out.println("Username: " + user.getUsername()); // Print username
			System.out.println("Name: " + user.getFirstName() + " " + user.getLastName()); // Print user's full name
			System.out.println("Roles: " + user.getRoles()); // Print user's roles
			System.out.println("Setup Complete: " + user.isSetupComplete()); // Print setup status
			System.out.println(); // Print a newline for better readability
		}
	}

	/**
	 * Method to return the list of users
	 * 
	 * @return List of all registered users
	 */
	public List<User> getAllUsers() {
		return users; // Return the list of users
	}

	// ========== Role Management Methods ========== //

	/**
	 * Add role to user
	 * 
	 * @param user The user to whom the role will be added
	 * @param role The role to be added to the user
	 */
	public void addRole(User user, Role role) {
		user.getRoles().add(role); // Add the specified role to the user
	}

	/**
	 * Remove role from user
	 * 
	 * @param user The user from whom the role will be removed
	 * @param role The role to be removed from the user
	 */
	public void removeRole(User user, Role role) {
		user.getRoles().remove(role); // Remove the specified role from the user
	}

	/**
	 * Delete user
	 * 
	 * @param user The user to be deleted
	 * @return true if the user was successfully removed, false otherwise
	 */
	public boolean deleteUser(User user) {
		return users.remove(user); // Remove the user from the list and return the result
	}

	// ========== Invitation Management Methods ========== //

	/**
	 * Invite a new user
	 * 
	 * @param username The username for the invited user
	 * @param email The email for the invited user
	 * @param roles The roles assigned to the invited user
	 * @return The invitation code for the newly created invitation
	 */
	public String inviteUser(String username, String email, Set<Role> roles) {
		Invitation newInvitation = new Invitation(username, email, roles); // Create a new invitation
		invitations.add(newInvitation); // Add invitation to the list
		System.out.println("Invitation code for " + email + " : " + newInvitation.getInvitationCode()); // Print invitation code
		return newInvitation.getInvitationCode(); // Return the invitation code
	}

	/**
	 * Check if a user is invited
	 * 
	 * @param invitationCode The invitation code to check
	 * @return true if the invitation exists and is not used, false otherwise
	 */
	public boolean isUserInvited(String invitationCode) {
		for (Invitation invitation : invitations) { // Iterate through the list of invitations
			if (invitation.getInvitationCode().equals(invitationCode) && !invitation.isUsed()) { // Check for matching invitation code and usage status
				return true; // Invitation exists and is not used
			}
		}
		return false; // Invitation does not exist or has been used
	}

	/**
	 * Get invitation details from an invitation code
	 * 
	 * @param invitationCode The invitation code to search for
	 * @return The invitation object if found, null otherwise
	 */
	public Invitation getInvitationFromInvitationCode(String invitationCode) {
		for (Invitation invitation : invitations) { // Iterate through the list of invitations
			if (invitation.getInvitationCode().equals(invitationCode)) { // Check for matching invitation code
				return invitation; // Return the invitation object
			}
		}
		return null; // Invitation not found
	}

	/**
	 * Delete an invitation
	 * 
	 * @param invitationCode The invitation code to be deleted
	 */
	public void deleteInvitation(String invitationCode) {
		invitations.removeIf(invitation -> invitation.getInvitationCode().equals(invitationCode)); // Remove the invitation with the matching code
	}

	/**
	 * List all invitations
	 * 
	 * This method prints the details of all invitations in the system.
	 */
	public void listInvitations() {
		for (Invitation invitation : invitations) { // Iterate through the list of invitations
			System.out.println("Username: " + invitation.getUsername()); // Print username associated with the invitation
			System.out.println("Email: " + invitation.getEmail()); // Print email associated with the invitation
			System.out.println("Roles: " + invitation.getRoles()); // Print roles associated with the invitation
			System.out.println("Invitation Code: " + invitation.getInvitationCode()); // Print invitation code
			System.out.println("Used: " + invitation.isUsed()); // Print usage status
			System.out.println(); // Print a newline for better readability
		}
	}

	// ========== Password Reset Management Methods ========== //

	/**
	 * Request a password reset
	 * 
	 * @param email The email of the user requesting the reset
	 * @return void
	 */
	public void requestPasswordReset(String email) {
		String oneTimePassword = generateOneTimePassword(); // Implement this method to generate a secure OTP
		Instant expirationTime = Instant.now().plus(3, ChronoUnit.DAYS); // OTP valid for 3 days
		System.out.println("OTP for " + email + ": " + oneTimePassword + ", it expires at " + expirationTime);
		resetRequests.add(new ResetRequest(email, oneTimePassword, expirationTime));
	}

	/**
	 * Reset request
	 * 
	 * @param email The email of the user whose password will be reset
	 * @return request if email found or null
	 */
	// Find reset request by email
	public ResetRequest findRequestByEmail(String email) {
		for (ResetRequest request : resetRequests) {
			if (request.getEmail().equals(email)) {
				return request;
			}
		}
		return null; // No request found for the given email
	}

	/**
	 * Update user's password
	 * 
	 * @param email The email of the user whose password will be reset
	 * @param newPassword The new password to set
	 * @return true if the password was reset successfully, false otherwise
	 */
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

	/**
	 * Remove the reset request from the database
	 * 
	 * @param email The email of the user whose password will be reset
	 * @param newPassword The new password to set
	 * @return true if the password was reset successfully, false otherwise
	 */
	public void removeRequest(ResetRequest request) {
		resetRequests.remove(request);
	}
	
	
	/**
	 * Generate a secure one-time password
	 * 
	 * @return String
	 */
	private String generateOneTimePassword() {
		// Implementation for generating a secure OTP (e.g., random 6-digit number)
		return String.valueOf(new Random().nextInt(999999));
	}
}
