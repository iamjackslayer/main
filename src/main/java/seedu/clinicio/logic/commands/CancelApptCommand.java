package seedu.clinicio.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.clinicio.commons.core.Messages;
import seedu.clinicio.commons.core.index.Index;
import seedu.clinicio.logic.CommandHistory;
import seedu.clinicio.logic.commands.exceptions.CommandException;
import seedu.clinicio.model.Model;
import seedu.clinicio.model.appointment.Appointment;

//@@author gingivitiss

/**
 * Cancels appointments from the schedule.
 */
public class CancelApptCommand extends Command {

    public static final String COMMAND_WORD = "cancelappt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Cancels appointment from the schedule. "
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_CANCEL_APPOINTMENT_SUCCESS = "Appointment cancelled: %1$s";

    private final Index targetIndex;

    public CancelApptCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Appointment> lastShownList = model.getFilteredAppointmentList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
        }

        //from the appt list shown, select index of appt and cancel
        Appointment targetAppointment = lastShownList.get(targetIndex.getZeroBased());
        model.cancelAppointment(targetAppointment);

        //save changes to ClinicIO
        model.commitClinicIo();
        return new CommandResult(String.format(MESSAGE_CANCEL_APPOINTMENT_SUCCESS, targetAppointment));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CancelApptCommand // instanceof handles nulls
                && targetIndex.equals(((CancelApptCommand) other).targetIndex)); // state check
    }
}
