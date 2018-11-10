package seedu.clinicio.logic.commands;

import seedu.clinicio.commons.core.EventsCenter;
import seedu.clinicio.commons.core.UserSession;
import seedu.clinicio.commons.events.ui.LogoutClinicIoEvent;
import seedu.clinicio.logic.CommandHistory;
import seedu.clinicio.logic.commands.exceptions.CommandException;
import seedu.clinicio.model.Model;

//@@author jjlee050
/**
 * Logout user from ClinicIO.
 */
public class LogoutCommand extends Command {

    public static final String COMMAND_WORD = "logout";
    public static final String MESSAGE_SUCCESS = "User logout from ClinicIO successfully.";
    public static final String MESSAGE_NO_USER_LOGIN = "You have not logged in to ClinicIO.";

    @Override
    public CommandResult execute(Model model, CommandHistory history)
            throws CommandException {
        if (!UserSession.isLogin()) {
            throw new CommandException(MESSAGE_NO_USER_LOGIN);
        }
        EventsCenter.getInstance().post(new LogoutClinicIoEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
