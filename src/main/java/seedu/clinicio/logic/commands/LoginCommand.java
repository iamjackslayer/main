package seedu.clinicio.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_ROLE;

import seedu.clinicio.commons.core.EventsCenter;
import seedu.clinicio.commons.core.UserSession;
import seedu.clinicio.commons.events.ui.LoginSuccessEvent;
import seedu.clinicio.logic.CommandHistory;
import seedu.clinicio.logic.commands.exceptions.CommandException;

import seedu.clinicio.model.Model;
import seedu.clinicio.model.staff.Staff;

//@@author jjlee050

/**
 * Authenticate user and provide them access to ClinicIO based on the role.
 */
public class LoginCommand extends Command {

    public static final String COMMAND_WORD = "login";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Authenticate user to provide "
            + "user access to ClinicIO based on the roles.\n"
            + "Parameters: " + COMMAND_WORD
            + " " + PREFIX_ROLE + "ROLE"
            + " " + PREFIX_NAME + "NAME"
            + " " + PREFIX_PASSWORD + "PASSWORD\n"
            + "Example: login r/doctor n/Adam Bell pass/doctor1";

    public static final String MESSAGE_FAILURE = "Invalid login credentials. Please try again.";
    public static final String MESSAGE_LOGIN_ALREADY = "You have already logged in.";
    public static final String MESSAGE_NO_RECORD_FOUND = "No staff records found.";
    public static final String MESSAGE_SUCCESS = "Login successful.";

    private final Staff toAuthenticate;

    /**
     * Creates an LoginCommand to add the specified {@code Staff}.
     * This {@code Staff} could possibly be a staff or receptionist.
     */
    public LoginCommand(Staff staff) {
        requireNonNull(staff);
        toAuthenticate = staff;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.hasStaff(toAuthenticate)) {
            throw new CommandException(MESSAGE_NO_RECORD_FOUND);
        } else if (UserSession.isLogin()) {
            return new CommandResult(MESSAGE_LOGIN_ALREADY);
        }

        boolean isAuthenticatedSuccess = model.checkStaffCredentials(toAuthenticate);
        if (!isAuthenticatedSuccess) {
            return new CommandResult(MESSAGE_FAILURE);
        }

        UserSession.create(toAuthenticate);
        EventsCenter.getInstance().post(new LoginSuccessEvent(toAuthenticate));
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LoginCommand // instanceof handles nulls
                && toAuthenticate.equals(((LoginCommand) other).toAuthenticate)); // state check
    }

}
