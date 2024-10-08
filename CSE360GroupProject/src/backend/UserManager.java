package backend;

import java.util.*;

public class UserManager {
	private List<User> users = new ArrayList<>();

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

	// Reset password
	public boolean resetPassword(User user, String newPassword) {
		user.setPassword(newPassword);
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
	public List<User> getUsers() {
		return users;
	}

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

	// Invite a new user (this is simplified, in real cases you'd generate a
	// one-time code)
	public User inviteUser(String username, String password, Set<Role> roles) {
		return createUser(username, password, roles);
	}
}