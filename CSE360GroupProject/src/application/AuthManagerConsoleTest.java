package application;

import backend.*;
import java.util.HashSet;
import java.util.Set;

public class AuthManagerConsoleTest {
    private AuthManager authManager;
    private User admin;

    public AuthManagerConsoleTest() {
        authManager = new AuthManager();
        admin = authManager.createFirstUser("admin", "adminpass"); // Create the first user as admin
    }

    public void runTests() {
        testCreateFirstUser();
        testCreateUser();
        testLogin();
        testCompleteAccountSetup();
        testFindUserByUsername();
        testUserExistsForEmail();
        testAddRole();
        testRemoveRole();
        testDeleteUser();
        testInviteUser();
        testRequestPasswordReset();
        testUpdateUserPassword();
        testDeleteInvitation();
        testListUsers();
        testListInvitations();
    }

    private void reinitializeAuthManager() {
        authManager = new AuthManager();
        admin = authManager.createFirstUser("admin", "adminpass"); // Create the first user as admin
    }

    private void testCreateFirstUser() {
        System.out.println("\n=====Test 1: Testing createFirstUser=====\n");
        if (admin != null && "admin".equals(admin.getUsername())) {
            System.out.println("SUCCESS: Admin created successfully.\n");
        } else {
            System.out.println("FAILURE: Admin creation failed.\n");
        }

        if (authManager.createFirstUser("newadmin", "newpass") == null) {
            System.out.println("SUCCESS: Cannot create a second admin.\n");
        } else {
            System.out.println("FAILURE: Should not be able to create a second admin.\n");
        }

        authManager.listUsers();
    }

    private void testCreateUser() {
        reinitializeAuthManager(); // Reinitialize for fresh tests
        System.out.println("\n=====Test 2: Testing createUser=====\n");
        Set<Role> roles = new HashSet<>();
        roles.add(Role.STUDENT);
        User newUser = authManager.createUser("user1", "userpass", roles);
        
        if (newUser != null && "user1".equals(newUser.getUsername())) {
            System.out.println("SUCCESS: New user created successfully.\n");
        } else {
            System.out.println("FAILURE: New user creation failed.\n");
        }

        if (authManager.getAllUsers().contains(newUser)) {
            System.out.println("SUCCESS: New user is in the user list.\n");
        } else {
            System.out.println("FAILURE: New user should be in the user list.\n");
        }

        authManager.listUsers();
    }

    private void testLogin() {
        reinitializeAuthManager(); // Reinitialize for fresh tests
        System.out.println("\n=====Test 3: Testing login=====\n");
        User loggedInUser = authManager.login("admin", "adminpass");
        if (loggedInUser != null && "admin".equals(loggedInUser.getUsername())) {
            System.out.println("SUCCESS: User logged in successfully.\n");
        } else {
            System.out.println("FAILURE: User login failed.\n");
        }

        if (authManager.login("admin", "wrongpass") == null) {
            System.out.println("SUCCESS: Login failed with incorrect password.\n");
        } else {
            System.out.println("FAILURE: Login should fail with incorrect password.\n");
        }

        if (authManager.login("unknown", "adminpass") == null) {
            System.out.println("SUCCESS: Login failed with unknown username.\n");
        } else {
            System.out.println("FAILURE: Login should fail with unknown username.\n");
        }

        authManager.listUsers();
    }

    private void testCompleteAccountSetup() {
        reinitializeAuthManager(); // Reinitialize for fresh tests
        System.out.println("\n=====Test 4: Testing completeAccountSetup=====\n");
        boolean result = authManager.completeAccountSetup(admin, "John", "M", "Doe", "Johnny", "john.doe@example.com");
        if (result) {
            System.out.println("SUCCESS: Account setup completed successfully.\n");
        } else {
            System.out.println("FAILURE: Account setup failed.\n");
        }

        // Validate the user's details after setup
        if ("John".equals(admin.getFirstName()) && "Doe".equals(admin.getLastName()) &&
            "john.doe@example.com".equals(admin.getEmail()) && admin.isSetupComplete()) {
            System.out.println("SUCCESS: User details are correct after setup.\n");
        } else {
            System.out.println("FAILURE: User details are incorrect after setup.\n");
        }

        authManager.listUsers();
    }

    private void testFindUserByUsername() {
        reinitializeAuthManager(); // Reinitialize for fresh tests
        System.out.println("\n=====Test 5: Testing findUserByUsername=====\n");
        User foundUser = authManager.findUserByUsername("admin");
        if (foundUser != null && "admin".equals(foundUser.getUsername())) {
            System.out.println("SUCCESS: Admin found successfully.\n");
        } else {
            System.out.println("FAILURE: Admin should be found.\n");
        }

        if (authManager.findUserByUsername("nonexistent") == null) {
            System.out.println("SUCCESS: Nonexistent user not found.\n");
        } else {
            System.out.println("FAILURE: Nonexistent user should not be found.\n");
        }

        authManager.listUsers();
    }

    private void testUserExistsForEmail() {
        reinitializeAuthManager(); // Reinitialize for fresh tests
        System.out.println("\n=====Test 6: Testing userExistsForEmail=====\n");
        authManager.completeAccountSetup(admin, "John", "M", "Doe", "Johnny", "john.doe@example.com");
        if (authManager.userExistsForEmail("john.doe@example.com")) {
            System.out.println("SUCCESS: Email is registered.\n");
        } else {
            System.out.println("FAILURE: Email should be registered.\n");
        }

        if (!authManager.userExistsForEmail("unknown@example.com")) {
            System.out.println("SUCCESS: Unknown email is not registered.\n");
        } else {
            System.out.println("FAILURE: Unknown email should not be registered.\n");
        }

        authManager.listUsers();
    }

    private void testAddRole() {
        reinitializeAuthManager(); // Reinitialize for fresh tests
        System.out.println("\n=====Test 7: Testing addRole=====\n");
        Set<Role> roles = new HashSet<>();
        roles.add(Role.STUDENT);
        User newUser = authManager.createUser("user2", "userpass", roles);
        
        authManager.addRole(newUser, Role.ADMIN);
        if (newUser.getRoles().contains(Role.ADMIN)) {
            System.out.println("SUCCESS: ADMIN role added successfully.\n");
        } else {
            System.out.println("FAILURE: ADMIN role should have been added.\n");
        }

        authManager.listUsers();
    }

    private void testRemoveRole() {
        reinitializeAuthManager(); // Reinitialize for fresh tests
        System.out.println("\n=====Test 8: Testing removeRole=====\n");
        Set<Role> roles = new HashSet<>();
        roles.add(Role.ADMIN);
        User newUser = authManager.createUser("user3", "userpass", roles);
        
        authManager.removeRole(newUser, Role.ADMIN);
        if (!newUser.getRoles().contains(Role.ADMIN)) {
            System.out.println("SUCCESS: ADMIN role removed successfully.\n");
        } else {
            System.out.println("FAILURE: ADMIN role should have been removed.\n");
        }

        authManager.listUsers();
    }

    private void testDeleteUser() {
        reinitializeAuthManager(); // Reinitialize for fresh tests
        System.out.println("\n=====Test 9: Testing deleteUser=====\n");
        Set<Role> roles = new HashSet<>();
        roles.add(Role.STUDENT);
        User newUser = authManager.createUser("user4", "userpass", roles);

        if (authManager.deleteUser(newUser)) {
            System.out.println("SUCCESS: User deleted successfully.\n");
        } else {
            System.out.println("FAILURE: User should have been deleted.\n");
        }

        if (!authManager.getAllUsers().contains(newUser)) {
            System.out.println("SUCCESS: Deleted user is not in the user list.\n");
        } else {
            System.out.println("FAILURE: Deleted user should not be in the user list.\n");
        }

        authManager.listUsers();
    }

    private void testInviteUser() {
        reinitializeAuthManager(); // Reinitialize for fresh tests
        System.out.println("\n=====Test 10: Testing inviteUser=====\n");
        Set<Role> roles = new HashSet<>();
        roles.add(Role.INSTRUCTOR);
        String invitationCode = authManager.inviteUser("invitedUser", "invite@example.com", roles);
        
        if (invitationCode != null && authManager.isUserInvited(invitationCode)) {
            System.out.println("SUCCESS: User invited successfully.\n");
        } else {
            System.out.println("FAILURE: User invitation failed.\n");
        }

        authManager.listInvitations();
    }

    private void testRequestPasswordReset() {
        reinitializeAuthManager(); // Reinitialize for fresh tests
        System.out.println("\n=====Test 11: Testing requestPasswordReset=====\n");
        authManager.requestPasswordReset("john.doe@example.com");
        if (authManager.findRequestByEmail("john.doe@example.com") != null) {
            System.out.println("SUCCESS: Reset request found for the email.\n");
        } else {
            System.out.println("FAILURE: Reset request should be found for the email.\n");
        }

        authManager.listUsers();
    }

    private void testUpdateUserPassword() {
        reinitializeAuthManager(); // Reinitialize for fresh tests
        System.out.println("\n=====Test 12: Testing updateUserPassword=====\n");
        Set<Role> roles = new HashSet<>();
        roles.add(Role.STUDENT);
        User newUser = authManager.createUser("updatetestuser", "userpass", roles);
        authManager.completeAccountSetup(newUser, "John", "M", "Doe", "Johnny", "john.doe@example.com");
        authManager.updateUserPassword("john.doe@example.com", "newuserpass");
        
        if (authManager.login("updatetestuser", "newuserpass") != null) {
            System.out.println("SUCCESS: Password updated successfully.\n");
        } else {
            System.out.println("FAILURE: Password update failed.\n");
        }

        authManager.listUsers();
    }

    private void testDeleteInvitation() {
        reinitializeAuthManager(); // Reinitialize for fresh tests
        System.out.println("\n=====Test 13: Testing deleteInvitation=====\n");
        String invitationCode = authManager.inviteUser("invitedUser2", "invite2@example.com", new HashSet<>(Set.of(Role.STUDENT)));
        authManager.deleteInvitation(invitationCode);
        if (authManager.getInvitationFromInvitationCode(invitationCode) == null) {
            System.out.println("SUCCESS: Invitation deleted successfully.\n");
        } else {
            System.out.println("FAILURE: Invitation should have been deleted.\n");
        }

        authManager.listInvitations();
    }

    private void testListUsers() {
        reinitializeAuthManager(); // Reinitialize for fresh tests
        System.out.println("\n=====Test 14: Testing listUsers=====\n");
        authManager.listUsers();
    }

    private void testListInvitations() {
        reinitializeAuthManager(); // Reinitialize for fresh tests
        testInviteUser();
        System.out.println("\n=====Test 15: Testing listInvitations=====\n");
        authManager.listInvitations();
    }

    public static void main(String[] args) {
        AuthManagerConsoleTest test = new AuthManagerConsoleTest();
        test.runTests();
    }
}
