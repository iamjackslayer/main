package seedu.clinicio.commons.core;

import static seedu.clinicio.model.staff.Role.RECEPTIONIST;

import seedu.clinicio.model.staff.Staff;

//@@author jjlee050
/**
 * A utility class to handle user session in ClinicIO
 */
public class UserSession {
    private static Staff currentUser;

    /**
     * Create a new user session for ClinicIO.
     * @param user Authenticated user to add into user session
     */
    public static void create(Staff user) {
        currentUser = user;
    }

    /**
     * Delete the user session from ClinicIO.
     */
    public static void destroy() {
        currentUser = null;
    }

    /**
     * Check if user is login to ClinicIO.
     * @return The status of user session.
     */
    public static boolean isLogin() {
        return currentUser != null;
    }

    /**
     * Check if user is login to ClinicIO as a receptionist.
     * @return The status of user session as receptionist.
     */
    public static boolean isLoginAsReceptionist() {
        return (currentUser != null) && (currentUser.getRole().equals(RECEPTIONIST));
    }
}
