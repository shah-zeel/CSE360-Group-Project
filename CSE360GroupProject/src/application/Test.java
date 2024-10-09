import backend.*;
import java.util.*;

public class TestMain {

    public static void main(String[] args) {
        AuthManager authManager = new AuthManager();

        // =========== Scenario 1: Admin Creation ===========
        System.out.println("=== Scenario 1: Admin Creation ===");
        User firstAdmin = authManager.createFirstUser("adminUser", "adminPass");
        assert firstAdmin != null : "Failed to create first admin user";
        System.out.println("Admin account created successfully!");

        // =========== Scenario 2: Admin Invitation ===========
        System.out.println("\n=== Scenario 2: Admin Invites New User (Student) ===");
        Set<Role> studentRoles = new HashSet<>(Collections.singletonList(Role.STUDENT));
        String invitationCode = authManager.inviteUser("studentUser", "student@example.com", studentRoles);
        assert invitationCode != null : "Failed to generate invitation code";
        System.out.println("Invitation code generated for student: " + invitationCode);

        // =========== Scenario 3: User Logs in with Invitation Code ===========
        System.out.println("\n=== Scenario 3: Student logs in with invitation code ===");
        boolean isValidInvite = authManager.isUserInvited(invitationCode);
        assert isValidInvite : "Invitation code is invalid or expired";
        User newStudent = authManager.createUser("studentUser", "studentPass", studentRoles);
        authManager.completeAccountSetup(newStudent, "John", "D", "Doe", "Johnny", "student@example.com");
        System.out.println("Student account created successfully!");

        // =========== Scenario 4: User Login (Invalid) ===========
        System.out.println("\n=== Scenario 4: Invalid login attempt ===");
        User invalidLogin = authManager.login("wrongUser", "wrongPass");
        assert invalidLogin == null : "Expected invalid login";
        System.out.println("Error: Incorrect username or password");

        // =========== Scenario 5: Valid Login for New User ===========
        System.out.println("\n=== Scenario 5: Valid Login (Student) ===");
        User validStudentLogin = authManager.login("studentUser", "studentPass");
        assert validStudentLogin != null : "Student login failed";
        System.out.println("Student logged in successfully!");

        // =========== Scenario 6: Multiple Roles ===========
        System.out.println("\n=== Scenario 6: User with multiple roles ===");
        Set<Role> instructorRole = new HashSet<>(Arrays.asList(Role.INSTRUCTOR, Role.STUDENT));
        String multiRoleInviteCode = authManager.inviteUser("dualRoleUser", "dual@example.com", instructorRole);
        User dualRoleUser = authManager.createUser("dualRoleUser", "dualPass", instructorRole);
        authManager.completeAccountSetup(dualRoleUser, "Jane", "A", "Smith", "Janie", "dual@example.com");

        System.out.println("User with multiple roles logged in. Selecting role 'Instructor'...");
        // Simulate selecting a role
        assert dualRoleUser.getRoles().contains(Role.INSTRUCTOR) : "Expected user to have Instructor role";
        System.out.println("Instructor role selected");

        System.out.println("User logs out, logging in as 'Student'...");
        // Simulate logging out and logging in as a different role
        assert dualRoleUser.getRoles().contains(Role.STUDENT) : "Expected user to have Student role";
        System.out.println("Student role selected");

        // =========== Scenario 7: Password Reset ===========
        System.out.println("\n=== Scenario 7: Password Reset ===");
        authManager.requestPasswordReset("student@example.com");
        ResetRequest resetRequest = authManager.findRequestByEmail("student@example.com");
        assert resetRequest != null : "Password reset request failed";
        boolean passwordUpdated = authManager.updateUserPassword("student@example.com", "newStudentPass");
        assert passwordUpdated : "Password reset failed";
        System.out.println("Password reset successful");

        // =========== Scenario 8: User List Management ===========
        System.out.println("\n=== Scenario 8: User List Management ===");
        authManager.listUsers(); // Print out list of users

        // =========== Scenario 9: Role Modification by Admin ===========
        System.out.println("\n=== Scenario 9: Role Modification by Admin ===");
        authManager.addRole(validStudentLogin, Role.INSTRUCTOR);
        assert validStudentLogin.getRoles().contains(Role.INSTRUCTOR) : "Failed to add Instructor role";
        System.out.println("Added Instructor role to student");

        // =========== Scenario 10: User Deletion ===========
        System.out.println("\n=== Scenario 10: Deleting a user ===");
        boolean isDeleted = authManager.deleteUser(newStudent);
        assert isDeleted : "Failed to delete user";
        System.out.println("User deleted successfully");

        // =========== Scenario 11: Error Handling ===========
        System.out.println("\n=== Scenario 11: Error Handling (Missing fields) ===");
        try {
            authManager.completeAccountSetup(validStudentLogin, "", "", "Doe", "", "invalid_email");
            assert false : "Account setup should fail due to missing fields";
        } catch (Exception e) {
            System.out.println("Error: Account setup failed due to missing fields");
        }

        System.out.println("\n=== All scenarios completed successfully! ===");
    }
}
