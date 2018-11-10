package seedu.clinicio.logic.commands;

//@@author gingivitiss

import static java.util.Objects.requireNonNull;

import seedu.clinicio.commons.core.Messages;
import seedu.clinicio.logic.CommandHistory;
import seedu.clinicio.model.Model;
import seedu.clinicio.model.appointment.AppointmentContainsDatePredicate;

/**
 * Lists appointments on specified date.
 */
public class ListApptCommand extends Command {

    public static final String COMMAND_WORD = "listappt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all appointments that lands on "
            + "the specified date (case-insensitive) and displays them as a list "
            + "with index numbers in chronological order.\n"
            + "Parameters: dd mm yyyy\n"
            + "Example: " + COMMAND_WORD + " 12 12 2012";

    public static final String MESSAGE_SUCCESS = "Listed all appointments";

    private final AppointmentContainsDatePredicate predicate;

    public ListApptCommand(AppointmentContainsDatePredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredAppointmentList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_APPOINTMENTS_LISTED_OVERVIEW,
                        model.getFilteredAppointmentList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListApptCommand // instanceof handles nulls
                && predicate.equals(((ListApptCommand) other).predicate)); // state check
    }
}
